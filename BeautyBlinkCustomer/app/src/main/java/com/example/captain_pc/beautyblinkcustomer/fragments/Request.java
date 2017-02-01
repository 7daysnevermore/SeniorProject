package com.example.captain_pc.beautyblinkcustomer.fragments;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.captain_pc.beautyblinkcustomer.CreateRequest;
import com.example.captain_pc.beautyblinkcustomer.OfferPage;
import com.example.captain_pc.beautyblinkcustomer.PromotionDetails;
import com.example.captain_pc.beautyblinkcustomer.R;
import com.example.captain_pc.beautyblinkcustomer.model.DataRequest;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Request extends Fragment {
    private TextView postP;
    private List<DataRequest> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private LinearLayout create_request;
    private ImageView reqbtn;
    public Request() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_request,container,false);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("beautician-promotion"+"/"+mFirebaseUser.getUid().toString());

        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView){

        databaseReference = FirebaseDatabase.getInstance().getReference().child("beautician-offer/W5mxjrCK4wXr1rm79gIe9gvrCtI3");
        //professor promotion feeds
        recyclerView =(RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        //Order from latest data
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        final FirebaseRecyclerAdapter<DataRequest,Request.RequestViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataRequest, Request.RequestViewHolder>
                (DataRequest.class,R.layout.request_card,Request.RequestViewHolder.class,databaseReference) {

            @Override
            protected void populateViewHolder(RequestViewHolder viewHolder, final DataRequest model, final int position) {
               viewHolder.setStatus(model.getStatus());
                viewHolder.setDate(model.getDate());
                viewHolder.setEvent(model.getEvent());
                viewHolder.setColor(model.getColor());
               viewHolder.setService(model.getService());
                viewHolder.setColor(model.getColor());
                viewHolder.setMaxP(model.getMaxP());
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    final String cshow = getRef(position).getKey();
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> RequestValues = new HashMap<>();
                        RequestValues.put("key",cshow);
                        RequestValues.put("service", model.getService());
                        RequestValues.put("event", model.getEvent());
                        RequestValues.put("numberofperson", model.getNumP());
                        RequestValues.put("maxP", model.getMaxP()+"");
                        RequestValues.put("date", model.getDate());
                        RequestValues.put("time", model.getTime());
                        RequestValues.put("location", model.getLocate());
                        RequestValues.put("color",model.getColor());
                        RequestValues.put("specialrequest", model.getCus());
                        RequestValues.put("offer",model.getStatus());
                        RequestValues.put("uid", mFirebaseUser.getUid().toString());
                        RequestValues.put("specialbeau",model.getBeau());
                        Intent intent = new Intent(getActivity(),OfferPage.class);
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

        create_request = (LinearLayout)rootView.findViewById(R.id.create_req);
        create_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateRequest.class);
                startActivity(intent);
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
    public static Request newInstance(){
        Request fragment = new Request();
        Bundle args = new Bundle(); //Argument
        fragment.setArguments(args);
        return fragment;
    }
    public static class RequestViewHolder extends RecyclerView.ViewHolder  {

        View mview;
        public RequestViewHolder(View itemView){
            super(itemView);
            mview=itemView;

        }

        public void setDate(String date){
           TextView post_date= (TextView)mview.findViewById(R.id.date_des);
           post_date.setText(date);
        }
        public void setEvent(String event){
           TextView post_event = (TextView)mview.findViewById(R.id.event_des);
            post_event.setText(event);

        }
        public void setColor(int color){
            LinearLayout cC = (LinearLayout)mview.findViewById(R.id.ch_color);
            cC.setBackgroundColor(color);
        }
        public void setMaxP(String maxP){
            TextView post_maxprice = (TextView)mview.findViewById(R.id.price_des);
            post_maxprice.setText(maxP);
        }
        public void setService(String service){
            TextView post_service = (TextView)mview.findViewById(R.id.tService);
            post_service.setText(service);

        }
        public void setStatus(String status){

            Button post_service = (Button)mview.findViewById(R.id.btnStat);
            post_service.setText(status);

        }



    }


}
