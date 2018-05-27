package com.hostelmanager.hosteladmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hostelmanager.hosteladmin.Model.HostelerInfo;

public class DescriptionView extends AppCompatActivity {

    private TextView room,type,desc,name,mobile;
    Button bt1,bt2,bt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_view);

        final String s1 = getIntent().getStringExtra("roomno");
        String s2 = getIntent().getStringExtra("isstype");
        String s3 = getIntent().getStringExtra("issdesc");
        final String s4 = getIntent().getStringExtra("mobile");
        final String s5 = getIntent().getStringExtra("uid");

        room = findViewById(R.id.roomno);
        type = findViewById(R.id.isstype);
        desc = findViewById(R.id.issdescrip);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        bt1 = findViewById(R.id.seeen);
        bt2 = findViewById(R.id.reesolved);
        bt3 = findViewById(R.id.details);

        room.setText(s1);
        type.setText(s2);
        desc.setText(s3);
        mobile.setText(s4);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HostelerInfo hostelerInfo = new HostelerInfo();
                hostelerInfo.setName(dataSnapshot.child(s4).getValue(HostelerInfo.class).getName());

                name.setText(hostelerInfo.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Issues").child("RP")
                        .child(s1).child(s5).child("status");

                db.setValue("1");
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Issues").child("RP")
                        .child(s1).child(s5).child("status");

                db.setValue("2");
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(DescriptionView.this,StudentDetail.class);
                in.putExtra("mobile",s4);
                startActivity(in);
            }
        });

    }
}
