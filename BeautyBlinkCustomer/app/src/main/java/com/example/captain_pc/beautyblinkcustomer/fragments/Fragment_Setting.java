package com.example.captain_pc.beautyblinkcustomer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.captain_pc.beautyblinkcustomer.MyAccount;
import com.example.captain_pc.beautyblinkcustomer.MyLikes;
import com.example.captain_pc.beautyblinkcustomer.MyPromotion;
import com.example.captain_pc.beautyblinkcustomer.R;
import com.example.captain_pc.beautyblinkcustomer.RecentlyViewed;
import com.example.captain_pc.beautyblinkcustomer.Setting;
import com.example.captain_pc.beautyblinkcustomer.Support;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by CaptainPC on 20/1/2560.
 */

public class Fragment_Setting extends Fragment{
    public Fragment_Setting() {
        // Required empty public constructor
    }

    TextView myAccount, recentlyViewed, myPromotion, setting, support, logout;
    LinearLayout myLikes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_setting,container,false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView){

        myAccount = (TextView) rootView.findViewById(R.id.myAccount);
        myLikes = (LinearLayout) rootView.findViewById(R.id.myLikes);
        recentlyViewed = (TextView) rootView.findViewById(R.id.recentlyViewed);
        myPromotion = (TextView) rootView.findViewById(R.id.myPromotion);
        setting = (TextView) rootView.findViewById(R.id.setting);
        support = (TextView) rootView.findViewById(R.id.support);
        logout = (TextView) rootView.findViewById(R.id.logout);

        myAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyAccount.class));
            }
        });

        myLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyLikes.class));
            }
        });

        recentlyViewed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RecentlyViewed.class));
            }
        });

        myPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyPromotion.class));
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Setting.class));
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Support.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
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

    public static Fragment_Setting newInstance(){
        Fragment_Setting fragment = new Fragment_Setting();
        Bundle args = new Bundle(); //Argument
        fragment.setArguments(args);
        return fragment;
    }
}
