package com.corrency.divisas.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.corrency.divisas.DivisasApplication;
import com.corrency.divisas.models.Response;
import com.corrency.divisas.service.CurrenciesServices;
import com.corrency.divisas.service.HistoricoService;
import com.corrency.divisas.service.ValuesCurrenciesService;

import jakarta.validation.constraints.Pattern;

@Validated
@RestController
public class CurrenciesController {
    private static final Logger logger = LogManager.getLogger(DivisasApplication.class);
    
    @Autowired
    CurrenciesServices currenciesServices;

    @Autowired
    HistoricoService historicoService;

    @Autowired
    ValuesCurrenciesService valuesCurrenciesService;

    
    @GetMapping("/currencies/{currency}")
    public Response getValues(
        @PathVariable("currency") @Pattern(regexp = "^[a-zA-Z]{3,5}$") String currency, 
        @RequestParam(required = false) String finit, 
        @RequestParam(required = false) String fend
    ){
        logger.info("Realizando peticion a currencies");
        
        return currenciesServices.getValuesCurrencies(currency, finit, fend);
    }
    
}
