package com.guilhermecardoso.buscapontos.app.resultadoscidade;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guilhermecardoso.buscapontos.R;
import com.guilhermecardoso.buscapontos.app.detalhescidade.DetailCidadeActivity;
import com.guilhermecardoso.buscapontos.app.resultadoscidade.adapter.ResultadoCidadeAdapter;
import com.guilhermecardoso.buscapontos.domain.entity.Cidade;

import java.util.List;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by guilhermecardoso on 11/26/17.
 */

public class ResultadosCidadeFragment extends Fragment implements ResultadosCidadeContract.View {

    private ResultadosCidadeContract.Actions presenter;
    private SweetAlertDialog dialog;
    private ResultadoCidadeAdapter adapter;
    private static String TAG = "ResultadosCidadeFragment";

    public static Fragment newInstance(String cidade, String estado) {
        ResultadosCidadeFragment instance = new ResultadosCidadeFragment();

        if (cidade != null && estado != null) {
            Bundle bundle = new Bundle();
            bundle.putString(ResultadosCidadeActivity.EXTRA_CIDADE_NOME, cidade);
            bundle.putString(ResultadosCidadeActivity.EXTRA_ESTADO_NOME, estado);
            instance.setArguments(bundle);
        }

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.resultados_cidades_fragment, container, false);
        ButterKnife.bind(this, rootView);

        RecyclerView recyclerView = ButterKnife.findById(rootView, R.id.recycler_view);
        adapter = new ResultadoCidadeAdapter(this.presenter);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (presenter != null) {
            String cidade = args != null ? args.getString(ResultadosCidadeActivity.EXTRA_CIDADE_NOME) : null;
            String estado = args != null ? args.getString(ResultadosCidadeActivity.EXTRA_ESTADO_NOME) : null;

            presenter.loadResults(cidade, estado);
        }

    }

    public void setPresenter(ResultadosCidadeContract.Actions presenter) {
        this.presenter = presenter;
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

    @Override
    public void navigateToDetails(Cidade cidadeAtual) {
        Intent intent = new Intent(getActivity(), DetailCidadeActivity.class);
        intent.putExtra(DetailCidadeActivity.EXTRA_CIDADE_NOME, cidadeAtual.getNome());
        intent.putExtra(DetailCidadeActivity.EXTRA_ESTADO_NOME, cidadeAtual.getEstado());

        startActivity(intent);

    }

    @Override
    public void showResults(List<Cidade> cidades) {
        adapter.addAll(cidades);
    }
}
