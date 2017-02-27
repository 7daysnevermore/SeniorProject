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
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.CheckBeautician;
import com.example.captain_pc.beautyblinkcustomer.CreateRequest;
import com.example.captain_pc.beautyblinkcustomer.MessagePage;
import com.example.captain_pc.beautyblinkcustomer.OfferPage;
import com.example.captain_pc.beautyblinkcustomer.Payment;
import com.example.captain_pc.beautyblinkcustomer.PromotionDetails;
import com.example.captain_pc.beautyblinkcustomer.R;
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

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("customer-request/"+uid);
        recyclerView =(RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        final FirebaseRecyclerAdapter<ModelRequest,Request.RequestViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ModelRequest, Request.RequestViewHolder>
                (ModelRequest.class,R.layout.cus_request_card,Request.RequestViewHolder.class,ref) {
            @Override
            protected void populateViewHolder(Request.RequestViewHolder viewHolder, final ModelRequest model, final int position) {

                viewHolder.setEvent(model.getEvent());
                viewHolder.setService(model.getService());
                viewHolder.setCurrenttime(model.getCurrenttime());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setDate(model.getDate());
                viewHolder.setMaxprice(model.getMaxprice());
                viewHolder.setStatus(model.getStatus());
                final String ke =getRef(position).getKey();
                Log.d("position",""+ke);
                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("beautician-key/"+ke);
                mRootRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Offerss user = dataSnapshot.getValue(Offerss.class);
                        if (user == null) {
                            Toast.makeText(getActivity(), "Error: could not fetch user.", Toast.LENGTH_LONG).show();
                        } else {
                            kg=user.key;
                           list.add(kg);
                            Collections.reverse(list);
                            Log.d("kakakaka",""+kg);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //Log.d("dumaa",""+kg);
               tv = (TextView)viewHolder.mview.findViewById(R.id.tcount);
                DatabaseReference c = FirebaseDatabase.getInstance().getReference().child("offer/"+ke);
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
                                confirmValues.put("event",model.getEvent());
                                confirmValues.put("service",model.getService());
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
                                map.put("event",model.getEvent());
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
        public void setStatus(String status){
            TextView tv = (TextView)mview.findViewById(R.id.tSer);
            tv.setText(status);
        }
        public void setService(String service){
            TextView tv = (TextView)mview.findViewById(R.id.tService);
            tv.setText(service);
        }
        public void setCurrenttime(String currenttime){
            TextView tv = (TextView)mview.findViewById(R.id.tTime);
            ModelRequest mr = new ModelRequest();
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
