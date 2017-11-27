package com.guilhermecardoso.buscapontos.app.resultadoscidade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.guilhermecardoso.buscapontos.R;
import com.guilhermecardoso.buscapontos.domain.repository.CidadeRepositoryImpl;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by guilhermecardoso on 11/26/17.
 */

public class ResultadosCidadeActivity extends AppCompatActivity {

    public static final String EXTRA_CIDADE_NOME = "EXTRA_CIDADE_NOME";
    public static final String EXTRA_ESTADO_NOME = "EXTRA_ESTADO_NOME";
    private static final String TAG = "ResultadosCidadeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent bundle = getIntent();

        String cidade = null;
        String estado = null;

        if (bundle != null) {
            cidade = bundle.getStringExtra(EXTRA_CIDADE_NOME);
            estado = bundle.getStringExtra(EXTRA_ESTADO_NOME);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupFragment(cidade, estado);
    }

    private void setupFragment(String cidade, String estado) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentByTag(TAG);

        if (fragment == null) {
            fragment = ResultadosCidadeFragment.newInstance(cidade, estado);
            ResultadosCidadeContract.Actions presenter = new ResultadosCidadePresenter((ResultadosCidadeFragment) fragment,
                    new CompositeDisposable(),
                    new CidadeRepositoryImpl());
            ((ResultadosCidadeFragment) fragment).setPresenter(presenter);
        }

        fragmentTransaction.replace(R.id.fragment, fragment, TAG);
        fragmentTransaction.commit();
    }

}
