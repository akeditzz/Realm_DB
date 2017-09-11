package com.example.akshaymanagooli.realm_db.Modules;

import com.example.akshaymanagooli.realm_db.Model.CarsDB;
import com.example.akshaymanagooli.realm_db.Model.UserDB;

import io.realm.annotations.RealmModule;

/**
 * Created by Akshay.Managooli on 9/8/2017.
 */
@RealmModule(classes = {UserDB.class, CarsDB.class})
public class RealmUserModule {
}
