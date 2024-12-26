package com.aivle.mini7.controller;

import com.aivle.mini7.model.Advice;
import com.aivle.mini7.model.Input;
import com.aivle.mini7.model.RecommendHospital;
import com.aivle.mini7.service.EmergencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ModelAndView adminDashboard(Pageable pageable) {
        ModelAndView mv = new ModelAndView("admin/index");
        // 대시보드용 index.html
        Page<Input> input = emergencyService.findAllInput(pageable);
        Page<Advice> advice = emergencyService.findAllAdvice(pageable);
        Page<RecommendHospital> recommendHospital = emergencyService.findAllHospital(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id")));

        mv.addObject("inputList", input);
        mv.addObject("adviceList", advice);
        mv.addObject("recommendHospitalList", recommendHospital);

        long totalCalls = emergencyService.getTotalCalls();
        String averageDuration = emergencyService.getAverageDuration();
        long urgentCalls = emergencyService.getUrgentCalls();
        double averageDistance = emergencyService.getAverageDistance();
        averageDistance = Math.round(averageDistance * 100.0) / 100.0;


        mv.addObject("totalRequests", totalCalls);
        mv.addObject("averageDistance", averageDistance);
        mv.addObject("averageTime", averageDuration);
        if(totalCalls==0)
            mv.addObject("emergencyRatio", 0.0);
        else{
            double temp=urgentCalls / totalCalls * 100.0;
            temp=Math.round(temp * 100.0) / 100.0;
            mv.addObject("emergencyRatio", temp);
        }

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

