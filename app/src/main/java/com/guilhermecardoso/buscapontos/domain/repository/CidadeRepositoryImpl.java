package com.guilhermecardoso.buscapontos.domain.repository;

import com.guilhermecardoso.buscapontos.domain.entity.Cidade;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by guilhermecardoso on 11/25/17.
 */

public class CidadeRepositoryImpl implements CidadeRepository {

    @Override
    public void saveAll(final List objects) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmList<Cidade> cidadeRealmList = new RealmList<>();
                cidadeRealmList.addAll(objects);
                realm.copyToRealm(cidadeRealmList);
            }
        });
        realm.close();
    }


    @Override
    public Flowable<List<Cidade>> findAll() {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(Cidade.class).findAll()
            .asFlowable()
            .flatMap(new Function<RealmResults<Cidade>, Publisher<List<Cidade>>>() {
                @Override
                public Publisher<List<Cidade>> apply(RealmResults<Cidade> cidades) throws Exception {
                    return Flowable.just(realm.copyFromRealm(cidades));
                }
            });
    }

    @Override
    public Flowable<List<String>> findAllEstados() {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(Cidade.class)
                .distinct("estado")
                .asFlowable()
                .flatMap(new Function<RealmResults<Cidade>, Publisher<List<Cidade>>>() {
                    @Override
                    public Publisher<List<Cidade>> apply(RealmResults<Cidade> estados) throws Exception {
                        return Flowable.just(realm.copyFromRealm(estados));
                    }
                })
                .flatMap(new Function<List<Cidade>, Publisher<List<String>>>() {
                    @Override
                    public Publisher<List<String>> apply(List<Cidade> estados) throws Exception {
                        List<String> names = new ArrayList<>();
                        for (Cidade estado : estados) {
                            names.add(estado.getEstado());
                        }
                        return Flowable.just(names);
                    }
                });
    }

    @Override
    public Flowable<List<String>> findAllNames() {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(Cidade.class).findAll()
                .asFlowable()
                .flatMap(new Function<RealmResults<Cidade>, Publisher<List<Cidade>>>() {
                    @Override
                    public Publisher<List<Cidade>> apply(RealmResults<Cidade> cidades) throws Exception {
                        return Flowable.just(realm.copyFromRealm(cidades));
                    }
                }).flatMap(new Function<List<Cidade>, Publisher<List<String>>>() {
                    @Override
                    public Publisher<List<String>> apply(List<Cidade> cidades) throws Exception {
                        List<String> names = new ArrayList();
                        for (Cidade cidade : cidades) {
                            names.add(cidade.getNome());
                        }
                        return Flowable.just(names);
                    }
                });
    }

    @Override
    public Completable deleteAll() {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(Cidade.class);
                    }
                });
            }
        });
    }

    @Override
    public Flowable<List<Cidade>> findByEstado(String estado) {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(Cidade.class)
            .like("estado", estado)
            .findAll()
            .asFlowable()
            .flatMap(new Function<RealmResults<Cidade>, Publisher<List<Cidade>>>() {
                @Override
                public Publisher<List<Cidade>> apply(RealmResults<Cidade> cidades) throws Exception {
                    return Flowable.just(realm.copyFromRealm(cidades));
                }
            });
    }

    @Override
    public Flowable<List<Cidade>> findByName(String name) {
        final Realm realm = Realm.getDefaultInstance();
        return realm.where(Cidade.class)
                .like("nome", name)
                .findAll()
                .asFlowable()
                .flatMap(new Function<RealmResults<Cidade>, Publisher<List<Cidade>>>() {
                    @Override
                    public Publisher<List<Cidade>> apply(RealmResults<Cidade> cidades) throws Exception {
                        return Flowable.just(realm.copyFromRealm(cidades));
                    }
                });
    }

    @Override
    public Completable delete(final long id) {
        final Realm realm = Realm.getDefaultInstance();
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter e) throws Exception {
                realm.where(Cidade.class).equalTo("id", id).findAll().deleteAllFromRealm();
            }
        });
    }

    @Override
    public boolean isEmpty() {
        return Realm.getDefaultInstance().isEmpty();
    }

    @Override
    public Flowable<List<Cidade>> findByNameAndEstado(String name, String estado) {
        final Realm realm = Realm.getDefaultInstance();
        RealmQuery query = realm.where(Cidade.class);

        if (name != null && !name.equals("")) {
            query.contains("nome", name, Case.INSENSITIVE);
        }

        if (estado != null && estado.compareTo("") != 0) {
            query.contains("estado", estado, Case.INSENSITIVE);
        }

        return query
                .findAll()
                .asFlowable()
                .flatMap(new Function<RealmResults<Cidade>, Publisher<List<Cidade>>>() {
                    @Override
                    public Publisher<List<Cidade>> apply(RealmResults<Cidade> cidades) throws Exception {
                        return Flowable.just(realm.copyFromRealm(cidades));
                    }
                });
    }

    @Override
    public Flowable<List<String>> findAllNamesByEstado(String estado) {
        final Realm realm = Realm.getDefaultInstance();
        RealmQuery query = realm.where(Cidade.class);

        if (estado != null && estado.compareTo("") != 0) {
            query.equalTo("estado", estado);
        }

        return query.findAll()
                .asFlowable()
                .flatMap(new Function<RealmResults<Cidade>, Publisher<List<Cidade>>>() {
                    @Override
                    public Publisher<List<Cidade>> apply(RealmResults<Cidade> cidades) throws Exception {
                        return Flowable.just(realm.copyFromRealm(cidades));
                    }
                }).flatMap(new Function<List<Cidade>, Publisher<List<String>>>() {
                    @Override
                    public Publisher<List<String>> apply(List<Cidade> cidades) throws Exception {
                        List<String> names = new ArrayList();
                        for (Cidade cidade : cidades) {
                            names.add(cidade.getNome());
                        }
                        return Flowable.just(names);
                    }
                });
    }
}
