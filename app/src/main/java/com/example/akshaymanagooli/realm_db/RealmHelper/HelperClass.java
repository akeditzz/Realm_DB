package com.example.akshaymanagooli.realm_db.RealmHelper;

import android.content.Context;

import com.example.akshaymanagooli.realm_db.Model.CarsDB;
import com.example.akshaymanagooli.realm_db.Model.UserDB;
import com.example.akshaymanagooli.realm_db.Modules.RealmUserModule;
import com.example.akshaymanagooli.realm_db.View_DetailsActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Akshay.Managooli on 9/8/2017.
 */

public class HelperClass {

    public Realm realm;
    public static HelperClass instance;
    public RealmConfiguration UserConfig = new RealmConfiguration.Builder()
            .name("User.realm")
            .schemaVersion(1)
            .modules(new RealmUserModule())
            .build();

    public HelperClass(){
        realm = Realm.getInstance(UserConfig);

    }

    public Realm getRealm() {
        return realm;
    }



    public static HelperClass getInstance(){
        if (instance == null){
            instance = new HelperClass();

        }
        return instance;
    }


    public RealmResults<UserDB> getAllUsers(){

        return realm.where(UserDB.class).findAll();

    }
    public RealmResults<UserDB> getUsers(int id){

        return realm.where(UserDB.class).equalTo("id",id).findAll();

    }

    public RealmResults<CarsDB> getCars(RealmResults<UserDB> list){

        return realm.where(CarsDB.class).equalTo("phone",list.get(0).getPhone()).findAll();

    }


    public void DeleteUser(int id, final Context context){
        final RealmResults<UserDB> list2 = realm.where(UserDB.class).equalTo("id",id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                list2.deleteFromRealm(0);
                ((View_DetailsActivity)context).realmAdapter.notifyDataSetChanged();

            }
        });
    }



}
