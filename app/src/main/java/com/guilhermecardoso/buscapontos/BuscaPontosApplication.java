package com.guilhermecardoso.buscapontos;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by guilhermecardoso on 11/25/17.
 */

public class BuscaPontosApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
