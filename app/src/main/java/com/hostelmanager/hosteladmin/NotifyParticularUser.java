package com.hostelmanager.hosteladmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hostelmanager.hosteladmin.Model.SendNotifi;

public class NotifyParticularUser extends AppCompatActivity {
    private TextView tv1;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_particular_user);

        final String mob = getIntent().getStringExtra("mobile");

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.94),(int)(height*.47));

        tv1 = findViewById(R.id.npumessage);
        btn = findViewById(R.id.npusendmsg);

        final String[] token = {""};
        final DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                .child("Students").child(mob);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FCM_Device_Tokens tokenn = dataSnapshot.getValue(FCM_Device_Tokens.class);
                token[0] = tokenn.getToken();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = tv1.getText().toString().trim();
                if(msg.equals("")){
                    Toast.makeText(NotifyParticularUser.this,"Please Enter Message",Toast.LENGTH_SHORT).show();
                    return;
                }

                SendNotifi sendNotifi = new SendNotifi();
                String key = db.push().getKey();
                sendNotifi.setKey(key);
                sendNotifi.setMsg(msg);
                sendNotifi.setToken(token[0]);
                db.child("message").child(key).setValue(sendNotifi);
                Toast.makeText(NotifyParticularUser.this,"Message Successfully Sent",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
