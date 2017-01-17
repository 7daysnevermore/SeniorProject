package com.example.captain_pc.beautyblinkcustomer.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.captain_pc.beautyblinkcustomer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Promotions extends Fragment {

    private EditText mF;
    private Button mB;
    private DatabaseReference mRef;
    public Promotions() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_promotions,container,false);
         mRef = FirebaseDatabase.getInstance().getReference();
       // mRef.setValue(getContext());
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView){

        mB = (Button)rootView.findViewById(R.id.button2);
        DatabaseReference mChild = mRef.child("GG");
        mChild.setValue("Gameeeee");
    }

    @Override
    public void onStart(){ super.onStart(); }

    @Override
    public void onStop(){ super.onStop(); }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null){
            //Restore Instance State here
        }
    }

    public static Promotions newInstance(){
        Promotions fragment = new Promotions();
        Bundle args = new Bundle(); //Argument
        fragment.setArguments(args);
        return fragment;
    }
}
