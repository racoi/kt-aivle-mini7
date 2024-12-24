package com.aivle.mini7.service;

import com.aivle.mini7.client.api.FastApiClient;
import com.aivle.mini7.client.dto.HospitalResponse;
import com.aivle.mini7.client.dto.StatusResponse;
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

@RequiredArgsConstructor
@Service
public class EmergencyService {
    private final InputRepository inputRepository;
    private final RecommendHosptialRepository recommendHosptialRepository;
    private final FastApiClient fastApiClient;
    private final EmergencyInfoRepository emergencyInfoRepository;
    private final AdviceRepository adviceRepository;

    public StatusResponse createInput(Input input){
        LocalDateTime now=LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 포맷 적용
        String formattedDateTime = now.format(formatter);
        input.setDatetime(formattedDateTime);
//        input.setInputLatitude(37.555946);
//        input.setInputLongitude(126.972317);
//        FastApiClient 를 호출한다.
        StatusResponse statusResponse = fastApiClient.getHospital(input.getDetail(), input.getLatitude(), input.getLongitude(),input.getEmCount());
        saveInput(input);
        return statusResponse;
    }

    public List<HospitalResponse> createHospitals(StatusResponse statusResponse,Input input){
        List<HospitalResponse> hospitalList = statusResponse.getData();
        for (HospitalResponse hr:hospitalList){
            RecommendHospital recommendHospital = new RecommendHospital();
            recommendHospital.setInputId(input);
            recommendHospital.setDuration(hr.getDuration());
            recommendHospital.setDistance(hr.getDistance());
            recommendHospital.setArrivalTime(hr.getArrivalTime());
            recommendHospital.setDepartureTime(hr.getDepartureTime());
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

}
