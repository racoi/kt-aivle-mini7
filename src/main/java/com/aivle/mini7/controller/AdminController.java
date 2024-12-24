package com.aivle.mini7.controller;

import com.aivle.mini7.model.Advice;
import com.aivle.mini7.model.Input;
import com.aivle.mini7.model.RecommendHospital;
import com.aivle.mini7.service.EmergencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final EmergencyService emergencyService;

    //     pageable default value
    @GetMapping("/admin")
    public ModelAndView index(Pageable pageable) {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/index");

        Page<Input> input = emergencyService.findAllInput(pageable);
        Page<Advice> advice = emergencyService.findAllAdvice(pageable);
        Page<RecommendHospital> recommendHospital = emergencyService.findAllHospital(pageable);

        mv.addObject("inputList", input);
        mv.addObject("adviceList", advice);
        mv.addObject("recommendHospitalList", recommendHospital);

        return mv;
    }
}
