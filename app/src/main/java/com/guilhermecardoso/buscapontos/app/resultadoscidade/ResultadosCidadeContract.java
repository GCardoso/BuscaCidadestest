package com.guilhermecardoso.buscapontos.app.resultadoscidade;

import com.guilhermecardoso.buscapontos.app.BaseActions;
import com.guilhermecardoso.buscapontos.domain.entity.Cidade;

import java.util.List;

/**
 * Created by guilhermecardoso on 11/26/17.
 */

public interface ResultadosCidadeContract {

    interface View {
        void showLoading();
        void hideLoading();
        void showError();
        void navigateToDetails(Cidade cidadeAtual);
        void showResults(List<Cidade> cidades);
    }

    interface Actions extends BaseActions {
        void loadResults(String nome, String estado);
        void loadDetails(Cidade cidadeAtual);
    }
}
