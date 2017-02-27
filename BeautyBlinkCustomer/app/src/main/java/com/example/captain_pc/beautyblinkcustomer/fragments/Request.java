package com.example.captain_pc.beautyblinkcustomer.fragments;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.captain_pc.beautyblinkcustomer.MessagePage;
import com.example.captain_pc.beautyblinkcustomer.OfferPage;
import com.example.captain_pc.beautyblinkcustomer.Payment;
import com.example.captain_pc.beautyblinkcustomer.PromotionDetails;
import com.example.captain_pc.beautyblinkcustomer.R;
import com.example.captain_pc.beautyblinkcustomer.RequestDetails;
import com.example.captain_pc.beautyblinkcustomer.Review;
import com.example.captain_pc.beautyblinkcustomer.model.DataRequest;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Request extends Fragment {
    private TextView postP;
    private List<DataRequest> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private LinearLayout create_request;
    private Button btnPayment,btnMessage,btnReview;
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

        databaseReference = FirebaseDatabase.getInstance().getReference().child("customer-request1").child(mFirebaseUser.getUid());
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
               viewHolder.setStatus(model.status);
                viewHolder.setDate(model.date);
                viewHolder.setEvent(model.event);
                viewHolder.setColor("#ff0000");
                viewHolder.setService(model.service);
                viewHolder.setMaxP(model.maxprice);
                //viewHolder.setCurrenttime(model.dateform);


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
                        RequestValues.put("uid", mFirebaseUser.getUid().toString());

                        Intent intent = new Intent(getActivity(),RequestDetails.class);
                        intent.putExtra("request",  RequestValues);
                        startActivity(intent);
                    }
                });
                btnPayment = (Button)viewHolder.mview.findViewById(R.id.payment);
                btnPayment.setOnClickListener(new View.OnClickListener() {
                    final String key = getRef(position).getKey();
                    @Override
                    public void onClick(View v) {

                        HashMap<String, Object> confirmValues = new HashMap<String, Object>();
                        confirmValues.put("key",key);
                        //confirmValues.put("event",model.getEvent());
                        //confirmValues.put("service",model.getService());

                        Intent goPaymentP = new Intent(getActivity(),Payment.class);
                        goPaymentP.putExtra("payment", confirmValues);
                        startActivity(goPaymentP);
                    }
                });
                btnMessage = (Button)viewHolder.mview.findViewById(R.id.message);
                btnMessage.setOnClickListener(new View.OnClickListener() {
                    final String key = getRef(position).getKey();
                    @Override
                    public void onClick(View v) {

                        HashMap<String,Object> map = new HashMap<String, Object>();
                        map.put("key",key);
                        map.put("event",model.event);
                        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference().getRoot().child("message").child(key);
                        //ref.updateChildren(map);
                        Intent goMessage = new Intent(getActivity(), MessagePage.class);
                        goMessage.putExtra("message",map);
                        startActivity(goMessage);
                        //ref.updateChildren(map);
                    }
                });
                btnReview=(Button)viewHolder.mview.findViewById(R.id.review);
                btnReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(),Review.class);
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
        public void setColor(String color){
            LinearLayout cC = (LinearLayout)mview.findViewById(R.id.ch_color);
            cC.setBackgroundColor(Color.parseColor(color));
        }
        public void setMaxP(Integer maxP){
            TextView post_maxprice = (TextView)mview.findViewById(R.id.price_des);
            post_maxprice.setText(String.valueOf(maxP));
        }
        public void setService(String service){
            TextView post_service = (TextView)mview.findViewById(R.id.tService);
            post_service.setText(service);

        }
        public void setStatus(String status){
            String finalstatus = null;
            if(status.equals("1")||status.equals("2")){
                finalstatus = "Offer";
            }
            if(status.equals("3")){ finalstatus = "Unpaid"; }
            if(status.equals("4")){ finalstatus = "To receive"; }
            if(status.equals("5")){ finalstatus = "Completed"; }
            Button post_service = (Button)mview.findViewById(R.id.btnStat);
            post_service.setText(finalstatus);

        }
        public void setCurrenttime(String dateform){
            TextView tm= (TextView)mview.findViewById(R.id.btnTime);
            DataRequest r = new DataRequest();
            Log.d("Bye","="+r.dateform);
            //String d = "JAN 31 2017 10:11 PM";
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm a");
            Date convertedDate = null;
            try{
                convertedDate = dateFormat.parse(r.dateform);
                //convertedDate = dateFormat.parse(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            PrettyTime p = new PrettyTime();
            String datetime = p.format(convertedDate);
            Log.d("Bye","="+datetime);
            Log.d("test","="+convertedDate);
            String a = null;
            if(datetime.contains("minutes")){
                a =datetime.replace("minutes","mins");
            }else{
                a = datetime;
            }
            tm.setText(""+a);


        }




    }


}
