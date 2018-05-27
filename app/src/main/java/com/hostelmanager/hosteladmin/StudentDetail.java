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

public class StudentDetail extends AppCompatActivity {
    private String name,hostel,roomno,college,year;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        final String mobile = getIntent().getStringExtra("mobile");

        tv1 = findViewById(R.id.sdname);
        tv2 = findViewById(R.id.sdmobile);
        tv3 = findViewById(R.id.sdcollege);
        tv4 = findViewById(R.id.sdhostel);
        tv5 = findViewById(R.id.sdroom);
        tv6 = findViewById(R.id.sdyear);
        Button btn = findViewById(R.id.sdback);
        Button btn2 = findViewById(R.id.sdsendnotifi);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Students").child(mobile);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HostelerInfo hostelerInfo = dataSnapshot.getValue(HostelerInfo.class);
                assert hostelerInfo != null;
                name = hostelerInfo.getName();
                hostel = hostelerInfo.getHostel();
                roomno = hostelerInfo.getRoomno();
                college = hostelerInfo.getCollege();
                year = hostelerInfo.getYear();

                tv1.setText(name);
                tv2.setText(mobile);
                tv3.setText(college);
                tv4.setText(hostel);
                tv5.setText(roomno);
                tv6.setText(year);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in =new Intent(StudentDetail.this,NotifyParticularUser.class);
                in.putExtra("mobile",mobile);
                startActivity(in);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
