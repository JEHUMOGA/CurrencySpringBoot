package com.corrency.divisas.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corrency.divisas.DivisasApplication;
import com.corrency.divisas.models.CurrenciesResponse;
import com.corrency.divisas.models.Data;
import com.corrency.divisas.models.Historico;
import com.corrency.divisas.models.Meta;
import com.corrency.divisas.models.Response;
import com.corrency.divisas.models.ValuesCurrencies;
import com.corrency.divisas.repository.ValueCurrencyRepository;

@Service
public class CurrenciesServices {
    private static final Logger logger = LogManager.getLogger(DivisasApplication.class);

    @Autowired
    HistoricoService historicoService;

    @Autowired
    ValueCurrencyRepository valueCurrencyRepository;    
    
    public Response getValuesCurrencies(String currency, String finit, String fend){
        Response response = new Response();
        Historico fechaInicial = new Historico();
        Historico fechaFinal = new Historico();
        boolean bandFinit = false;
        boolean bandFend = false;
        currency = currency.toUpperCase();
        if(finit != null ){
            logger.info("Verificando la fecha inicial");
            bandFinit = true;
            fechaInicial = historicoService.getFecha(LocalDateTime.parse(finit,DateTimeFormatter.ISO_DATE_TIME), 'I');
        }

        if(fend != null){
            logger.info("Verificando la fecha Final");
            bandFend = true;
            fechaFinal = historicoService.getFecha(LocalDateTime.parse(fend,DateTimeFormatter.ISO_DATE_TIME), 'F');
        }

        if(bandFinit && !bandFend){
            response = getValuesCurrencies(currency, fechaInicial);
        } 
        else if (!bandFinit && bandFend){
            logger.info("Obteniendo el valor de currency " + currency + " en la fecha " + fechaFinal.getFecha().toString());
            response = getValuesCurrencies(currency, fechaFinal);
        }
        else if(bandFinit && bandFend){
            logger.info("Obteniendo el valor de currency entre las fechas " + fechaInicial.getFecha().toString() + " y " + fechaFinal.getFecha().toString());
            response = getValuesCurrenciesBetween(currency, fechaInicial, fechaFinal);
        }
        else {
            response = getAllValues(currency);
        }
        return response;
    }

    public Response getValuesCurrencies(String code, Historico fecha){
        Response response = new Response();
        Data data = new Data();
        Meta meta = new Meta();
        String mensaje = null;
        try{
            
            if(fecha == null){
                logger.info("No se encontro registro de la fecha");
                mensaje = "No se encontraron fechas";
                meta.setMeta(404, "Not Found", mensaje);
                response.setMeta(meta);
                return response;
            }
            logger.info("Obteniendo el valor de currency " + code + " en la fecha " + fecha.getFecha().toString());

            List<ValuesCurrencies> valores = valueCurrencyRepository.getValuesCurrency(fecha.getHistoricoid(),code.toUpperCase());
            if(valores.isEmpty()){
                logger.error("Moneda no encontrada.");
                meta.setMeta(404, "Not Found", null);
                response.setMeta(meta);
                return response;
            }
            CurrenciesResponse currenciesResponse = new CurrenciesResponse(fecha.getFecha(), valores);
            meta.setMeta(200, "Ok", mensaje);
            response.setMeta(meta);
            data.setCurrencie(currenciesResponse);
            response.setData(data);
        }catch(Exception e){
            logger.fatal("Error al obtener valor de moneda: " + e.getMessage());
            mensaje = "Error al consultar currencies: " + e.getMessage();
            meta.setMeta(500, "System Error", mensaje);
            response.setMeta(meta);
            return response;
        }

        return response;
    }

    public Response getAllValues(String currency){
        logger.info("Obteniendo los valores de todas las monedas.");
        Response response = new Response();
        Meta meta = new Meta();
        Data data = new Data();
        String mensaje =  null;
        try{
            List<Historico> fechas = historicoService.getAllFechas();
            if(fechas.isEmpty()){
                logger.info("No se encontro registros");
                mensaje = "No se encontraron registros";
                meta.setMeta(404, "Not Found", mensaje);
                response.setMeta(meta);
                return response;
            }


            List<CurrenciesResponse> valuesCurrencies = new ArrayList<CurrenciesResponse>();
            if(currency.equals("ALL")){
                for (Historico fecha : fechas) {
                    CurrenciesResponse valueCurrency = new CurrenciesResponse(
                        fecha.getFecha(), 
                        valueCurrencyRepository.getValuesCurrency(fecha.getHistoricoid()));
                    valuesCurrencies.add(valueCurrency);
                }
            }else{
                for (Historico fecha : fechas) {
                    CurrenciesResponse valueCurrency = new CurrenciesResponse(
                        fecha.getFecha(), 
                        valueCurrencyRepository.getValuesCurrency(fecha.getHistoricoid(), currency));
                    valuesCurrencies.add(valueCurrency);
                }
            }
            

            data.setCurrencies(valuesCurrencies);
            meta.setMeta(200, "Ok", mensaje);
            response.setData(data);
            response.setMeta(meta);
        
        }catch (Exception e){
            mensaje = "Error al mostrar all currencies: " + e.getMessage();
            meta.setMeta(500, "System Error", mensaje);
            response.setMeta(meta);
            return response;
        }
        return response;
    }


    public Response getValuesCurrenciesBetween(String currency, Historico finit, Historico fend){
        Response response = new Response();
        Meta meta = new Meta();
        String mensaje = null;

        try{
            if(finit == null && fend == null){
                mensaje = "No se encontraron fechas";
                meta.setMeta(404, "Not Found", mensaje);
                response.setMeta(meta);
                return response;
            }
            List<Historico> fechas = historicoService.getFechaBetween(finit.getFecha(), fend.getFecha());

            if(fechas.isEmpty()){
                meta.setMeta(404, "Not Found", "No se encontraron currencies");
                response.setMeta(meta);
                return response;
            }

            if(currency.equals("ALL")){
                response = getAllBetween(fechas);
            }else{
                response = getValuesBetween(currency,fechas);
            }

            meta.setStatus("Ok");
            meta.setCode(200);
            response.setMeta(meta);
        }catch(Exception e){
            System.out.println("Ah surgido un error inesperado: " + e.getMessage());
            meta.setStatus("System Error");
            meta.setCode(500);
            response.setMeta(meta);
            return response;
        }

        return response;
    }

    public Response getAllBetween(List<Historico> fechas){
        Response response = new Response();
        Data data = new Data();
        data.setCurrencies(new ArrayList<CurrenciesResponse>());
        for(Historico h: fechas){
            CurrenciesResponse values = new CurrenciesResponse(
                h.getFecha(), 
                valueCurrencyRepository.getValuesCurrency(h.getHistoricoid()));
            data.getCurrencies().add(values);            
        }
        response.setData(data);
        return response;
    }

    public Response getValuesBetween(String currency,List<Historico> fechas){
        Response response = new Response();
        Data data = new Data();
        List<ValuesCurrencies> valuesCurrencies = new ArrayList<>();
        List<CurrenciesResponse> valuesResponse = new ArrayList<>();
        data.setCurrencies(new ArrayList<CurrenciesResponse>());
        for(Historico h: fechas){
            valuesCurrencies = valueCurrencyRepository.getValuesCurrency(h.getHistoricoid(), currency);
            CurrenciesResponse values = new CurrenciesResponse(
                h.getFecha(), valuesCurrencies
                );
            
            valuesResponse.add(values);    
        }

        data.setCurrencies(valuesResponse);

        response.setData(data);
        return response;
    }

}
