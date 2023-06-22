package com.corrency.divisas.models;


import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "historicocurrencies")
@Getter
@Setter
@NoArgsConstructor
public class Historico {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer historicoid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime fecha = LocalDateTime.now().withNano(0);
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalTime tiemporespuesta;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "status")
    private short status;
}
