package com.corrency.divisas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.corrency.divisas.models.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Integer>{
    @Query(value = "SELECT historicoid,fecha, tiemporespuesta, status FROM historicocurrencies WHERE fecha = :fecha AND status = 1" , nativeQuery = true)
    public Historico getFecha(@Param("fecha") LocalDateTime fecha);

    @Query(value = "SELECT historicoid,fecha, tiemporespuesta, status FROM historicocurrencies WHERE fecha = :fecha AND status = 1" , nativeQuery = true)
    public boolean getExistFecha(@Param("fecha") LocalDateTime fecha);

    @Query(value = "SELECT historicoid,fecha, tiemporespuesta, status FROM historicocurrencies WHERE status = 1 Order by fecha asc limit 1" , nativeQuery = true)
    public Historico getFechaInicial();

    @Query(value = "SELECT historicoid,fecha, tiemporespuesta, status FROM historicocurrencies WHERE status = 1 Order by fecha desc limit 1" , nativeQuery = true)
    public Historico getFechaFinal();

    @Query(value = "SELECT historicoid,fecha, tiemporespuesta, status FROM historicocurrencies WHERE fecha BETWEEN :finit AND :fend AND status = 1" , nativeQuery = true)
    public List<Historico> getFechasBetween(@Param("finit") LocalDateTime finit, @Param("fend") LocalDateTime fend);

    

}
