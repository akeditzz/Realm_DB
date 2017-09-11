package com.example.akshaymanagooli.realm_db;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.akshaymanagooli.realm_db.Model.CarsDB;
import com.example.akshaymanagooli.realm_db.Model.UserDB;
import com.example.akshaymanagooli.realm_db.Modules.RealmUserModule;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {

    Realm userRealm;
    EditText name,phone,carname,carno;
    int i;
    RelativeLayout progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);

        RealmConfiguration UserConfig = new RealmConfiguration.Builder()
                .name("User.realm")
                .schemaVersion(1)
                .modules(new RealmUserModule())
                .build();






        userRealm = Realm.getInstance(UserConfig);

//        realm.deleteAll();
        name = (EditText)findViewById(R.id.name);
        phone = (EditText)findViewById(R.id.phone);
        carname = (EditText)findViewById(R.id.et_carname);
        carno = (EditText)findViewById(R.id.et_carno);
        progressbar = (RelativeLayout) findViewById(R.id.progressbar);

    }


    public void click(View v){

        switch (v.getId()){
            case R.id.button:
                if (TextUtils.isEmpty(name.getText().toString())){
                    name.setError("Required");
                    name.requestFocus();
                }else if(TextUtils.isEmpty(phone.getText().toString())){
                    phone.setError("Required");
                    phone.requestFocus();
                }else if(TextUtils.isEmpty(carname.getText().toString())){
                    carname.setError("Required");
                    carname.requestFocus();
                }else if(TextUtils.isEmpty(carno.getText().toString())){
                    carno.setError("Required");
                    carno.requestFocus();
                }else {
                    progressbar.setVisibility(View.VISIBLE);
                    Random r = new Random();
                    i = r.nextInt(((9999-1000)+1)+1000);
                    userRealm.executeTransactionAsync(new Realm.Transaction() {
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


                            userRealm.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm nrealm) {

                                    CarsDB carsDB = nrealm.createObject(CarsDB.class);
                                    carsDB.setPhone(phone.getText().toString());
                                    carsDB.setCarname(carname.getText().toString());
                                    carsDB.setCarno(carno.getText().toString());

                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {
                                    name.setText("");
                                    phone.setText("");
                                    carname.setText("");
                                    carno.setText("");
                                    progressbar.setVisibility(View.GONE);
                                    Intent i = new Intent(MainActivity.this,View_DetailsActivity.class);
                                    startActivity(i);
                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable error) {

                                }
                            });


                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            progressbar.setVisibility(View.GONE);
                        }
                    });

                }

                RealmResults<UserDB> res = userRealm.where(UserDB.class).findAll();

                int j = res.size();
                for (int i=0;i<j;i++){
                    Log.e("Result: ",res.get(i).getId() +" "+res.get(i).getName()+" "+res.get(i).getPhone());
                }
                break;
            case R.id.button2:
                Intent i = new Intent(this,View_DetailsActivity.class);
                startActivity(i);
                break;
            case R.id.et_addcar:
                showChangeLangDialog();
                break;
        }



    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        final View dialogView = LayoutInflater.from(this).inflate(R.layout.car_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText phonee = (EditText) dialogView.findViewById(R.id.phone);
        final EditText carnamee = (EditText) dialogView.findViewById(R.id.carname);
        final EditText carnoo = (EditText) dialogView.findViewById(R.id.carno);


        dialogBuilder.setTitle("Add Car");
        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                RealmResults<UserDB> res = userRealm.where(UserDB.class).equalTo("phone",phonee.getText().toString()).findAll();
                if (res.size()>0){
                    userRealm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm mrealm) {
                            CarsDB carsDB = mrealm.createObject(CarsDB.class);
                            carsDB.setPhone(phonee.getText().toString());
                            carsDB.setCarname(carnamee.getText().toString());
                            carsDB.setCarno(carnoo.getText().toString());

                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            phonee.setText("");
                            carnamee.setText("");
                            carnoo.setText("");
                            dialog.dismiss();
                            Intent i = new Intent(MainActivity.this,View_DetailsActivity.class);
                            startActivity(i);


                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {

                        }
                    }); 
                }else{
                    Toast.makeText(MainActivity.this, "This User Dosent Exists", Toast.LENGTH_SHORT).show();
                }

                
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
                dialog.dismiss();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userRealm.close();

    }
}
