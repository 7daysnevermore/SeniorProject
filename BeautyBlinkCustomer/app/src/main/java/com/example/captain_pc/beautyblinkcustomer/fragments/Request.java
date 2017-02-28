package com.example.captain_pc.beautyblinkcustomer.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
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
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.CheckBeautician;
import com.example.captain_pc.beautyblinkcustomer.CreateRequest;
import com.example.captain_pc.beautyblinkcustomer.HiredDetails;
import com.example.captain_pc.beautyblinkcustomer.MessagePage;
import com.example.captain_pc.beautyblinkcustomer.OfferPage;
import com.example.captain_pc.beautyblinkcustomer.Payment;
import com.example.captain_pc.beautyblinkcustomer.PromotionDetails;
import com.example.captain_pc.beautyblinkcustomer.R;
import com.example.captain_pc.beautyblinkcustomer.RequestDetails;
import com.example.captain_pc.beautyblinkcustomer.Review;
import com.example.captain_pc.beautyblinkcustomer.model.DataRequest;
import com.example.captain_pc.beautyblinkcustomer.model.ModelRequest;
import com.example.captain_pc.beautyblinkcustomer.model.Offerss;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Request extends Fragment {
    private TextView postP,tv;
    private List<DataRequest> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private LinearLayout create_request;
    private Button btnPayment,btnMessage,btnReview;
    private ImageView reqbtn;
    private Button btnOf,btnUnp,btnTor,btnCo;
    String uid,kg;
    ArrayList<String> list = new ArrayList<String>();
    List<String> reverseView ;
    public Request() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_request,container,false);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        uid = mFirebaseUser.getUid().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("beautician-promotion"+"/"+mFirebaseUser.getUid().toString());

        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {

        btnOf = (Button)rootView.findViewById(R.id.btnOffer);
        btnUnp = (Button)rootView.findViewById(R.id.btnUnpaid);
        btnTor = (Button)rootView.findViewById(R.id.btnToreceive);
        btnCo = (Button)rootView.findViewById(R.id.btnCompleted);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("customer-request1").child(mFirebaseUser.getUid());
        //professor promotion feeds
        recyclerView =(RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        final FirebaseRecyclerAdapter<DataRequest,Request.RequestViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataRequest, Request.RequestViewHolder>
                (DataRequest.class,R.layout.cus_request_card,Request.RequestViewHolder.class,databaseReference) {
            @Override
            protected void populateViewHolder(RequestViewHolder viewHolder, final DataRequest model, final int position) {
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
                        RequestValues.put("uid", mFirebaseUser.getUid().toString());

                        if(model.status.equals("1")||model.status.equals("2")){
                            Intent intent = new Intent(getActivity(),RequestDetails.class);
                            intent.putExtra("request",  RequestValues);
                            startActivity(intent);
                        }
                        if (model.status.equals("3") || model.status.equals("4") || model.status.equals("5")|| model.status.equals("7")) {
                            Intent intent = new Intent(getActivity(), HiredDetails.class);
                            intent.putExtra("status",model.status);
                            intent.putExtra("request", RequestValues);
                            startActivity(intent);
                        }
                    }
                });
                btnPayment = (Button)viewHolder.mview.findViewById(R.id.payment);
                btnPayment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String key = getRef(position).getKey();

                        HashMap<String, Object> confirmValues = new HashMap<String, Object>();
                        confirmValues.put("key",key);
                        //confirmValues.put("event",model.getEvent());
                        //confirmValues.put("service",model.getService());

                        Intent goPaymentP = new Intent(getActivity(),Payment.class);
                        goPaymentP.putExtra("payment", confirmValues);
                        startActivity(goPaymentP);
                    }

                });
                //Log.d("dumaa",""+kg);
                final String key = getRef(position).getKey();
               tv = (TextView)viewHolder.mview.findViewById(R.id.tcount);
                DatabaseReference c = FirebaseDatabase.getInstance().getReference().child("offer/"+key);
                c.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int countt = (int) dataSnapshot.getChildrenCount();

                        tv.setVisibility(View.VISIBLE);
                        tv.setText(""+countt);
                        Log.d("countnum","="+countt);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                TextView t = (TextView)viewHolder.mview.findViewById(R.id.tSer);
                String str = t.getText().toString();
                ModelRequest mr = new ModelRequest();
                mr.setStatus(str);

                switch (mr.getStatus()){

                    case "toprovide" : //1. -KduSkjJFaaSR8lYG_v3,-KdvBYlZhPjJZvBGf9dK
                           // 2.-KdvA4JLQvMtPa_Xnci,-KduyWaZfGtIcS2Wc5dV
                        viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                            String ke =getRef(position).getKey();
                            @Override
                            public void onClick(View v) {
                                HashMap<String, Object> k = new HashMap<>();
                                k.put("keyrequest",ke);
                                Intent intent = new Intent(getActivity(),CheckBeautician.class);
                                intent.putExtra("request",  k);
                                startActivity(intent);


                            }
                        });
                        break;
                    case "Unpaid" :

                        ImageView img1 = (ImageView)viewHolder.mview.findViewById(R.id.imageView3);
                        ImageView img2 = (ImageView)viewHolder.mview.findViewById(R.id.imageView2);
                        TextView to =(TextView)viewHolder.mview.findViewById(R.id.tcount);
                        to.setVisibility(View.GONE);
                        img1.setVisibility(View.GONE);
                        img2.setVisibility(View.GONE);
                        LinearLayout ll = (LinearLayout)viewHolder.mview.findViewById(R.id.afteroffer);
                        ll.setVisibility(View.VISIBLE);
                        viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                            final String ke =getRef(position).getKey();

                            @Override
                            public void onClick(View v) {
                              String l = list.get(position);
                               Log.d("list",""+l);
                                HashMap<String, Object> k = new HashMap<>();
                                k.put("keyrequest",ke);
                                k.put("keyoffer", l);
                                Intent intent = new Intent(getActivity(),OfferPage.class);
                                intent.putExtra("request",  k);
                                startActivity(intent);


                            }
                        });
                        btnPayment = (Button)viewHolder.mview.findViewById(R.id.payment);
                        btnPayment.setOnClickListener(new View.OnClickListener() {
                            final String key = getRef(position).getKey();
                            @Override
                            public void onClick(View v) {
                                String l = list.get(position);
                                HashMap<String, Object> confirmValues = new HashMap<String, Object>();
                                confirmValues.put("key",key);
                                confirmValues.put("event",model.event);
                                confirmValues.put("service",model.service);
                                confirmValues.put("insidekey",l);

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

                        break;
                }

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
        btnOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contentstatus, new OfferFragment(), "fragment_screen");
                ft.commit();
            }
        });

        btnUnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contentstatus, new UnpaidFragment(), "fragment_screen");
                ft.commit();
            }
        });
        btnTor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contentstatus, new ToreceiveFragment(), "fragment_screen");
                ft.commit();
            }
        });
        btnCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.contentstatus, new CompletedFragment(), "fragment_screen");
                ft.commit();
            }
        });




    }
    public static Request newInstance(){
        Request fragment = new Request();
        Bundle args = new Bundle(); //Argument
        fragment.setArguments(args);
        return fragment;
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


    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        View mview;
        public RequestViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }

        public void setMaxprice(String maxprice){
            TextView tv =(TextView)mview.findViewById(R.id.tPrice);
            tv.setText(maxprice);
        }
        public void setDate(String date){
            TextView tv =(TextView)mview.findViewById(R.id.tDate);
            tv.setText(date);
        }
        public void setLocation(String location){
            TextView tv = (TextView)mview.findViewById(R.id.tLocation);
            tv.setText(location);
        }

        public void setEvent(String event){
            TextView tv = (TextView)mview.findViewById(R.id.tevent);
            tv.setText(event);
        }
        public void setMaxP(Integer maxP){
            TextView post_maxprice = (TextView)mview.findViewById(R.id.tPrice);
            post_maxprice.setText(String.valueOf(maxP));
        }
        public void setService(String service){
            TextView tv = (TextView)mview.findViewById(R.id.tService);
            tv.setText(service);
        }
        public void setStatus(String status){
            String finalstatus = null;
            if(status.equals("1")||status.equals("2")){
                finalstatus = "Offer";
            }
            if(status.equals("3")){ finalstatus = "Unpaid"; }
            if(status.equals("4")){ finalstatus = "To receive"; }
            if(status.equals("5")){ finalstatus = "Completed"; }
            //Button post_service = (Button)mview.findViewById(R.id.btnStat);
            //post_service.setText(finalstatus);

        }

        public void setColorcircle(String color){
            ImageView cC = (ImageView)mview.findViewById(R.id.cirNoti);
            int colorc = Color.parseColor(color);
            //PorterDuffColorFilter greyFilter = new PorterDuffColorFilter(colorc, PorterDuff.Mode.MULTIPLY);
            GradientDrawable drawable = (GradientDrawable) cC.getBackground();
            drawable.setColor(colorc);
            //((GradientDrawable)cC.getBackground().setColorFilter(greyFilter);
            //cC.setBackgroundColor(Color.parseColor(color));
            //GradientDrawable bgShape = (GradientDrawable)cC.getBackground();
            //bgShape.setColor(Color.parseColor(color));
        }
        public void setCurrenttime(String currenttime){
            TextView tv = (TextView)mview.findViewById(R.id.tTime);
            DataRequest mr = new DataRequest();
            mr.setCurrenttime(currenttime);

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm a");
            Date convertedDate = null;
            try{
                convertedDate = dateFormat.parse(mr.getCurrenttime());
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
            tv.setText(""+a+", ");
        }
    }
}
