package com.hostelmanager.hosteladmin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class AddHosteler extends AppCompatActivity {

    private TextView name,mobile,email;
    private Button adduser;
    private String str1n,str2e,str3m;

    public AddHosteler() {
        // Required empty public constructor
    }

   /* public static AddHosteler newInstance(String param1, String param2) {
        AddHosteler fragment = new AddHosteler();
        Bundle args = new Bundle();
        return fragment;
    }*/

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_hosteler);

        name = findViewById(R.id.username);
        email = findViewById(R.id.useremail);
        mobile = findViewById(R.id.usermob);

        adduser = findViewById(R.id.useradduser);

        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str1n = name.getText().toString().trim();
                str2e = email.getText().toString().trim();
                str3m = mobile.getText().toString().trim();

                if(str1n.equals("")){
                    Toast.makeText(AddHosteler.this,"Please Enter Name",Toast.LENGTH_SHORT).show();
                    return ;
                }
                else if(str2e.equals("")){
                    Toast.makeText(AddHosteler.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                    return ;
                }
                else if(str3m.equals("")){
                    Toast.makeText(AddHosteler.this,"Please Enter Phone Number",Toast.LENGTH_SHORT).show();
                    return ;
                }

                Intent intent = new Intent(AddHosteler.this, MobileAndOTP.class);

                intent.putExtra("email",str2e);
                intent.putExtra("name",str1n);
                intent.putExtra("mobile",str3m);

                startActivity(intent);



            }
        });
    }

}
