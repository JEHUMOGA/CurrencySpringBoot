package com.corrency.divisas.models;

import java.time.LocalDateTime;
import java.util.List;

public class CurrenciesResponse {
    private LocalDateTime fecha;
    private List<ValuesCurrencies> currencies;

    public CurrenciesResponse(LocalDateTime fecha,List<ValuesCurrencies> currencies ){
        this.fecha = fecha;
        this.currencies = currencies;
    }

}
