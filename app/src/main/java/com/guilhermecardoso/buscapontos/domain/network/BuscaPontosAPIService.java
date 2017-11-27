package com.guilhermecardoso.buscapontos.domain.network;

import com.guilhermecardoso.buscapontos.domain.entity.Cidade;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by guilhermecardoso on 11/25/17.
 */

public interface BuscaPontosAPIService {
    String SERVICE_ENDPOINT = "http://wsteste.devedp.com.br/Master/CidadeServico.svc/rest/";
    String SERVICE_TOKEN = null;

    @GET("BuscaTodasCidades")
    Observable<List<Cidade>> listCidades();

    @POST("BuscaPontos")
    Observable<Integer> loadPoints(@Body Cidade cidade);
}
