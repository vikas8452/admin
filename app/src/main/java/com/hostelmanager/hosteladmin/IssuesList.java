package com.hostelmanager.hosteladmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hostelmanager.hosteladmin.Model.SendRecieveIssues;

import java.util.ArrayList;

public class IssuesList extends AppCompatActivity {

    //private RecyclerView gridView;
    private MyItemRecyclerViewAdapter adapter;
    private ArrayList<SendRecieveIssues> sendRecieveIssues;

    public IssuesList() {
        sendRecieveIssues = new ArrayList<>();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_item);

        GridView gridView = findViewById(R.id.gridissue);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Issues").child("RP");
        databaseReference.keepSynced(true);
        //   helper=new FireBaseHelper(databaseReference);

        adapter=new MyItemRecyclerViewAdapter(getBaseContext(),retrieve(databaseReference));
        gridView.setAdapter(adapter);

    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        //sendRecieveIssues.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Log.d("ds","Hell");
            try {
                SendRecieveIssues spacecraft = ds.getValue(SendRecieveIssues.class);
                assert spacecraft != null;
                if(spacecraft.getStatus().equals("2"))
                    continue;
                sendRecieveIssues.add(spacecraft);
                // Toast.makeText(getActivity(), buySellSubjects.get(0).getBookName() + "", Toast.LENGTH_SHORT).show();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<SendRecieveIssues> retrieve(DatabaseReference db)
    {
        Log.d("sdsd","Entered in the listener");
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("sdsd","Calling Fetvch data");
                fetchData(dataSnapshot);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return  sendRecieveIssues;
    }
}
