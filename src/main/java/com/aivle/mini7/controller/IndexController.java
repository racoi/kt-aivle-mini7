package com.aivle.mini7.controller;

import com.aivle.mini7.client.api.FastApiClient;
import com.aivle.mini7.client.dto.HospitalResponse;
import com.aivle.mini7.client.dto.StatusResponse;
//import com.aivle.mini7.model.EmergencyInfo;
import com.aivle.mini7.model.EmergencyInfo;
import com.aivle.mini7.model.Input;
//import com.aivle.mini7.repository.EmergencyInfoRepository;
import com.aivle.mini7.model.RecommendHospital;
import com.aivle.mini7.repository.EmergencyInfoRepository;
import com.aivle.mini7.repository.InputRepository;
import com.aivle.mini7.repository.RecommendHosptialRepository;
import com.aivle.mini7.service.EmergencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {
    private final EmergencyService emergencyService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/recommend_hospital")
    public ModelAndView recommend_hospital(@ModelAttribute Input input) {
        StatusResponse statusResponse = emergencyService.createInput(input);
        //log.info("hospital: {}", hospitalList);
        ModelAndView mv = new ModelAndView();
        if (statusResponse.getGrade()<=3) {
            mv.addObject("hospitalList", emergencyService.createHospitals(statusResponse,input));
            mv.setViewName("recommend_hospital");  // 병원 목록 화면
        } else {
            mv.addObject("message", emergencyService.createAdvice(statusResponse,input));
            mv.setViewName("comment");  // 응급처치 화면
        }
        return mv;
    }

}


