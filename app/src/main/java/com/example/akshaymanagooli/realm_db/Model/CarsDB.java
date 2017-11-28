package com.example.akshaymanagooli.realm_db.Model;

import io.realm.RealmObject;

/**
 * Created by Akshay.Managooli on 9/8/2017.
 */

public class CarsDB extends RealmObject {


    String phone;

    String carname;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }
}
