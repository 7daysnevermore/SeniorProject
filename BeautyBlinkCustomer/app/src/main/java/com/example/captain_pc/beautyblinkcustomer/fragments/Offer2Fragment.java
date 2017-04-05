package com.example.captain_pc.beautyblinkcustomer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.captain_pc.beautyblinkcustomer.HiredDetails;
import com.example.captain_pc.beautyblinkcustomer.R;
import com.example.captain_pc.beautyblinkcustomer.RequestDetails;
import com.example.captain_pc.beautyblinkcustomer.model.DataRequest;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

/**
 * Created by NunePC on 4/4/2560.
 */

public class Offer2Fragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private Query dataQuery1;
    String uid,kg;

    public Offer2Fragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_offer2,container,false);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        uid = mFirebaseUser.getUid().toString();
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("customer-request1").child(mFirebaseUser.getUid());
        final DatabaseReference databaseRef = databaseReference.getRef();
        dataQuery1 = databaseRef.orderByChild("status").equalTo("2");
        //professor promotion feeds
        recyclerView =(RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        final FirebaseRecyclerAdapter<DataRequest,Request.RequestViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataRequest, Request.RequestViewHolder>
                (DataRequest.class,R.layout.cus_request_card,Request.RequestViewHolder.class,dataQuery1) {
            @Override
            protected void populateViewHolder(Request.RequestViewHolder viewHolder, final DataRequest model, final int position) {

                if(model.userprofile!=null){
                    viewHolder.setProfile(getActivity().getApplicationContext(),model.userprofile);
                }
                viewHolder.setStatus(model.status);
                viewHolder.setDate(model.date);
                viewHolder.setEvent(model.event);
                viewHolder.setLocation(model.location);
                viewHolder.setService(model.service);
                viewHolder.setMaxP(model.maxprice);
                viewHolder.setCurrenttime(model.currenttime);


                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    final String cshow = getRef(position).getKey();
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> RequestValues = new HashMap<>();
                        RequestValues.put("key",cshow);
                        RequestValues.put("service", model.service);
                        RequestValues.put("event", model.event);
                        RequestValues.put("numberofperson", model.numberofperson);
                        RequestValues.put("maxprice", model.maxprice);
                        RequestValues.put("date", model.date);
                        RequestValues.put("time", model.time);
                        RequestValues.put("location", model.location);
                        RequestValues.put("specialrequest", model.specialrequest);
                        RequestValues.put("status",model.status);
                        RequestValues.put("custname",model.username);
                        RequestValues.put("userprofile", model.userprofile);
                        RequestValues.put("reqpic", model.reqpic);
                        RequestValues.put("uid", mFirebaseUser.getUid().toString());

                        if(model.status.equals("1")||model.status.equals("2")){
                            Intent intent = new Intent(getActivity(),RequestDetails.class);
                            intent.putExtra("request",  RequestValues);
                            startActivity(intent);
                        }
                        if (model.status.equals("3") || model.status.equals("4") || model.status.equals("5")|| model.status.equals("6")|| model.status.equals("7")) {
                            Intent intent = new Intent(getActivity(), HiredDetails.class);
                            intent.putExtra("status",model.status);
                            intent.putExtra("request", RequestValues);
                            startActivity(intent);
                        }
                    }
                });


            }
        };
        firebaseRecyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = firebaseRecyclerAdapter.getItemCount();
                int lastVisiblePosition = mLayoutManager.findLastVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.scrollToPosition(positionStart);
                }
            }
        });
        recyclerView.setAdapter(firebaseRecyclerAdapter);
        recyclerView.setLayoutManager(mLayoutManager);




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

    public static Offer2Fragment newInstance(){
        Offer2Fragment fragment = new Offer2Fragment();
        Bundle args = new Bundle(); //Argument
        fragment.setArguments(args);
        return fragment;
    }
}
