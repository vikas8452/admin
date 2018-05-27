package com.hostelmanager.hosteladmin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hostelmanager.hosteladmin.Model.SendRecieveIssues;

import java.util.List;


public class MyItemRecyclerViewAdapter extends BaseAdapter {

    private final List<SendRecieveIssues> sendRecieveIssues;
    private final Context context;


    MyItemRecyclerViewAdapter(Context contex, List<SendRecieveIssues> items) {
        sendRecieveIssues = items;
        context = contex;
    }

    /*@Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.issues_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SendRecieveIssues ls = mValues.get(position);

        holder.tfroomno.setText(ls.getStatus());
        holder.tfissuetype.setText(ls.getType());
        holder.tfissuedesc.setText(ls.getDescription());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }*/

    @Override
    public int getCount() {
        return sendRecieveIssues.size();
    }

    @Override
    public Object getItem(int position) {
        return sendRecieveIssues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.d("dfdad","Enetred in getView");

        if(view==null)
            view=LayoutInflater.from(context).inflate(R.layout.issues_list_layout,parent,false);

        TextView tv1 = view.findViewById(R.id.roomno);
        TextView tv2 = view.findViewById(R.id.issuetype);
        TextView tv3 = view.findViewById(R.id.issuedescription);
        Button bt1 = view.findViewById(R.id.seen);
        Button bt2 = view.findViewById(R.id.resolved);
        LinearLayout linearLayout = view.findViewById(R.id.linearlayout);

        final SendRecieveIssues sendRecieveIssues = (SendRecieveIssues) getItem(position);



        tv1.setText(sendRecieveIssues.getRoomno());
        tv2.setText(sendRecieveIssues.getType());
        tv3.setText(sendRecieveIssues.getDescription());

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Issues").child("RP")
                        .child(sendRecieveIssues.getRoomno()).child(sendRecieveIssues.getUid()).child("status");

                db.setValue("1");
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Issues").child("RP")
                        .child(sendRecieveIssues.getRoomno()).child(sendRecieveIssues.getUid()).child("status");

                db.setValue("2");
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,sendRecieveIssues.getMobile(),Toast.LENGTH_SHORT).show();
                Intent in = new Intent(context,DescriptionView.class);
                in.putExtra("roomno",sendRecieveIssues.getRoomno());
                in.putExtra("isstype",sendRecieveIssues.getType());
                in.putExtra("issdesc",sendRecieveIssues.getDescription());
                in.putExtra("mobile",sendRecieveIssues.getMobile());
                in.putExtra("uid",sendRecieveIssues.getUid());
                context.startActivity(in);
            }
        });


        return view;
    }

    /*public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tfroomno,tfissuetype,tfissuedesc;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tfroomno = (TextView) view.findViewById(R.id.roomno);
            tfissuetype = (TextView) view.findViewById(R.id.issuetype);
            tfissuedesc = (TextView) view.findViewById(R.id.issuedescription);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tfroomno.getText() + "'"+ " '" + tfissuetype.getText() + "'"+ " '" + tfissuedesc.getText() + "'";
        }
    }*/
}
