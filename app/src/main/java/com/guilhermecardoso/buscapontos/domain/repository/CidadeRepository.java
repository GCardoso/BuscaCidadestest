package com.guilhermecardoso.buscapontos.domain.repository;

import com.guilhermecardoso.buscapontos.domain.entity.Cidade;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by guilhermecardoso on 11/25/17.
 */

public interface CidadeRepository extends Repository {

    Flowable<List<String>> findAllNames();
    Flowable<List<String>> findAllNamesByEstado(String estado);

    Flowable<List<Cidade>> findByEstado(String estado);
    Flowable<List<Cidade>> findByName(String name);
    Flowable<List<Cidade>> findByNameAndEstado(String name, String estado);
    Flowable<List<Cidade>> findAll();

    Flowable<List<String>> findAllEstados();
}
