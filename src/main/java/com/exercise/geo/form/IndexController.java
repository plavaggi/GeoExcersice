package com.exercise.geo.form;

import com.exercise.geo.dto.GeoDataDto;
import com.exercise.geo.model.common.Input;
import com.exercise.geo.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping({ "/", "/index" })
public class IndexController {

    @Autowired
    private GeoService geoService;

    @GetMapping
    public String main(Model model) {
        model.addAttribute("input", new Input());
        return "index";
    }

    @PostMapping
    public String save(Input input, Model model) {
        try {
            String ipAdress = input.getIpAddress();
            GeoDataDto GeoDataDto = geoService.postDataByIp(ipAdress);
            model.addAttribute("GeoDataDto", GeoDataDto);
            return "saved";
        }catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }

    }
}