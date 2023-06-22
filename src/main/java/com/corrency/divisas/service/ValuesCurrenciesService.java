package com.corrency.divisas.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.corrency.divisas.DivisasApplication;
import com.corrency.divisas.configuration.CurrenciesProperties;
import com.corrency.divisas.models.Data;
import com.corrency.divisas.models.DataCurrencies;
import com.corrency.divisas.models.Historico;
import com.corrency.divisas.models.Meta;
import com.corrency.divisas.models.Response;
import com.corrency.divisas.models.ValuesCurrencies;
import com.corrency.divisas.repository.ValueCurrencyRepository;


@Service
public class ValuesCurrenciesService {
    private static final Logger logger = LogManager.getLogger(DivisasApplication.class);
    @Autowired
    ValueCurrencyRepository valueCurrencyRepository;

    @Autowired
    HistoricoService historicoService;

    @Autowired
    CurrenciesProperties currenciesProperties;

    @Autowired
     RestTemplate restTemplate;

    @Scheduled(fixedDelayString = "${currency.tiempo-peticion}")
    public Response postValues(){
        Response response = new Response();
        Meta meta = new Meta();
        Data data = new Data();
        data.setValuesCurrencies(new ArrayList<ValuesCurrencies>());
        Historico fecha = new Historico();   
        Historico fechaResultado = new Historico();   
        try{

            ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setConnectTimeout(currenciesProperties.getTimeout());
            ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setReadTimeout(currenciesProperties.getTimeout());
            String uri = currenciesProperties.getApikey();

            DataCurrencies values = restTemplate.getForObject(uri, DataCurrencies.class);
            logger.info("Consulta exitosa.");
            fecha.setTiemporespuesta(getTimeResponse(fecha.getFecha(), LocalDateTime.now()));
            fecha.setStatus((short)1);
            fechaResultado  = historicoService.saveHistorico(fecha);
            logger.info("Se registro correctamente la consulta.");
            Collection<ValuesCurrencies> ls = values.getData().values();
            List<ValuesCurrencies> listMonedas = new ArrayList<>();
            for (ValuesCurrencies valuesCurrencies : ls) {
                valuesCurrencies.setHistoricoid(fechaResultado.getHistoricoid());
                //data.getValuesCurrencies().add(valueCurrencyRepository.save(valuesCurrencies));
                listMonedas.add(valuesCurrencies);
            }
            valueCurrencyRepository.saveAll(listMonedas);
            //data.setCurrencies((List<CurrenciesResponse>)valueCurrencyRepository.saveAll(listMonedas));
            logger.info("Valores de monedas registradas correctamente .");
            meta.setCode(200);
            meta.setStatus("Ok");
            response.setData(data);
            response.setMeta(meta);
        }catch(ResourceAccessException res){
            logger.info("ResouseceAccesException: " + res.getMessage());
            fechaResultado.setTiemporespuesta(getTimeResponse(fecha.getFecha(), LocalDateTime.now()));
            fechaResultado.setStatus((short)-1);
            historicoService.saveHistorico(fechaResultado);
            logger.info("Se registro correctamente el fallo en el historial.");
            meta.setMeta(408, "Reques Timeout", res.getMessage());
            response.setMeta(meta);
            return response;
        }catch(RestClientException erc){
            logger.info("RestClientException: " + erc.getMessage());
            fechaResultado.setTiemporespuesta(getTimeResponse(fecha.getFecha(), LocalDateTime.now()));
            fechaResultado.setStatus((short)-1);
            historicoService.saveHistorico(fechaResultado);
            logger.info("Se registro correctamente el fallo en el historial.");
            meta.setMeta(500, "Internal Server Error", erc.getMessage());
            response.setMeta(meta);
            return response;
        }
        catch(Exception e){
            logger.info("Exception no controlada: " + e.getMessage());
            fechaResultado.setTiemporespuesta(getTimeResponse(fecha.getFecha(), LocalDateTime.now()));
            fechaResultado.setStatus((short)-1);
            historicoService.saveHistorico(fechaResultado);
            logger.info("Se registro correctamente el fallo en el historial.");
            meta.setCode(500);
            meta.setStatus("Internal Server Error.");
            meta.setMensaje("Error al guardar los valores de monedas: " + e.getMessage());
            response.setMeta(meta);
            return response;
        }
        return response;
    }

    public LocalTime getTimeResponse(LocalDateTime before, LocalDateTime after){
        Duration diff = Duration.between(before, after);
        long segundos = diff.getSeconds();

        LocalTime response = LocalTime.ofSecondOfDay(segundos);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        response = LocalTime.parse(response.format(formatter), DateTimeFormatter.ofPattern("HH:mm:ss"));

        return response;
    }

}
