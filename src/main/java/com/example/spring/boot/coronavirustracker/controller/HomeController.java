package com.example.spring.boot.coronavirustracker.controller;

import com.example.spring.boot.coronavirustracker.service.CoronaVirusDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private CoronaVirusDataService coronaVirusDataService;

    public HomeController(CoronaVirusDataService coronaVirusDataService) {
        this.coronaVirusDataService = coronaVirusDataService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalCases", coronaVirusDataService.totalCases());
        model.addAttribute("differenceTotalCases", coronaVirusDataService.differenceTotalCases());
        model.addAttribute("locations", coronaVirusDataService.getLocations());
        model.addAttribute("stateColumn", CoronaVirusDataService.LOCATION_STATE_COLUMN);
        model.addAttribute("countryColumn", CoronaVirusDataService.LOCATION_COUNTRY_COLUMN);
        model.addAttribute("totalCasesColumn", CoronaVirusDataService.LOCATION_TOTAL_CASES);
        model.addAttribute("differenceTotalCasesColumn",
                CoronaVirusDataService.LOCATION_DIFFERENCE_TOTAL_CASES);
        return "home";
    }
}
