package com.guilhermecardoso.buscapontos.app.buscacidade;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.guilhermecardoso.buscapontos.R;
import com.guilhermecardoso.buscapontos.app.resultadoscidade.ResultadosCidadeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by guilhermecardoso on 11/25/17.
 */

public class BuscaCidadeFragment extends Fragment implements BuscaCidadeContract.View, AdapterView.OnItemSelectedListener {

    private BuscaCidadeContract.Actions presenter;
    private SweetAlertDialog dialog;
    private static String TAG = "BuscaCidadeFragment";
    private ArrayAdapter<String> adapter;

    @BindView(R.id.estado_spineer)
    Spinner spinnerEstado;
    @BindView(R.id.auto_complete_cidade)
    AutoCompleteTextView autoCompleteCidade;

    public static BuscaCidadeFragment newInstance() {
        return new BuscaCidadeFragment();
    }

    public void setPresenter(BuscaCidadeContract.Actions presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.busca_cidades_fragment, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (presenter != null) {
            presenter.loadCities();
        }

    }


    @Override
    public void showLoading() {
        FragmentActivity activity = getActivity();
        if (dialog == null && activity != null) {
            dialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
            dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            dialog.setTitleText("Loading");
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (dialog != null) {
            dialog.dismissWithAnimation();
            dialog = null;
        }
    }

    @Override
    public void showError() {
        FragmentActivity activity = getActivity();
        if (dialog == null && activity != null) {
            dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
            dialog.setTitleText("Some error occurred");
            dialog.setCancelable(true);
            dialog.show();
        }
    }

    @OnClick(R.id.button_search)
    @Override
    public void navigateToSearchResults() {
        String cidade = autoCompleteCidade.getText().toString();
        String estado = spinnerEstado.getSelectedItem().toString();

        if (estado.compareTo("Selecione") == 0) {
            estado = "";
        }

        Intent intent = new Intent(getActivity(), ResultadosCidadeActivity.class);
        intent.putExtra(ResultadosCidadeActivity.EXTRA_CIDADE_NOME, cidade);
        intent.putExtra(ResultadosCidadeActivity.EXTRA_ESTADO_NOME, estado);

        startActivity(intent);
    }

    @Override
    public void showEstados(List<String> estados) {
        estados.add(0, "Selecione");
        ArrayAdapter estadosAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, estados);
        spinnerEstado.setAdapter(estadosAdapter);
    }

    @Override
    public void showCitiesSuggestions(List<String> cidades) {
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_dropdown_item_1line, cidades);

        this.autoCompleteCidade.setAdapter(adapter);
        spinnerEstado.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String estado = spinnerEstado.getSelectedItem().toString();
        if (estado.compareTo("Selecione") == 0) {
            estado = "";
        }
        presenter.loadCitiesFromState(estado);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
