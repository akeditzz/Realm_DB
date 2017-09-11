package com.example.akshaymanagooli.realm_db.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akshaymanagooli.realm_db.Model.CarsDB;
import com.example.akshaymanagooli.realm_db.R;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Akshay.Managooli on 9/5/2017.
 */

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {

    RealmResults<CarsDB> list;
    Context context;
    Realm carRealm;

    public CarsAdapter(RealmResults<CarsDB> list, Context context, Realm carRealm) {
        this.list = list;
        this.context = context;
        this.carRealm = carRealm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.realm_cars_row,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Log.e("dafsdfd",list.get(position).getCarname());

        holder.name.setText(list.get(position).getCarname());
        holder.no.setText(list.get(position).getCarno());
        holder.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = list.get(position).getPhone();
                final RealmResults<CarsDB> list2 = carRealm.where(CarsDB.class).equalTo("phone",id).findAll();
                carRealm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        list2.deleteFromRealm(position);
                        notifyDataSetChanged();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name,no;
        ImageView clear;

        public ViewHolder(View itemView)  {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView)itemView.findViewById(R.id.name);
            no = (TextView)itemView.findViewById(R.id.no);
            clear = (ImageView)itemView.findViewById(R.id.clear);
        }

        @Override
        public void onClick(View view) {

//            showChangeLangDialog(getAdapterPosition());
        }
    }

//    public void showChangeLangDialog(int pos) {
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
//
//        final View dialogView = LayoutInflater.from(context).inflate(R.layout.update_dialog, null);
//        dialogBuilder.setView(dialogView);
//
//        final EditText namee = (EditText) dialogView.findViewById(R.id.name);
//        final EditText phonee = (EditText) dialogView.findViewById(R.id.phone);
//        String id = list.get(pos).getPhone();
//        final RealmResults<CarsDB> list2 = realm.where(CarsDB.class).equalTo("id",id).findAll();
//        namee.setText(list2.get(0).getName());
//        phonee.setText(list2.get(0).getPhone());
//
//        dialogBuilder.setTitle("Update");
//        dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //do something with edt.getText().toString();
//                realm.executeTransaction(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//                        list2.get(0).setName(namee.getText().toString());
//                        list2.get(0).setPhone(phonee.getText().toString());
//                        notifyDataSetChanged();
//                    }
//                });
//            }
//        });
//        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                //pass
//                dialog.dismiss();
//            }
//        });
//        AlertDialog b = dialogBuilder.create();
//        b.show();
//    }

}
