package com.corrency.divisas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.corrency.divisas.controller.CurrenciesController;
import com.corrency.divisas.models.CurrenciesResponse;
import com.corrency.divisas.models.Data;
import com.corrency.divisas.models.Meta;
import com.corrency.divisas.models.Response;
import com.corrency.divisas.models.ValuesCurrencies;
import com.corrency.divisas.service.CurrenciesServices;
import com.corrency.divisas.service.HistoricoService;
import com.corrency.divisas.service.ValuesCurrenciesService;


@ExtendWith(MockitoExtension.class)
public class CurrenciesControllerTest {
    @InjectMocks
    CurrenciesController currenciesController;

    @Mock
    CurrenciesServices currenciesServices;

    @Mock
    HistoricoService historicoService;

    @Mock
    ValuesCurrenciesService valuesCurrenciesService;

    @Test
    public void getValuesTest1(){
        Response response = new Response();
        Data dataTest = new Data();
        Meta metaTest = new Meta();
        String currencyTest = "USD";
        LocalDateTime finitTest = LocalDateTime.parse("2023-06-20T22:15:00", DateTimeFormatter.ISO_DATE_TIME);

        Response responseTest = new Response();

        ValuesCurrencies valuesCurrenciesTest = new ValuesCurrencies();
        valuesCurrenciesTest.setCode(currencyTest);
        valuesCurrenciesTest.setHistoricoid(1);
        valuesCurrenciesTest.setValue(5.0);
        valuesCurrenciesTest.setValuesid(1);

        List<ValuesCurrencies> list = new ArrayList<>();
        list.add(valuesCurrenciesTest);

        CurrenciesResponse currenciesResponseTest = new CurrenciesResponse(finitTest, list);

        dataTest.setCurrencie(currenciesResponseTest);
        metaTest.setMeta(200, "Ok", null);
        responseTest.setData(dataTest);
        responseTest.setMeta(metaTest);

        when(currenciesServices.getValuesCurrencies(currencyTest, "2023-06-20T22:15:00", "2023-06-20T22:30:00")).thenReturn(responseTest);

        response = currenciesController.getValues(currencyTest, "2023-06-20T22:15:00", "2023-06-20T22:30:00");

        assertEquals("Ok", response.getMeta().getStatus());
        
    }


}
