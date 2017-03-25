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
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.BeauticianProfile;
import com.example.captain_pc.beautyblinkcustomer.OfferDetails;
import com.example.captain_pc.beautyblinkcustomer.OfferPage;
import com.example.captain_pc.beautyblinkcustomer.R;
import com.example.captain_pc.beautyblinkcustomer.SearchDetails;
import com.example.captain_pc.beautyblinkcustomer.model.DataCustomerLiked;
import com.example.captain_pc.beautyblinkcustomer.model.DataOffer;
import com.example.captain_pc.beautyblinkcustomer.model.DataProfilePromote;
import com.example.captain_pc.beautyblinkcustomer.model.OfferViewHolder;
import com.example.captain_pc.beautyblinkcustomer.model.SearchViewHolder;
import com.example.captain_pc.beautyblinkcustomer.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NunePC on 28/2/2560.
 */

public class OfferPagePopular extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private Query databaseQuery,dataQuery1;
    OfferPage offer;



    public OfferPagePopular(){ super(); }

    public static OfferPagePopular newInstance(){
        OfferPagePopular fragment = new OfferPagePopular();
        Bundle args = new Bundle(); //Argument
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_offer_popular,container,false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView){

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        offer = (OfferPage) getActivity();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("customer-received").child(mFirebaseUser.getUid()).child(offer.requestid);
        //professor promotion feeds
        recyclerView =(RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //Get search to order in fragment
        QueryRecycle(databaseReference);


    }

    public void QueryRecycle(Query dataQuery){

        //Order from latest data
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final FirebaseRecyclerAdapter<DataOffer,OfferViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataOffer, OfferViewHolder>
                (DataOffer.class,R.layout.offer_row,OfferViewHolder.class,dataQuery) {

            @Override
            protected void populateViewHolder(final OfferViewHolder viewHolder, final DataOffer model, final int position) {

                if(model.beaupic!=null){
                    viewHolder.setImage(getActivity().getApplicationContext(),model.beaupic);
                }
                if (!model.reqpic.equals("")) {
                    viewHolder.setRequestPic(getActivity().getApplicationContext(), model.reqpic);
                }
                /*if (!model.offerpic.equals("")) {
                    viewHolder.setOfferPic(getActivity().getApplicationContext(), model.offerpic);
                }*/
                viewHolder.setUsername(model.beauname);
                viewHolder.setPrice(model.price);

                DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("beautician-profilepromote").child(model.beauid);
                data.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataProfilePromote promote = dataSnapshot.getValue(DataProfilePromote.class);
                        if(dataSnapshot.getValue() == null || dataSnapshot.getValue() == ""){
                            viewHolder.setRating("0");
                        }
                        else{
                            viewHolder.setRating(promote.rating);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    //private static final String TAG = "Promotion";
                    final String cshow = getRef(position).getKey();
                    @Override
                    public void onClick(View view) {

                        HashMap<String, Object> RequestValues = new HashMap<>();
                        RequestValues.put("offerid",cshow);
                        RequestValues.put("requestid", model.requestid);
                        RequestValues.put("service", model.service);
                        RequestValues.put("event", model.event);
                        RequestValues.put("beauticianname", model.beauname);
                        RequestValues.put("beauid", model.beauid);
                        RequestValues.put("beauprofile", model.beaupic);
                        RequestValues.put("numberofperson", model.numberofperson);
                        RequestValues.put("price", model.price);
                        RequestValues.put("amount", model.amount);
                        RequestValues.put("date", model.date);
                        RequestValues.put("time", model.time);
                        RequestValues.put("location", model.location);
                        RequestValues.put("specialrequest", model.specialrequest);
                        RequestValues.put("status",model.status);
                        RequestValues.put("custid", model.customerid);
                        RequestValues.put("requestpic", model.reqpic);
                        RequestValues.put("currenttime", model.currenttime);
                        Intent intent = new Intent(getActivity(),OfferDetails.class);
                        intent.putExtra("request",  RequestValues);
                        startActivity(intent);
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

}
