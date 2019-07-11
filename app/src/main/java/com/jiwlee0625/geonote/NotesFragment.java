package com.jiwlee0625.geonote;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    RecyclerView noteListView;
    View mainView;
    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) { //this is necessary
            goToLogin();
        }
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    goToLogin();
                }
            }
        };
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mainView = view;
        init();
        loadData();
    }

    private void init() {
        noteListView = (RecyclerView) mainView.findViewById(R.id.notesList);
        noteListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    private void loadData() {

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
    public void goToLogin() {
        mAuth.removeAuthStateListener(mAuthStateListener);
        Intent goToLogInIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(goToLogInIntent);
    }

}
