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

import com.corrency.divisas.models.Historico;
import com.corrency.divisas.repository.HistoricoRepository;
import com.corrency.divisas.service.HistoricoService;

@ExtendWith(MockitoExtension.class)
public class HistoricoServiceTest {
    @InjectMocks
    HistoricoService historicoService;

    @Mock
    HistoricoRepository historicoRepository;

    @Test
    public void saveHistoricoTest(){
        Historico historicoTest = new Historico();
        historicoTest.setFecha(LocalDateTime.now().withNano(0));
        historicoTest.setStatus((short)1);
        historicoTest.setTiemporespuesta(LocalTime.now().withNano(0));

        when(historicoRepository.save(historicoTest)).thenReturn(historicoTest);

        Historico responseTest = historicoService.saveHistorico(historicoTest);
        
        assertEquals(historicoTest.getFecha(), responseTest.getFecha());
    }


    @Test
    public void getFechaTest(){
        Historico historicoTest = new Historico();
        historicoTest.setFecha(LocalDateTime.now().withNano(0));
        historicoTest.setStatus((short)1);
        historicoTest.setTiemporespuesta(LocalTime.now().withNano(0));

        when(historicoRepository.getFecha(historicoTest.getFecha())).thenReturn(historicoTest);

        Historico resposeTest = historicoService.getFecha(historicoTest.getFecha());

        assertEquals(historicoTest.getFecha(), resposeTest.getFecha());
    }

    @Test
    public void getFechaBetween(){
        LocalDateTime finitTest = LocalDateTime.parse("2023-06-20T20:15:00", DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime fendTest = LocalDateTime.parse("2023-06-20T20:30:00", DateTimeFormatter.ISO_DATE_TIME);

        Historico fecha = new Historico();
        fecha.setFecha(fendTest);
        fecha.setHistoricoid(1);
        fecha.setStatus((short)1);

        List<Historico> fechasTest = new ArrayList<>();
        fechasTest.add(fecha);

        when(historicoRepository.getFechasBetween(finitTest, fendTest)).thenReturn(fechasTest);

        List<Historico> responseTest = historicoService.getFechaBetween(finitTest, fendTest);

        assertEquals(1, responseTest.size());

    }

    @Test
    public void getFechaInitTest(){
        LocalDateTime finitTest = LocalDateTime.parse("2023-06-20T20:30:00", DateTimeFormatter.ISO_DATE_TIME);

        Historico fechaTest = new Historico();
        fechaTest.setFecha(finitTest);
        fechaTest.setHistoricoid(1);
        fechaTest.setStatus((short)1);

        when(historicoRepository.getFechaInicial()).thenReturn(fechaTest);

        Historico response = historicoService.getFechaInit();
        assertEquals(finitTest, response.getFecha());
    }

    @Test
    public void getFechaEndTest(){
        LocalDateTime fendTest = LocalDateTime.parse("2023-06-20T20:30:00", DateTimeFormatter.ISO_DATE_TIME);

        Historico fechaTest = new Historico();
        fechaTest.setFecha(fendTest);
        fechaTest.setHistoricoid(1);
        fechaTest.setStatus((short)1);

        when(historicoRepository.getFechaFinal()).thenReturn(fechaTest);

        Historico response = historicoService.getFechaEnd();
        assertEquals(fendTest, response.getFecha());
    }

    @Test
    public void getAllFechas(){
        LocalDateTime finitTest = LocalDateTime.parse("2023-06-20T20:30:00", DateTimeFormatter.ISO_DATE_TIME);

        Historico fecha = new Historico();
        fecha.setFecha(finitTest);
        fecha.setHistoricoid(1);
        fecha.setStatus((short)1);

        List<Historico> fechasTest = new ArrayList<>();
        fechasTest.add(fecha);

        when(historicoRepository.findAll()).thenReturn(fechasTest);

        List<Historico> responseTest = historicoService.getAllFechas();

        assertEquals(1,responseTest.size());
    }

    @Test
    public void getFechasTest(){
        LocalDateTime finitTest = LocalDateTime.parse("2023-06-20T20:30:00", DateTimeFormatter.ISO_DATE_TIME);

        Historico fecha = new Historico();
        fecha.setFecha(finitTest);
        fecha.setHistoricoid(1);
        fecha.setStatus((short)1);

        when(historicoRepository.getFecha(finitTest)).thenReturn(fecha);

        Historico responseTest = historicoService.getFecha(finitTest, 'I');

        assertEquals(finitTest, responseTest.getFecha());

    }

    @Test
    public void getFechaTest1(){
        LocalDateTime finitTest = LocalDateTime.parse("2023-06-20T20:30:00", DateTimeFormatter.ISO_DATE_TIME);

        Historico fecha = new Historico();
        fecha.setFecha(finitTest);
        fecha.setHistoricoid(1);
        fecha.setStatus((short)1);

        when(historicoRepository.getFechaInicial()).thenReturn(fecha);

        Historico responseTest = historicoService.getFecha(null, 'I');

        assertEquals(finitTest, responseTest.getFecha());
    }

    @Test
    public void getFechaTest2(){
        LocalDateTime finitTest = LocalDateTime.parse("2023-06-20T20:30:00", DateTimeFormatter.ISO_DATE_TIME);

        Historico fecha = new Historico();
        fecha.setFecha(finitTest);
        fecha.setHistoricoid(1);
        fecha.setStatus((short)1);

        when(historicoRepository.getFechaFinal()).thenReturn(fecha);

        Historico responseTest = historicoService.getFecha(null, 'F');

        assertEquals(finitTest, responseTest.getFecha());
    }
}
