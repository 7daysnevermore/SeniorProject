package com.example.captain_pc.beautyblinkcustomer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.BeauticianProfile;
import com.example.captain_pc.beautyblinkcustomer.R;
import com.example.captain_pc.beautyblinkcustomer.model.BeauticianService;
import com.example.captain_pc.beautyblinkcustomer.model.BeauticianUser;
import com.example.captain_pc.beautyblinkcustomer.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by NunePC on 24/2/2560.
 */

public class PreviewDetailFragment extends Fragment {

    TextView address,makeupandhair,makeup,hairstyle,hairdress,makeupandhair_price,makeup_price,hairstyle_price,hairdress_price;
    LinearLayout price01,price02,price03,price04;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    public PreviewDetailFragment(){ super(); }

    public static PreviewDetailFragment newInstance(){
        PreviewDetailFragment fragment = new PreviewDetailFragment();
        Bundle args = new Bundle(); //Argument
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_preview_detail,container,false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView){

        //Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        BeauticianProfile beauti = (BeauticianProfile) getActivity();

        address = (TextView) rootView.findViewById(R.id.address);
        makeupandhair = (TextView) rootView.findViewById(R.id.makeupandhair);
        makeup = (TextView) rootView.findViewById(R.id.makeup);
        hairstyle = (TextView) rootView.findViewById(R.id.hairstyle);
        hairdress = (TextView) rootView.findViewById(R.id.hairdress);
        makeupandhair_price = (TextView) rootView.findViewById(R.id.makeupandhair_price);
        makeup_price = (TextView) rootView.findViewById(R.id.makeup_price);
        hairstyle_price = (TextView) rootView.findViewById(R.id.hairstyle_price);
        hairdress_price = (TextView) rootView.findViewById(R.id.hairdress_price);
        price01 = (LinearLayout) rootView.findViewById(R.id.price01);
        price02 = (LinearLayout) rootView.findViewById(R.id.price02);
        price03 = (LinearLayout) rootView.findViewById(R.id.price03);
        price04 = (LinearLayout) rootView.findViewById(R.id.price04);

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

        mRootRef.child("beautician").child(beauti.b_uid).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BeauticianUser user = dataSnapshot.getValue(BeauticianUser.class);
                if (user == null) {
                    Toast.makeText(getActivity(), "Error: could not fetch user.", Toast.LENGTH_LONG).show();
                } else {

                    String build;
                    if(user.building==null){
                        build = "";
                    }
                    else{
                        build = user.building;
                    }
                    address.setText(user.address_number +" "+build+", "+user.address_sub_district+
                            ", "+user.address_district+", "+user.address_province+", "+user.address_code);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mRootRef01 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mRootRef02 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mRootRef03 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mRootRef04 = FirebaseDatabase.getInstance().getReference();

        mRootRef01.child("beautician-service").child(beauti.b_uid+"/S01").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BeauticianService services = dataSnapshot.getValue(BeauticianService.class);
                if (services == null) {
                } else {
                    makeupandhair.setVisibility(View.VISIBLE);
                    price01.setVisibility(View.VISIBLE);
                    makeupandhair_price.setText(services.price+" Bath");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRootRef02.child("beautician-service").child(beauti.b_uid + "/S02").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BeauticianService services = dataSnapshot.getValue(BeauticianService.class);
                if (services == null) {
                } else {
                    makeup.setVisibility(View.VISIBLE);
                    price02.setVisibility(View.VISIBLE);
                    makeup_price.setText(services.price+" Bath");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRootRef03.child("beautician-service").child(beauti.b_uid + "/S03").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BeauticianService services = dataSnapshot.getValue(BeauticianService.class);
                if (services == null) {
                } else {
                    hairstyle.setVisibility(View.VISIBLE);
                    price03.setVisibility(View.VISIBLE);
                    hairstyle_price.setText(services.price+" Bath");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRootRef04.child("beautician-service").child(beauti.b_uid + "/S04").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BeauticianService services = dataSnapshot.getValue(BeauticianService.class);
                if (services == null) {
                } else {
                    hairdress.setVisibility(View.VISIBLE);
                    price04.setVisibility(View.VISIBLE);
                    hairdress_price.setText(services.price+" Bath");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
}
