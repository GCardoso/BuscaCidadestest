package com.guilhermecardoso.buscapontos.app.detalhescidade;

import com.guilhermecardoso.buscapontos.app.BaseActions;
import com.guilhermecardoso.buscapontos.domain.entity.Cidade;

/**
 * Created by guilhermecardoso on 11/26/17.
 */

public interface DetailCidadeContract {

    interface View {
        void showLoading();
        void hideLoading();
        void showError();
        void showPoints(Integer points);
    }

    interface Actions extends BaseActions {
        void loadPoints(Cidade cidade);
    }

}
