package com.corrency.divisas.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meta {
    int code; 
    String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String mensaje;

    public void setMeta(int code, String status, String mensaje){
        this.code = code;
        this.status = status;
        this.mensaje = mensaje;
    }
}
