package com.corrency.divisas.models;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataCurrencies {
        private Map<String, ValuesCurrencies> data;
}
