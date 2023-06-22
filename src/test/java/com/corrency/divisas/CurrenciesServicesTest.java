package com.corrency.divisas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.corrency.divisas.models.CurrenciesResponse;
import com.corrency.divisas.models.Data;
import com.corrency.divisas.models.Historico;
import com.corrency.divisas.models.Meta;
import com.corrency.divisas.models.Response;
import com.corrency.divisas.models.ValuesCurrencies;
import com.corrency.divisas.repository.ValueCurrencyRepository;
import com.corrency.divisas.service.CurrenciesServices;
import com.corrency.divisas.service.HistoricoService;


@ExtendWith(MockitoExtension.class)
public class CurrenciesServicesTest {
    @InjectMocks
    CurrenciesServices currenciesServices;
    

    @Mock
    HistoricoService historicoService;
    
    @Mock
    ValueCurrencyRepository valueCurrencyRepository;

    @Test
    public void getValuesCurrenciesTest(){
        String currencyTest = "USD";
        LocalDateTime finitTest = LocalDateTime.parse("2023-06-20T22:15:00", DateTimeFormatter.ISO_DATE_TIME);
        Historico fecha = new Historico();
        fecha.setFecha(finitTest);
        fecha.setHistoricoid(1);
        fecha.setStatus((short)1);

        when(historicoService.getFecha(finitTest, 'I')).thenReturn(fecha);

        ValuesCurrencies valuesCurrenciesTest = new ValuesCurrencies();
        valuesCurrenciesTest.setCode("USD");
        valuesCurrenciesTest.setHistoricoid(1);
        valuesCurrenciesTest.setValue(5.0);
        valuesCurrenciesTest.setValuesid(1);
        List<ValuesCurrencies> valores = new ArrayList<>();

        valores.add(valuesCurrenciesTest);

        when(valueCurrencyRepository.getValuesCurrency(1, currencyTest)).thenReturn(valores);

        Response responseTest = currenciesServices.getValuesCurrencies(currencyTest, "2023-06-20T22:15:00", null);

        assertEquals("Ok", responseTest.getMeta().getStatus());
        
    }

    @Test
    public void getValuesCurrenciesTest1(){
        String currencyTest = "USD";
        LocalDateTime finitTest = LocalDateTime.parse("2023-06-20T22:15:00", DateTimeFormatter.ISO_DATE_TIME);
        Historico fecha = new Historico();
        fecha.setFecha(finitTest);
        fecha.setHistoricoid(1);
        fecha.setStatus((short)1);

        when(historicoService.getFecha(finitTest, 'F')).thenReturn(fecha);

        ValuesCurrencies valuesCurrenciesTest = new ValuesCurrencies();
        valuesCurrenciesTest.setCode("USD");
        valuesCurrenciesTest.setHistoricoid(1);
        valuesCurrenciesTest.setValue(5.0);
        valuesCurrenciesTest.setValuesid(1);
        List<ValuesCurrencies> valores = new ArrayList<>();

        valores.add(valuesCurrenciesTest);

        when(valueCurrencyRepository.getValuesCurrency(1, currencyTest)).thenReturn(valores);

        Response responseTest = currenciesServices.getValuesCurrencies(currencyTest, null,"2023-06-20T22:15:00");

        assertEquals("Ok", responseTest.getMeta().getStatus());
        
    }

