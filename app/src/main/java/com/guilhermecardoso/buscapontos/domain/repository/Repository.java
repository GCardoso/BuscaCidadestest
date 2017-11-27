package com.guilhermecardoso.buscapontos.domain.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by guilhermecardoso on 11/25/17.
 */

public interface Repository<E extends Object> {

    void saveAll(List<E> objects);
    Flowable<List<E>> findAll();
    Completable deleteAll();
    Completable delete(long id);

    boolean isEmpty();
}
