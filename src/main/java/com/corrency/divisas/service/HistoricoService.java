package com.corrency.divisas.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corrency.divisas.models.Historico;
import com.corrency.divisas.repository.HistoricoRepository;


@Service
public class HistoricoService {
    @Autowired
    HistoricoRepository historicoRepository;

    public Historico saveHistorico(Historico historico){
        return historicoRepository.save(historico);
    }
    
    public Historico getFecha(LocalDateTime fecha){
        Historico response = historicoRepository.getFecha(fecha);
        return response;
    }

    public List<Historico> getFechaBetween(LocalDateTime finit, LocalDateTime fend){
        return historicoRepository.getFechasBetween(finit, fend);
    }

    public Historico getFechaInit(){
        Historico response = historicoRepository.getFechaInicial();
        return response;
    }

    public Historico getFechaEnd(){
        return historicoRepository.getFechaFinal();
    }

    public List<Historico> getAllFechas(){
        return historicoRepository.findAll();
    }

    // f es para saber si es 'I' = Inicial o 'E' = End de final
    public Historico getFecha(LocalDateTime fecha, char f){
        Historico response = historicoRepository.getFecha(fecha);
        if(response == null){
            if(f == 'I')
                response = historicoRepository.getFechaInicial();
            else{
                response = historicoRepository.getFechaFinal();
            }
        }
        return response;
    }


}
