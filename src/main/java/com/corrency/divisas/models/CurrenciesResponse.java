package com.corrency.divisas.models;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;

@Getter
public class CurrenciesResponse {
    private LocalDateTime fecha;
    private List<ValuesCurrencies> currencies;

    public CurrenciesResponse(LocalDateTime fecha,List<ValuesCurrencies> currencies ){
        this.fecha = fecha;
        this.currencies = currencies;
    }

}
