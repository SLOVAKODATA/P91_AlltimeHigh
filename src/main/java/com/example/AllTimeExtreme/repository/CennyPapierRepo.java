package com.example.AllTimeExtreme.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.AllTimeExtreme.model.Cenny_papier;

@Repository
public interface CennyPapierRepo extends CrudRepository<Cenny_papier, Integer> {

}
