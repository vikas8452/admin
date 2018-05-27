package com.hostelmanager.hosteladmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hostelmanager.hosteladmin.Model.ConfStatus;
import com.hostelmanager.hosteladmin.Model.HostelerInfo;

import java.util.ArrayList;
import java.util.List;


public class NonVerifiedAdapter  extends BaseAdapter{
    private List<ConfStatus> confStatuses;
    private final Context context;

    NonVerifiedAdapter(Context context, List<ConfStatus> confStatuses) {
        this.confStatuses = confStatuses;
        this.context = context;
    }

    @Override
    public int getCount() {
        return confStatuses.size();
    }

    @Override
    public Object getItem(int position) {
        return confStatuses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return confStatuses.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.d("dfdad","Enetred in getView");

        if(view==null)
            view= LayoutInflater.from(context).inflate(R.layout.non_verified,parent,false);

        final TextView tv1 = view.findViewById(R.id.nvname);
        final TextView tv2 = view.findViewById(R.id.nvroomno);
        TextView tv3 = view.findViewById(R.id.nvmobile);
        Button bt1 = view.findViewById(R.id.nvconfirm);
        Button bt2 = view.findViewById(R.id.nvdelete);
        final RelativeLayout rl = view.findViewById(R.id.vloadingdata);

        //LinearLayout linearLayout = view.findViewById(R.id.nvlinearlayout);

        final ConfStatus confStatus = (ConfStatus) getItem(position);

        final String mob = confStatus.getMobile();
        tv3.setText(mob);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Students").child(mob);

        final HostelerInfo[] hostelerInfo = new HostelerInfo[1];
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                hostelerInfo[0] = dataSnapshot.getValue(HostelerInfo.class);

                assert hostelerInfo[0] != null;
                tv1.setText(hostelerInfo[0].getName());
                tv2.setText(hostelerInfo[0].getRoomno());
                rl.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                        .child("Hostels").child(hostelerInfo[0].getHostel()).child(hostelerInfo[0].getRoomno()).child(mob);
                notifyDataSetChanged();
                db.child("stat").setValue("2");
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                        .child("Hostels").child(hostelerInfo[0].getHostel()).child(hostelerInfo[0].getRoomno()).child(mob);

                final AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setMessage("Are you sure");
                alertbox.setTitle("Delete");

                alertbox.setPositiveButton("CONFIRM",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                db.child("stat").setValue("3");
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                alertbox.show();
            }
        });
       /* linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context,StudentDetail.class);
                in.putExtra("mobile",mob);
                context.startActivity(in);

            }
        });*/
        return view;

    }
}
