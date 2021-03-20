package com.exercise.geo.form;

import com.exercise.geo.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping({ "/avg"})
public class AverageController {

    @Autowired
    private GeoService geoService;

    @GetMapping
    public String main(Model model) {
        try{
            HashMap<String,Double> averageDistance = geoService.getAverageDistance();
            model.addAttribute("averageDistance",averageDistance.get("distanciaPromedio"));
            return "avg";
        }catch (Exception e) {
            model.addAttribute("error", "Cannot retrieve any data, try searching for an ip first");
            return "error";
        }

    }
}
