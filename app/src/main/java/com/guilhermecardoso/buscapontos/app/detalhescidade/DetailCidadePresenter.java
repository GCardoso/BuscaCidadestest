package com.guilhermecardoso.buscapontos.app.detalhescidade;

import com.guilhermecardoso.buscapontos.domain.entity.Cidade;
import com.guilhermecardoso.buscapontos.domain.network.BuscaPontosAPIService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by guilhermecardoso on 11/26/17.
 */

public class DetailCidadePresenter implements DetailCidadeContract.Actions {

    private DetailCidadeContract.View view;
    private CompositeDisposable compositeDisposable;
    private BuscaPontosAPIService service;

    public DetailCidadePresenter(DetailCidadeContract.View view, CompositeDisposable compositeDisposable, BuscaPontosAPIService service) {
        this.view = view;
        this.compositeDisposable = compositeDisposable;
        this.service = service;
    }

    @Override
    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    @Override
    public void loadPoints(Cidade cidade) {
        view.showLoading();

        compositeDisposable.add(service.loadPoints(cidade).retry(3)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer points) throws Exception {
                view.hideLoading();
                view.showPoints(points);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                view.hideLoading();
                view.showError();
                throwable.printStackTrace();
            }
        }));
    }
}
