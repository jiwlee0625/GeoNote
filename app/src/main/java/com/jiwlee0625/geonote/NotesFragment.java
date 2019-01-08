package com.jiwlee0625.geonote;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesFragment extends Fragment {


    public NotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null) {
            goToLogin();
        }
        updateView();
    }
    public void goToLogin() {
        Intent goToLogInIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(goToLogInIntent);
    }

    public void updateView() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String path = "" + currentUser.getUid() + "/notes";
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(path);
        System.out.println("line 36 notes fragment");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot note : dataSnapshot.getChildren()) {
                    System.out.println(note.child("title").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read");
            }
        });

    }

}
