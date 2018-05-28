package com.hostelmanager.hosteladmin;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hostelmanager.hosteladmin.Model.ConfStatus;
import com.hostelmanager.hosteladmin.Model.HostelerInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NonVerified extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    GridView gridView;
    DatabaseReference databaseReference;
    private ArrayList<ConfStatus> confStatuses= new ArrayList<>();
    private NonVerifiedAdapter adapter;
    private SearchView sv;
    private String mob="";

    public NonVerified() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_non_verified, container, false);
        firebaseAuth= FirebaseAuth.getInstance();
        currentUser=firebaseAuth.getCurrentUser();
        gridView = view.findViewById(R.id.gridnonvf);

        sv = view.findViewById(R.id.search);

        String uid = firebaseAuth.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Hostels").child(uid);
        databaseReference.keepSynced(true);
        //   helper=new FireBaseHelper(databaseReference);

        adapter=new NonVerifiedAdapter(getContext(),retrieve(databaseReference));
        gridView.setAdapter(adapter);

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mob = newText.toUpperCase();
                gridView.invalidate();
                confStatuses.clear();
                adapter = null;
                adapter=new NonVerifiedAdapter(getContext(),retrieve(databaseReference));
                gridView.setAdapter(adapter);
                return false;
            }
        });

        return view;
    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        //sendRecieveIssues.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Log.d("ds","Hell");
            try {
                ConfStatus spacecraft = ds.getValue(ConfStatus.class);
                assert spacecraft != null;
                String name = spacecraft.getName().toUpperCase();
                if(spacecraft.getStat().equals("1")&&(name.contains(mob)||spacecraft.getMobile().contains(mob)))
                    confStatuses.add(spacecraft);
                // Toast.makeText(getActivity(), buySellSubjects.get(0).getBookName() + "", Toast.LENGTH_SHORT).show();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private List<ConfStatus> retrieve(DatabaseReference db) {
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
                //fetchData(dataSnapshot);
                //gridView.setAdapter(adapter);
                confStatuses.clear();
                gridView.invalidate();
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

        return  confStatuses;
    }

}
