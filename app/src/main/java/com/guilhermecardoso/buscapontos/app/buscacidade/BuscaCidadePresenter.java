package com.guilhermecardoso.buscapontos.app.buscacidade;

import android.util.Log;

import com.guilhermecardoso.buscapontos.domain.entity.Cidade;
import com.guilhermecardoso.buscapontos.domain.network.BuscaPontosAPIService;
import com.guilhermecardoso.buscapontos.domain.repository.CidadeRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by guilhermecardoso on 11/25/17.
 */

public class BuscaCidadePresenter implements BuscaCidadeContract.Actions {

    private BuscaCidadeContract.View view;
    private CompositeDisposable compositeDisposable;
    private BuscaPontosAPIService service;
    private CidadeRepository cidadeRepository;

    public BuscaCidadePresenter(BuscaCidadeContract.View view, CompositeDisposable compositeDisposable, BuscaPontosAPIService service, CidadeRepository cidadeRepository) {
        this.view = view;
        this.compositeDisposable = compositeDisposable;
        this.service = service;
        this.cidadeRepository = cidadeRepository;
    }

    @Override
    public void dispose() {
        if(!compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }

    @Override
    public void loadCities() {
        view.showLoading();
        if (cidadeRepository.isEmpty()) {
            compositeDisposable.add(
                    service.listCidades().retry(5)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<Cidade>>() {
                        @Override
                        public void accept(List<Cidade> cidades) throws Exception {
                            cidadeRepository.saveAll(cidades);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.i("App", throwable.getMessage());
                        }
                    }));
        }

        compositeDisposable
            .add(cidadeRepository.findAllEstados()
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<String>>() {
                        @Override
                        public void accept(List<String> estados) throws Exception {
                            view.showEstados(estados);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace();
                            view.showError();
                        }
                    })
            );

        compositeDisposable.add(cidadeRepository.findAllNames()
        .subscribeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> cidades) throws Exception {
                view.hideLoading();
                view.showCitiesSuggestions(cidades);
            }
        }));
    }

    @Override
    public void loadCitiesFromState(String estado) {
        view.showLoading();
        compositeDisposable.add(cidadeRepository.findAllNamesByEstado(estado)
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> cidades) throws Exception {
                        view.hideLoading();
                        view.showCitiesSuggestions(cidades);
                    }
                }));
    }
}
