package com.guilhermecardoso.buscapontos.app.detalhescidade;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guilhermecardoso.buscapontos.R;
import com.guilhermecardoso.buscapontos.app.resultadoscidade.ResultadosCidadeActivity;
import com.guilhermecardoso.buscapontos.domain.entity.Cidade;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by guilhermecardoso on 11/26/17.
 */

public class DetailCidadeFragment extends Fragment implements DetailCidadeContract.View {

    private DetailCidadeContract.Actions presenter;
    private SweetAlertDialog dialog;
    private static String TAG = "ResultadosCidadeFragment";
    private String nome;
    private String estado;

    @BindView(R.id.textview_points)
     TextView textViewPoints;

    public static Fragment newInstance(String cidade, String estado) {
        DetailCidadeFragment instance = new DetailCidadeFragment();

        if (cidade != null && estado != null) {
            Bundle bundle = new Bundle();
            bundle.putString(DetailCidadeActivity.EXTRA_CIDADE_NOME, cidade);
            bundle.putString(DetailCidadeActivity.EXTRA_ESTADO_NOME, estado);
            instance.setArguments(bundle);
        }

        return instance;
    }

    public void setPresenter(DetailCidadeContract.Actions presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.details_cidade_fragment, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (presenter != null) {
            nome = args != null ? args.getString(ResultadosCidadeActivity.EXTRA_CIDADE_NOME) : null;
            estado = args != null ? args.getString(ResultadosCidadeActivity.EXTRA_ESTADO_NOME) : null;

            presenter.loadPoints(new Cidade(nome, estado));
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

    @Override
    public void showPoints(Integer points) {
        StringBuilder sb = new StringBuilder();
        sb.append("A pontuação da Cidade ");
        sb.append(this.nome);
        sb.append(" é ");

        DecimalFormat formatter = new DecimalFormat(
                "#,##0",
                new DecimalFormatSymbols(new Locale("pt", "BR")));
        sb.append(formatter.format(points));

        textViewPoints.setText(sb.toString());
    }
}
