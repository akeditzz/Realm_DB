package com.example.akshaymanagooli.realm_db;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {

    Realm realm;
    EditText name,phone;
    int i;
    RelativeLayout progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);




        realm = Realm.getDefaultInstance();
//        realm.deleteAll();
        name = (EditText)findViewById(R.id.name);
        phone = (EditText)findViewById(R.id.phone);
        progressbar = (RelativeLayout) findViewById(R.id.progressbar);

    }


    public void click(View v){

        switch (v.getId()){
            case R.id.button:
                if (TextUtils.isEmpty(name.getText().toString())){
                    name.setError("Requires");
                    name.requestFocus();
                }else if(TextUtils.isEmpty(phone.getText().toString())){
                    phone.setError("Required");
                    phone.requestFocus();
                }else {
                    progressbar.setVisibility(View.VISIBLE);
                    Random r = new Random();
                    i = r.nextInt(((9999-1000)+1)+1000);
                    realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm mrealm) {
//
//                            mrealm.beginTransaction();
                            UserDB userDB = mrealm.createObject(UserDB.class,i);
//            userDB.setId(i);
                            userDB.setName(name.getText().toString());
                            userDB.setPhone(phone.getText().toString());
//                            mrealm.commitTransaction();

                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            name.setText("");
                            phone.setText("");
                            progressbar.setVisibility(View.GONE);
                            Intent i = new Intent(MainActivity.this,View_DetailsActivity.class);
                            startActivity(i);
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            progressbar.setVisibility(View.GONE);
                        }
                    });

                }

                RealmResults<UserDB> res = realm.where(UserDB.class).findAll();

                int j = res.size();
                for (int i=0;i<j;i++){
                    Log.e("Result: ",res.get(i).getId() +" "+res.get(i).getName()+" "+res.get(i).getPhone());
                }
                break;
            case R.id.button2:
                Intent i = new Intent(this,View_DetailsActivity.class);
                startActivity(i);
                break;
        }



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
