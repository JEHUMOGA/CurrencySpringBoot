package com.corrency.divisas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.corrency.divisas.models.ValuesCurrencies;

public interface ValueCurrencyRepository extends CrudRepository<ValuesCurrencies, Integer>{

    @Query(value = "SELECT valuesid, historicoid, code, valuecurrency FROM valuescurrencies WHERE historicoid = :historicoid AND code = :code",
        nativeQuery = true)
    public List<ValuesCurrencies> getValuesCurrency(@Param("historicoid") int historicoid, @Param("code") String code);

    @Query(value = "SELECT valuesid, historicoid, code, valuecurrency FROM valuescurrencies WHERE historicoid = :historicoid",
        nativeQuery = true)
    public List<ValuesCurrencies> getValuesCurrency(@Param("historicoid") int historicoid);

}
