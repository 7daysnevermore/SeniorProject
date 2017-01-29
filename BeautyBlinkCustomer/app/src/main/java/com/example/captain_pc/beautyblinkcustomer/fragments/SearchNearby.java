package com.example.captain_pc.beautyblinkcustomer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.captain_pc.beautyblinkcustomer.R;
import com.example.captain_pc.beautyblinkcustomer.model.DataProfilePromote;
import com.example.captain_pc.beautyblinkcustomer.model.SearchViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by NunePC on 30/1/2560.
 */

public class SearchNearby extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;

    public SearchNearby(){ super(); }

    public static SearchNearby newInstance(){
        SearchNearby fragment = new SearchNearby();
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

        //Order from latest data
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        final FirebaseRecyclerAdapter<DataProfilePromote,SearchViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataProfilePromote, SearchViewHolder>
                (DataProfilePromote.class,R.layout.profilepromote_row,SearchViewHolder.class,databaseReference) {

            @Override
            protected void populateViewHolder(SearchViewHolder viewHolder, final DataProfilePromote model, final int position) {

                viewHolder.setName(model.name);
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

                if(model.S01 != 0){
                    viewHolder.setS01(model.S01);
                }
                if (model.S02 != 0) {
                    viewHolder.setS02(model.S02);
                }
                if (model.S03 != 0) {
                    viewHolder.setS03(model.S03);
                }
                if (model.S04 != 0) {
                    viewHolder.setS04(model.S04);
                }


                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    //private static final String TAG = "Promotion";
                    final String cshow = getRef(position).getKey();
                    @Override
                    public void onClick(View view) {
                        //Log.w(TAG, "You clicked on "+position);
                        //firebaseRecyclerAdapter.getRef(position).removeValue();
                        //Toast.makeText(Promotion.this, "This is my Toast message!",
                        // Toast.LENGTH_LONG).show();

                        /*Intent cPro = new Intent(getActivity(),PromotionDetails.class);
                        cPro.putExtra("uid",  model.uid);
                        startActivity(cPro);*/
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
