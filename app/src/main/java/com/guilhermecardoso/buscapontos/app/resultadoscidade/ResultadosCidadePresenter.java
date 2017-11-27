package com.guilhermecardoso.buscapontos.app.resultadoscidade;

import com.guilhermecardoso.buscapontos.domain.entity.Cidade;
import com.guilhermecardoso.buscapontos.domain.repository.CidadeRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by guilhermecardoso on 11/26/17.
 */

class ResultadosCidadePresenter implements ResultadosCidadeContract.Actions {

    private ResultadosCidadeContract.View view;
    private CompositeDisposable compositeDisposable;
    private CidadeRepository cidadeRepository;

    public ResultadosCidadePresenter(ResultadosCidadeContract.View view, CompositeDisposable compositeDisposable, CidadeRepository cidadeRepository) {
        this.view = view;
        this.compositeDisposable = compositeDisposable;
        this.cidadeRepository = cidadeRepository;
    }

    @Override
    public void loadResults(String nome, String estado) {
        view.showLoading();
        compositeDisposable.add(cidadeRepository.findByNameAndEstado(nome, estado)
        .subscribeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Cidade>>() {
            @Override
            public void accept(List<Cidade> cidades) throws Exception {
                view.hideLoading();
                view.showResults(cidades);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                view.hideLoading();
                throwable.printStackTrace();
                view.showError();
            }
        }));
    }

    @Override
    public void loadDetails(Cidade cidadeAtual) {
        view.navigateToDetails(cidadeAtual);
    }

    @Override
    public void dispose() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
