package com.guilhermecardoso.buscapontos;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by guilhermecardoso on 11/25/17.
 */

public class BuscaPontosApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        final Realm realm = Realm.getDefaultInstance();

//        if (realm.isEmpty()) {
//            //Shall we download all the cities
//            BuscaPontosAPIService service = ServiceFactory.createService(BuscaPontosAPIService.class);
//            (new CompositeDisposable())
//            .add(service.listCidades()
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//            new Consumer<List<Cidade>>() {
//                @Override
//                public void accept(final List<Cidade> cidades) throws Exception {
//                    realm.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//                            RealmList<Cidade> listCidadesRealm = new RealmList<>();
//                            listCidadesRealm.addAll(cidades);
//                            realm.insertOrUpdate(listCidadesRealm);
//                        }
//                    });
//                }
//            }, new Consumer<Throwable>() {
//                @Override
//                public void accept(Throwable throwable) throws Exception {
//                    //Need to erase all and show message error
//                    realm.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//                            realm.delete(Cidade.class);
//                        }
//                    });
//
//                    SweetAlertDialog dialog = new SweetAlertDialog(BuscaPontosApplication.this, SweetAlertDialog.ERROR_TYPE);
//                    dialog.setTitleText("Some error occurred, please try again");
//                    dialog.setCancelable(true);
////                    dialog.show();
//                }
//            }));
//        }
    }
}
