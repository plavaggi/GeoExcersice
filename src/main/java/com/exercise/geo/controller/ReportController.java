package com.exercise.geo.controller;

import com.exercise.geo.dto.GeoDataDto;
import com.exercise.geo.model.Input;
import com.exercise.geo.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping({ "/reporte"})
public class ReportController {

    @Autowired
    private GeoService geoService;

    @GetMapping
    public String main(Model model) {
        model.addAttribute(model);
        return "reports";
    }


}
