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
import com.example.captain_pc.beautyblinkcustomer.R;
import com.example.captain_pc.beautyblinkcustomer.SearchDetails;
import com.example.captain_pc.beautyblinkcustomer.model.DataCustomerLiked;
import com.example.captain_pc.beautyblinkcustomer.model.DataProfilePromote;
import com.example.captain_pc.beautyblinkcustomer.model.DataVerified;
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
 * Created by NunePC on 30/1/2560.
 */

public class SearchPrice extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private Query databaseQuery,dataQuery1;

    public SearchPrice(){ super(); }

    public static SearchPrice newInstance(){
        SearchPrice fragment = new SearchPrice();
        Bundle args = new Bundle(); //Argument
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search_details,container,false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView){

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("profilepromote");
        //professor promotion feeds
        recyclerView =(RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //Get search to order in fragment
        final SearchDetails search = (SearchDetails) getActivity();



        if(search.checking == true){

            //search for each service
            databaseQuery = databaseReference.orderByChild(search.search).startAt(1);
            //final DatabaseReference databaseRef = databaseQuery.getRef();
            //Query order =  databaseRef.orderByValue();
            QueryRecycle(databaseQuery, search,true);
        }
        else {
            //search for each service
            databaseQuery = databaseReference.orderByChild(search.search).startAt(1);
            //final DatabaseReference databaseRef = databaseQuery.getRef();
            //Query order =  databaseRef.orderByValue();
            QueryRecycle(databaseQuery, search,false);
        }



    }

    public void QueryRecycle(Query dataQuery, final SearchDetails search,Boolean checking){

        //Order from latest data
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        if(checking==false){
            mLayoutManager.setReverseLayout(true);
        }
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final FirebaseRecyclerAdapter<DataProfilePromote,SearchViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataProfilePromote, SearchViewHolder>
                (DataProfilePromote.class,R.layout.profilepromote_row,SearchViewHolder.class,dataQuery) {

            @Override
            protected void populateViewHolder(final SearchViewHolder viewHolder, final DataProfilePromote model, final int position) {

                final Boolean[] checklike = new Boolean[1];
                checklike[0] = false;

                viewHolder.setName(model.username);
                viewHolder.setLocation(model.district,model.province);

                if(!model.BeauticianProfile.equals("")){
                    viewHolder.setProfile(getActivity().getApplicationContext(),model.BeauticianProfile);
                }

                if(!model.picture1.equals("")){
                    viewHolder.setPicture1(getActivity().getApplicationContext(),model.picture1);
                }
                if (!model.picture2.equals("")) {
                    viewHolder.setPicture2(getActivity().getApplicationContext(), model.picture2);
                }
                if (!model.picture3.equals("")) {
                    viewHolder.setPicture3(getActivity().getApplicationContext(), model.picture3);
                }

                if(model.S01 != 0 && search.search.equals("S01")){
                    viewHolder.setStart(model.S01);
                }
                if (model.S02 != 0 && search.search.equals("S02")) {
                    viewHolder.setStart(model.S02);
                }
                if (model.S03 != 0 && search.search.equals("S03")) {
                    viewHolder.setStart(model.S03);
                }
                if (model.S04 != 0 && search.search.equals("S04")) {
                    viewHolder.setStart(model.S04);
                }

                DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
                mRoot.child("customer-liked").child(mFirebaseUser.getUid()).child(model.uid).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataCustomerLiked like = dataSnapshot.getValue(DataCustomerLiked.class);
                        if (like != null) {
                            viewHolder.setLike();
                            checklike[0] = true;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }


                });

                mRoot.child("beautician-verified").child(model.uid).addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataVerified verified = dataSnapshot.getValue(DataVerified.class);
                        if (verified == null) {
                            Toast.makeText(getActivity(), "Error: could not fetch user.", Toast.LENGTH_LONG).show();
                        } else {
                            if (verified.makeup != null||verified.hairstyle != null||verified.hairdressing != null) {
                                viewHolder.setVerified();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                viewHolder.like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(checklike[0]==true ){
                            DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
                            mRoot.child("customer-liked").child(mFirebaseUser.getUid()).child(model.uid).removeValue();
                            viewHolder.setUnLike();
                            checklike[0]=false;
                        }
                        else{

                            final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                            //DatabaseReference databaseBeauLike = mRootRef.child("beautician-liked");

                            final String cshow = getRef(position).getKey();

                            final HashMap<String, Object> CustomerValues = new HashMap<>();

                            //Keep beautician profile
                            CustomerValues.put("name",model.username);
                            CustomerValues.put("profile",model.BeauticianProfile);
                            CustomerValues.put("uid",model.uid);

                            mRootRef.child("customer").child(mFirebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);
                                    if (user == null) {
                                        Toast.makeText(getActivity(), "Error: could not fetch user.", Toast.LENGTH_LONG).show();
                                    } else {

                                        final HashMap<String, Object> BeauticianValues = new HashMap<>();

                                        //Keep beautician profile
                                        BeauticianValues.put("name",user.firstname);
                                        BeauticianValues.put("profile","");
                                        BeauticianValues.put("uid",model.uid);

                                        Map<String,Object> childUpdate = new HashMap<>();
                                        childUpdate.put("/customer-liked/"+mFirebaseUser.getUid()+"/"+model.uid, CustomerValues);
                                        childUpdate.put("/beautician-liked/"+model.uid+"/"+mFirebaseUser.getUid().toString(), BeauticianValues);

                                        mRootRef.updateChildren(childUpdate);

                                        viewHolder.setLike();
                                        checklike[0]=true;


                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }


                            });


                        }



                    }
                });

                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    //private static final String TAG = "Promotion";
                    final String cshow = getRef(position).getKey();
                    @Override
                    public void onClick(View view) {

                        Intent cPro = new Intent(getActivity(),BeauticianProfile.class);
                        cPro.putExtra("uid",  model.uid);
                        cPro.putExtra("username",model.username);
                        cPro.putExtra("search", search.search);
                        cPro.putExtra("word", "");
                        startActivity(cPro);
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