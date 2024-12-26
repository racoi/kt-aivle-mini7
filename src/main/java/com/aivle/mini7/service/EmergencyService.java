package com.aivle.mini7.service;

import com.aivle.mini7.client.api.FastApiClient;
import com.aivle.mini7.client.dto.HospitalResponse;
import com.aivle.mini7.client.dto.StatusResponse;
import com.aivle.mini7.mapper.RecommendHospitalMapper;
import com.aivle.mini7.model.Advice;
import com.aivle.mini7.model.EmergencyInfo;
import com.aivle.mini7.model.Input;
import com.aivle.mini7.model.RecommendHospital;
import com.aivle.mini7.repository.AdviceRepository;
import com.aivle.mini7.repository.EmergencyInfoRepository;
import com.aivle.mini7.repository.InputRepository;
import com.aivle.mini7.repository.RecommendHosptialRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmergencyService {
    private final InputRepository inputRepository;
    private final RecommendHosptialRepository recommendHosptialRepository;
    private final FastApiClient fastApiClient;
    private final EmergencyInfoRepository emergencyInfoRepository;
    private final AdviceRepository adviceRepository;
    private final RecommendHospitalMapper recommendHospitalMapper;

    public StatusResponse createInput(Input input){
        LocalDateTime now=LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 포맷 적용
        String formattedDateTime = now.format(formatter);
        input.setDatetime(formattedDateTime);
        StatusResponse statusResponse = fastApiClient.getHospital(input.getDetail(), input.getLatitude(), input.getLongitude(),input.getEmCount());
        saveInput(input);
        return statusResponse;
    }

    public List<HospitalResponse> createHospitals(StatusResponse statusResponse,Input input){
        List<HospitalResponse> hospitalList = statusResponse.getData();
        for (HospitalResponse hr:hospitalList){
            RecommendHospital recommendHospital = recommendHospitalMapper.hospitalResponseToRecommendHospital(hr);
            recommendHospital.setInputId(input);
            EmergencyInfo emergencyInfo=emergencyInfoRepository.findById(hr.getInstitution_code()).orElseThrow(()->new IllegalArgumentException());
            recommendHospital.setInstitutionCode(emergencyInfo);
            saveHospitals(recommendHospital);
        }
        return hospitalList;
    }

    public String createAdvice(StatusResponse statusResponse,Input input){
        Advice advice= new Advice();
        String message=statusResponse.getMessage();
        advice.setAdvice(message);
        advice.setInput(input);
        advice.setEmClass(statusResponse.getGrade());
        saveAdvice(advice);
        return message;
    }

    public void saveAdvice(Advice advice)
    {
        adviceRepository.save(advice);
    }

    public void saveHospitals(RecommendHospital recommendHospital)
    {
        recommendHosptialRepository.save(recommendHospital);
    }

    public void saveInput(Input input){
        inputRepository.save(input);
    }

    public Page<Input> findAllInput(Pageable pageable){
        return inputRepository.findAll(pageable);
    }

    public Page<Advice> findAllAdvice(Pageable pageable){
        return adviceRepository.findAll(pageable);
    }

    public Page<RecommendHospital> findAllHospital(Pageable pageable){
        return recommendHosptialRepository.findAll(pageable);
    }

    public Page<RecommendHospital> getRecommendHospitalList(Pageable pageable) {
        return recommendHosptialRepository.findAll(pageable);
    }

    public long getTotalCalls() {
        if (inputRepository.count()==0)
            return 0;
        return inputRepository.count();
    }

    private long durationToSeconds(String duration) {
        String[] parts = duration.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        return hours * 3600 + minutes * 60 + seconds;
    }

    private String secondsToDuration(long totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
    public String getAverageDuration() {
        // 예: 평균 소요 시간 계산
        List<String> durations = recommendHosptialRepository.findAll().stream()
                .map(RecommendHospital::getDuration)
                .collect(Collectors.toList());

        long totalSeconds = durations.stream()
                .mapToLong(this::durationToSeconds)
                .sum();
        long averageSeconds = durations.isEmpty() ? 0 : totalSeconds / durations.size();
        return secondsToDuration(averageSeconds);
    }
    public double getAverageDistance() {
        return recommendHosptialRepository.findAll().stream()
                .mapToDouble(RecommendHospital::getDistance)
                .average()
                .orElse(0.0);
    }

    public long getUrgentCalls() {
        if(inputRepository.count() - adviceRepository.count()==0)
            return 0;
        return inputRepository.count() - adviceRepository.count();
    }

    public long getNonRelevantCalls() {
        if(adviceRepository.count()==0)
            return 0;
        return adviceRepository.count();
    }

}
