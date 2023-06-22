package com.corrency.divisas.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="valuescurrencies")
@Getter
@Setter
@JsonIgnoreProperties(value = {
    "valuesid",
    "historicoid"
})
public class ValuesCurrencies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    int valuesid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    int historicoid;
    String code;
    @Column(name = "valuecurrency")
    Double value;
}
