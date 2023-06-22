package com.corrency.divisas.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Data {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CurrenciesResponse currencie;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CurrenciesResponse> currencies;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ValuesCurrencies> valuesCurrencies;

}
