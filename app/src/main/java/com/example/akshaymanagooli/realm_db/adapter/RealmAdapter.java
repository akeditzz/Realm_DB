package com.example.akshaymanagooli.realm_db.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akshaymanagooli.realm_db.R;
import com.example.akshaymanagooli.realm_db.UserDB;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Akshay.Managooli on 9/5/2017.
 */

public class RealmAdapter extends RecyclerView.Adapter<RealmAdapter.ViewHolder> {

    RealmResults<UserDB> list;
    Context context;
    Realm realm;

    public RealmAdapter(RealmResults<UserDB> list, Context context,Realm realm) {
        this.list = list;
        this.context = context;
        this.realm = realm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.realm_view_row,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.name.setText(list.get(position).getName());
        holder.phone.setText(list.get(position).getPhone());
        holder.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = list.get(position).getId();
                final RealmResults<UserDB> list2 = realm.where(UserDB.class).equalTo("id",id).findAll();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        list2.deleteFromRealm(0);
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

        TextView name,phone;
        ImageView clear;

        public ViewHolder(View itemView)  {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView)itemView.findViewById(R.id.name);
            phone = (TextView)itemView.findViewById(R.id.phone);
            clear = (ImageView)itemView.findViewById(R.id.clear);
        }

        @Override
        public void onClick(View view) {

            showChangeLangDialog(getAdapterPosition());
        }
    }

    public void showChangeLangDialog(int pos) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        final View dialogView = LayoutInflater.from(context).inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText namee = (EditText) dialogView.findViewById(R.id.name);
        final EditText phonee = (EditText) dialogView.findViewById(R.id.phone);
        int id = list.get(pos).getId();
        final RealmResults<UserDB> list2 = realm.where(UserDB.class).equalTo("id",id).findAll();
        namee.setText(list2.get(0).getName());
        phonee.setText(list2.get(0).getPhone());

        dialogBuilder.setTitle("Update");
        dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        list2.get(0).setName(namee.getText().toString());
                        list2.get(0).setPhone(phonee.getText().toString());
                        notifyDataSetChanged();
                    }
                });
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

}