    @Test
    public void getValuesCurrenciesTest3(){
        String currencyTest = "USD";
        LocalDateTime finitTest = LocalDateTime.parse("2023-06-20T22:15:00", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime fendTest = LocalDateTime.parse("2023-06-20T22:30:00", DateTimeFormatter.ISO_DATE_TIME);
        Historico fechaInit = new Historico();
        fechaInit.setFecha(finitTest);
        fechaInit.setHistoricoid(1);
        fechaInit.setStatus((short)1);

        Historico fechaEnd = new Historico();
        fechaEnd.setFecha(fendTest);
        fechaEnd.setHistoricoid(2);
        fechaEnd.setStatus((short)1);

        when(historicoService.getFecha(finitTest, 'I')).thenReturn(fechaInit);
        when(historicoService.getFecha(fendTest, 'F')).thenReturn(fechaEnd);

        ValuesCurrencies valuesCurrenciesTest = new ValuesCurrencies();
        valuesCurrenciesTest.setCode("USD");
        valuesCurrenciesTest.setHistoricoid(1);
        valuesCurrenciesTest.setValue(5.0);
        valuesCurrenciesTest.setValuesid(1);
        List<Historico> listFechas = new ArrayList<>();

        listFechas.add(fechaInit);
        listFechas.add(fechaEnd);

        when(historicoService.getFechaBetween(finitTest, fendTest)).thenReturn(listFechas);

        Response responseTest = currenciesServices.getValuesCurrencies(currencyTest, "2023-06-20T22:15:00","2023-06-20T22:30:00");

        assertEquals("Ok", responseTest.getMeta().getStatus());
        
    }

    @Test
    public void getValuesCurrenciesTest4(){
        LocalDateTime finitTest = LocalDateTime.parse("2023-06-20T22:15:00", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime fendTest = LocalDateTime.parse("2023-06-20T22:30:00", DateTimeFormatter.ISO_DATE_TIME);
        Historico fechaInit = new Historico();
        fechaInit.setFecha(finitTest);
        fechaInit.setHistoricoid(1);
        fechaInit.setStatus((short)1);

        Historico fechaEnd = new Historico();
        fechaEnd.setFecha(fendTest);
        fechaEnd.setHistoricoid(2);
        fechaEnd.setStatus((short)1);

        ValuesCurrencies valuesCurrenciesTest = new ValuesCurrencies();
        valuesCurrenciesTest.setCode("USD");
        valuesCurrenciesTest.setHistoricoid(1);
        valuesCurrenciesTest.setValue(5.0);
        valuesCurrenciesTest.setValuesid(1);
        List<Historico> listFechas = new ArrayList<>();

        listFechas.add(fechaInit);
        listFechas.add(fechaEnd);

        when(historicoService.getAllFechas()).thenReturn(listFechas);

        Response response = currenciesServices.getValuesCurrencies("ALL", null, null);

        assertEquals(200,response.getMeta().getCode());
    }

    @Test
    public void getValuesCurrenciesTest2(){
        String currencyTest = "USD";
        Historico fechaTest = new Historico();
        fechaTest.setHistoricoid(1);
        fechaTest.setFecha(LocalDateTime.now());
        fechaTest.setTiemporespuesta(LocalTime.now());

        ValuesCurrencies valuesCurrenciesTest = new ValuesCurrencies();
        valuesCurrenciesTest.setHistoricoid(1);
        valuesCurrenciesTest.setCode("USD");
        valuesCurrenciesTest.setValue(5.0);
        valuesCurrenciesTest.setValuesid(1);
        List<ValuesCurrencies> valoresTest = new ArrayList<>();
        valoresTest.add(valuesCurrenciesTest);

        when(valueCurrencyRepository.getValuesCurrency(fechaTest.getHistoricoid(), currencyTest))
        .thenReturn(valoresTest);

        CurrenciesResponse currenciesResponseTest = new CurrenciesResponse(fechaTest.getFecha(), valoresTest);
        Data dataTest = new Data();
        Meta metaTest = new Meta();

        Response responseTest = new Response();
        dataTest.setCurrencie(currenciesResponseTest);
        metaTest.setMeta(200, "Ok", null);
        responseTest.setData(dataTest);
        responseTest.setMeta(metaTest);

        Response response = currenciesServices.getValuesCurrencies(currencyTest, fechaTest);

        assertEquals("Ok", response.getMeta().getStatus());

        
    }

    @Test
    public void getValuesCurrenciesNotFoundTest(){

        Response response = new Response();

        response = currenciesServices.getValuesCurrencies("USD", null);

        assertEquals(404,response.getMeta().getCode());
    }

    @Test
    public void getValuesCurrenciesBadRequest(){
        Historico historicoTest = new Historico();
        historicoTest.setFecha(LocalDateTime.now().withNano(0));
        historicoTest.setHistoricoid(1);

        when(valueCurrencyRepository.getValuesCurrency(1, "MXN")).thenReturn(new ArrayList<>());

        Response response = currenciesServices.getValuesCurrencies("MXN", historicoTest);

        assertEquals(404, response.getMeta().getCode());
    }

    @Test
    public void getValuesCurrenciesExceptionTest(){
        String currencyTest = "USD";
        Historico fechaTest = new Historico();
        fechaTest.setHistoricoid(1);
        fechaTest.setFecha(LocalDateTime.now());
        fechaTest.setTiemporespuesta(LocalTime.now());
        when(valueCurrencyRepository.getValuesCurrency(fechaTest.getHistoricoid(), currencyTest)).thenThrow(new NullPointerException());

        Response response = currenciesServices.getValuesCurrencies(currencyTest, fechaTest);

        assertEquals("System Error", response.getMeta().getStatus());
    }
    

    @Test
    public void getAllValuesTest(){
        List<Historico> fechasTest = new ArrayList<>();

        Historico fechaTest = new Historico();
        fechaTest.setFecha(LocalDateTime.now().withNano(0));
        fechaTest.setHistoricoid(1);
        fechaTest.setStatus((short) 1);
        fechaTest.setTiemporespuesta(LocalTime.now().withNano(0));

        fechasTest.add(fechaTest);

        when(historicoService.getAllFechas()).thenReturn(fechasTest);

        ValuesCurrencies values = new ValuesCurrencies();
        values.setCode("USD");
        values.setHistoricoid(1);
        values.setValue(5.0);
        values.setValuesid(1);

        List<ValuesCurrencies> valuesCurrencies = new ArrayList<>();
        valuesCurrencies.add(values);

        when(valueCurrencyRepository.getValuesCurrency(1)).thenReturn(valuesCurrencies);

        Response response = currenciesServices.getAllValues("ALL");

        assertEquals("Ok", response.getMeta().getStatus());

    }
    @Test
    public void getAllValuesTest2(){
        List<Historico> fechasTest = new ArrayList<>();

        Historico fechaTest = new Historico();
        fechaTest.setFecha(LocalDateTime.now().withNano(0));
        fechaTest.setHistoricoid(1);
        fechaTest.setStatus((short) 1);
        fechaTest.setTiemporespuesta(LocalTime.now().withNano(0));

        fechasTest.add(fechaTest);

        when(historicoService.getAllFechas()).thenReturn(fechasTest);

        ValuesCurrencies values = new ValuesCurrencies();
        values.setCode("USD");
        values.setHistoricoid(1);
        values.setValue(5.0);
        values.setValuesid(1);

        List<ValuesCurrencies> valuesCurrencies = new ArrayList<>();
        valuesCurrencies.add(values);

        when(valueCurrencyRepository.getValuesCurrency(1, "USD")).thenReturn(valuesCurrencies);

        Response response = currenciesServices.getAllValues("USD");

        assertEquals("Ok", response.getMeta().getStatus());

    }

    @Test
    public void getAllValuesNotFoundTest(){
        when(historicoService.getAllFechas()).thenReturn(new ArrayList<>());

        Response response = currenciesServices.getAllValues("ALL");

        assertEquals(404,response.getMeta().getCode());
    }

    @Test
    public void getAllValuesExceptionTest(){
        List<Historico> fechasTest = new ArrayList<>();

        Historico fechaTest = new Historico();
        fechaTest.setFecha(LocalDateTime.now().withNano(0));
        fechaTest.setHistoricoid(1);
        fechaTest.setStatus((short) 1);
        fechaTest.setTiemporespuesta(LocalTime.now().withNano(0));

        fechasTest.add(fechaTest);

        when(historicoService.getAllFechas()).thenReturn(fechasTest);


        when(valueCurrencyRepository.getValuesCurrency(1)).thenThrow(new NullPointerException());

        Response response = currenciesServices.getAllValues("ALL");

        assertEquals(500,response.getMeta().getCode());
    }
    

    @Test
    public void getValuesCurrenciesBetweenTest(){
        Response response = new Response();
        LocalDateTime finitTest = LocalDateTime.parse("2023-06-20T22:15:00", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime fendTest = LocalDateTime.parse("2023-06-20T22:30:00", DateTimeFormatter.ISO_DATE_TIME);
        

        Historico dateTest = new Historico();
        dateTest.setFecha(finitTest);
        dateTest.setHistoricoid(1);
        dateTest.setStatus((short)1);
        dateTest.setTiemporespuesta(LocalTime.now().withNano(0));

        Historico dateTestEnd = new Historico();
        dateTestEnd.setFecha(fendTest);
        dateTestEnd.setHistoricoid(1);
        dateTestEnd.setStatus((short)1);
        dateTestEnd.setTiemporespuesta(LocalTime.now().withNano(0));

        List<Historico> fechasTest = new ArrayList<>();
        fechasTest.add(dateTest);

        when(historicoService.getFechaBetween(finitTest, fendTest)).thenReturn(fechasTest);

        response = currenciesServices.getValuesCurrenciesBetween("USD", dateTest, dateTestEnd);

        assertEquals("Ok", response.getMeta().getStatus());
    }

    @Test
    public void getValuesCurrenciesBetweenTest2(){
        Response response = new Response();
        LocalDateTime finitTest = LocalDateTime.parse("2023-06-20T22:15:00", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime fendTest = LocalDateTime.parse("2023-06-20T22:30:00", DateTimeFormatter.ISO_DATE_TIME);
        

        Historico dateTest = new Historico();
        dateTest.setFecha(finitTest);
        dateTest.setHistoricoid(1);
        dateTest.setStatus((short)1);
        dateTest.setTiemporespuesta(LocalTime.now().withNano(0));

        Historico dateTestEnd = new Historico();
        dateTestEnd.setFecha(fendTest);
        dateTestEnd.setHistoricoid(1);
        dateTestEnd.setStatus((short)1);
        dateTestEnd.setTiemporespuesta(LocalTime.now().withNano(0));

        List<Historico> fechasTest = new ArrayList<>();
        fechasTest.add(dateTest);

        when(historicoService.getFechaBetween(finitTest, fendTest)).thenReturn(fechasTest);

        response = currenciesServices.getValuesCurrenciesBetween("ALL", dateTest, dateTestEnd);

        assertEquals("Ok", response.getMeta().getStatus());
    }

    @Test
    public void getValuesCurrenciesBetweenNotFound1(){
        Response response = currenciesServices.getValuesCurrenciesBetween("USD", null, null);

        assertEquals(404, response.getMeta().getCode());
    }

    @Test
    public void getValuesCurrenciesBetweenNotFound2(){
        LocalDateTime finitTest = LocalDateTime.parse("2023-06-20T22:15:00", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime fendTest = LocalDateTime.parse("2023-06-20T22:30:00", DateTimeFormatter.ISO_DATE_TIME);
        
        
        Historico dateTest = new Historico();
        dateTest.setFecha(finitTest);
        dateTest.setHistoricoid(1);
        dateTest.setStatus((short)1);
        dateTest.setTiemporespuesta(LocalTime.now().withNano(0));

        Historico dateTestEnd = new Historico();
        dateTestEnd.setFecha(fendTest);
        dateTestEnd.setHistoricoid(1);
        dateTestEnd.setStatus((short)1);
        dateTestEnd.setTiemporespuesta(LocalTime.now().withNano(0));

        List<Historico> fechasTest = new ArrayList<>();
        fechasTest.add(dateTest);

        when(historicoService.getFechaBetween(finitTest, fendTest)).thenReturn(new ArrayList<>());

        Response response = currenciesServices.getValuesCurrenciesBetween("ALL", dateTest, dateTestEnd);

        assertEquals(404, response.getMeta().getCode());
    }

    @Test
    public void getValuesCurrenciesBetweenException(){
        LocalDateTime finitTest = LocalDateTime.parse("2023-06-20T22:15:00", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime fendTest = LocalDateTime.parse("2023-06-20T22:30:00", DateTimeFormatter.ISO_DATE_TIME);
        
        
        Historico dateTest = new Historico();
        dateTest.setFecha(finitTest);
        dateTest.setHistoricoid(1);
        dateTest.setStatus((short)1);
        dateTest.setTiemporespuesta(LocalTime.now().withNano(0));

        Historico dateTestEnd = new Historico();
        dateTestEnd.setFecha(fendTest);
        dateTestEnd.setHistoricoid(1);
        dateTestEnd.setStatus((short)1);
        dateTestEnd.setTiemporespuesta(LocalTime.now().withNano(0));

        List<Historico> fechasTest = new ArrayList<>();
        fechasTest.add(dateTest);

        when(historicoService.getFechaBetween(finitTest, fendTest)).thenThrow(new NullPointerException());

        Response response = currenciesServices.getValuesCurrenciesBetween("ALL", dateTest, dateTestEnd);

        assertEquals(500, response.getMeta().getCode());
    }

}
