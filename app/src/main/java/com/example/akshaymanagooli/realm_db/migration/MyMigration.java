package com.example.akshaymanagooli.realm_db.migration;

import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by Akshay.Managooli on 11/28/2017.
 */

public class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        // DynamicRealm exposes an editable schema
        Log.e("OLDVERSION", String.valueOf(oldVersion));
        RealmSchema schema = realm.getSchema();



        if (oldVersion == 6) {
            try {
                RealmObjectSchema personSchema = schema.get("CarsDB");

                personSchema.removeField("carno");
            } catch (Exception e) {
                e.printStackTrace();
            }

            oldVersion++;
        }




    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof MyMigration);
    }

}
