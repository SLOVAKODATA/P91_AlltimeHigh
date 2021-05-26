package com.example.AllTimeExtreme.repository;

import com.example.AllTimeExtreme.model.SplitCennehoPapieraRealizacia;
import com.example.AllTimeExtreme.model.kluce.SplitCennehoPapieraRealizaciaPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface SplitCennehoPapieraRealizaciaRepo extends CrudRepository<SplitCennehoPapieraRealizacia, SplitCennehoPapieraRealizaciaPK> {

    @Query(value="(SELECT REALIZOVANE FROM SPLIT_CENNEHO_PAPIERA_REALIZACIA where ID_CENNEHO_PAPIERA = :idCennehoPapiera and DATE(REALIZOVANE)=DATE(NOW()) ) ", nativeQuery=true)
    public Date dnesnaRealizaciaStockSplitu(Integer idCennehoPapiera);

}
