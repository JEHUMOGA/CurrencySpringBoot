package com.corrency.divisas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.corrency.divisas.configuration.CurrenciesProperties;
import com.corrency.divisas.models.DataCurrencies;
import com.corrency.divisas.models.Historico;
import com.corrency.divisas.models.Response;
import com.corrency.divisas.models.ValuesCurrencies;
import com.corrency.divisas.repository.ValueCurrencyRepository;
import com.corrency.divisas.service.HistoricoService;
import com.corrency.divisas.service.ValuesCurrenciesService;

@ExtendWith(MockitoExtension.class)
public class ValuesCurrenciesServiceTest {
    @InjectMocks
    ValuesCurrenciesService valuesCurrenciesService;

    @Mock
    HistoricoService historicoService;

    @Mock
    ValueCurrencyRepository valueCurrencyRepository;

    @Mock
    CurrenciesProperties currenciesProperties;

    @Mock
    RestTemplate restTemplate;

    @Mock
    SimpleClientHttpRequestFactory clientHttpRequestFactory;

    @Test
    public void postValueTest(){
        DataCurrencies valuesTest = new DataCurrencies();
        ValuesCurrencies monedaTest = new ValuesCurrencies();

        monedaTest.setCode("USD");
        monedaTest.setHistoricoid(1);
        monedaTest.setValue(5.0);
        monedaTest.setValuesid(1);
        Map<String, ValuesCurrencies> dataTest = new HashMap<>();
        dataTest.put("USD", monedaTest);
        valuesTest.setData(dataTest);

        Historico fechaResponseTest = new Historico();
        fechaResponseTest.setHistoricoid(1);


        when(restTemplate.getRequestFactory()).thenReturn(clientHttpRequestFactory);

        when(currenciesProperties.getApikey()).thenReturn("uriTest/mock");

        when(restTemplate.getForObject("uriTest/mock",  DataCurrencies.class)).thenReturn(valuesTest);

        when(currenciesProperties.getTimeout()).thenReturn(5000);

        when(historicoService.saveHistorico(any(Historico.class))).thenReturn(fechaResponseTest);
        
        Response responseTest = valuesCurrenciesService.postValues();

        assertEquals(200, responseTest.getMeta().getCode());
    }


    @Test
    public void postValuesResourceAccessExceptionTest(){
        Historico fechaResponseTest = new Historico();
        fechaResponseTest.setHistoricoid(1);

        when(restTemplate.getRequestFactory()).thenThrow(new ResourceAccessException("MockitoResourceAccessException"));

        when(historicoService.saveHistorico(any(Historico.class))).thenReturn(fechaResponseTest);
        
        Response responseTest = valuesCurrenciesService.postValues();

        assertEquals(408, responseTest.getMeta().getCode());
    }

    @Test
    public void postValuesExceptionRestClientTest(){
        Historico fechaResponseTest = new Historico();
        fechaResponseTest.setHistoricoid(1);

        when(restTemplate.getRequestFactory()).thenThrow(new RestClientException("MockitoRestClientException"));

        when(historicoService.saveHistorico(any(Historico.class))).thenReturn(fechaResponseTest);
        
        Response responseTest = valuesCurrenciesService.postValues();

        assertEquals(500, responseTest.getMeta().getCode());
    }

    @Test
    public void postValuesExceptionTest(){
        Historico fechaResponseTest = new Historico();
        fechaResponseTest.setHistoricoid(1);

        when(restTemplate.getRequestFactory()).thenThrow(new NullPointerException("MockitoException"));

        when(historicoService.saveHistorico(any(Historico.class))).thenReturn(fechaResponseTest);
        
        Response responseTest = valuesCurrenciesService.postValues();

        assertEquals(500, responseTest.getMeta().getCode());
    }
}
