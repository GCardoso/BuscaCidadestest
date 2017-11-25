package com.guilhermecardoso.buscapontos.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by guilhermecardoso on 11/25/17.
 */

public class Cidade {

    @SerializedName("Nome")
    private String nome;
    @SerializedName("Estado")
    private String estado;

    public Cidade(String nome, String estado) {
        this.nome = nome;
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
