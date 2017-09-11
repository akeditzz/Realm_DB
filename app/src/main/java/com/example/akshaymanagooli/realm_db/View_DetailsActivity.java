package com.example.akshaymanagooli.realm_db;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.akshaymanagooli.realm_db.Model.UserDB;
import com.example.akshaymanagooli.realm_db.RealmHelper.HelperClass;
import com.example.akshaymanagooli.realm_db.adapter.RealmAdapter;

import io.realm.Realm;
import io.realm.RealmResults;

public class View_DetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public RealmAdapter realmAdapter;
    Realm userRealm,carRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__details);
        Realm.init(this);







        userRealm = HelperClass.getInstance().getRealm();


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RealmResults<UserDB> res = HelperClass.getInstance().getAllUsers();
        if (res.size()==0){
            Toast.makeText(this, "No Data!", Toast.LENGTH_SHORT).show();
        }

        realmAdapter = new RealmAdapter(res,this,userRealm);

        recyclerView.setAdapter(realmAdapter);

    }
}
