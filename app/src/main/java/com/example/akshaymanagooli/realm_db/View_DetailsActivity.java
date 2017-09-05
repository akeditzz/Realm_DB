package com.example.akshaymanagooli.realm_db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.akshaymanagooli.realm_db.adapter.RealmAdapter;

import io.realm.Realm;
import io.realm.RealmResults;

public class View_DetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RealmAdapter realmAdapter;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__details);
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RealmResults<UserDB> res = realm.where(UserDB.class).findAll();
        if (res.size()==0){
            Toast.makeText(this, "No Data!", Toast.LENGTH_SHORT).show();
        }

        realmAdapter = new RealmAdapter(res,this,realm);

        recyclerView.setAdapter(realmAdapter);

    }
}
