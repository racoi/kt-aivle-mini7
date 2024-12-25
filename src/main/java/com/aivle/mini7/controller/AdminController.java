package com.aivle.mini7.controller;

import com.aivle.mini7.model.Advice;
import com.aivle.mini7.model.Input;
import com.aivle.mini7.model.RecommendHospital;
import com.aivle.mini7.service.EmergencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private final EmergencyService emergencyService;

    // 대시보드 페이지
    @GetMapping
    public ModelAndView adminDashboard() {
        ModelAndView mv = new ModelAndView("admin/index"); // 대시보드용 index.html
        return mv;
    }

    // Input 데이터 페이지
    @GetMapping("/input")
    public ModelAndView input(Pageable pageable) {
        ModelAndView mv = new ModelAndView("admin/input");
        Page<Input> input = emergencyService.findAllInput(pageable);
        mv.addObject("inputList", input);
        return mv;
    }

    // Advice 데이터 페이지
    @GetMapping("/advice")
    public ModelAndView advice(Pageable pageable) {
        ModelAndView mv = new ModelAndView("admin/advice");
        Page<Advice> advice = emergencyService.findAllAdvice(pageable);
        mv.addObject("adviceList", advice);
        return mv;
    }

    // Recommend Hospital 데이터 페이지
    @GetMapping("/recommend-hospital")
    public ModelAndView recommendHospital(Pageable pageable) {
        ModelAndView mv = new ModelAndView("admin/recommendHospital");
        Page<RecommendHospital> recommendHospital = emergencyService.findAllHospital(pageable);
        mv.addObject("recommendHospitalList", recommendHospital);
        return mv;
    }
}

