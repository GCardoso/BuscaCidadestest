package com.guilhermecardoso.buscapontos.domain.entity;

import io.realm.RealmObject;

/**
 * Created by guilhermecardoso on 11/25/17.
 */

public class Estado extends RealmObject{

    String name;

    public Estado(String name) {
        this.name = name;
    }

    public Estado() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
