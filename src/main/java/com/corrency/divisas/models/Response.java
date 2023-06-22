package com.corrency.divisas.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Data data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Meta meta;
    
}
