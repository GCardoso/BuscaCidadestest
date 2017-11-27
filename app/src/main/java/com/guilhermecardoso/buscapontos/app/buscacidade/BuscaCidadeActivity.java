package com.guilhermecardoso.buscapontos.app.buscacidade;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.guilhermecardoso.buscapontos.R;
import com.guilhermecardoso.buscapontos.domain.network.BuscaPontosAPIService;
import com.guilhermecardoso.buscapontos.domain.network.ServiceFactory;
import com.guilhermecardoso.buscapontos.domain.repository.CidadeRepositoryImpl;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by guilhermecardoso on 11/25/17.
 */

public class BuscaCidadeActivity extends AppCompatActivity {

    private static final String TAG = "BuscaCidadeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupFragment();
    }

    private void setupFragment() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentByTag(TAG);

        if (fragment == null) {
            fragment = BuscaCidadeFragment.newInstance();
            BuscaCidadeContract.Actions presenter = new BuscaCidadePresenter((BuscaCidadeFragment) fragment,
                    new CompositeDisposable(),
                    ServiceFactory.createService(BuscaPontosAPIService.class),
                    new CidadeRepositoryImpl());
            ((BuscaCidadeFragment) fragment).setPresenter(presenter);
        }

        fragmentTransaction.replace(R.id.fragment, fragment, TAG);
        fragmentTransaction.commit();
    }
}
