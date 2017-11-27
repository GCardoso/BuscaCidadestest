package com.guilhermecardoso.buscapontos.app.buscacidade;

import com.guilhermecardoso.buscapontos.app.BaseActions;

import java.util.List;

/**
 * Created by guilhermecardoso on 11/25/17.
 */

public interface BuscaCidadeContract {

    interface View {
        void showLoading();
        void hideLoading();
        void showError();
        void navigateToSearchResults();
        void showEstados(List<String> estados);
        void showCitiesSuggestions(List<String> cidades);
    }

    interface Actions extends BaseActions {
        void loadCities();
        void loadCitiesFromState(String estado);
    }
}
