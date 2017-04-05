package com.example.captain_pc.beautyblinkcustomer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.captain_pc.beautyblinkcustomer.PromotionDetails;
import com.example.captain_pc.beautyblinkcustomer.R;
import com.example.captain_pc.beautyblinkcustomer.model.DataPromotion;
import com.example.captain_pc.beautyblinkcustomer.model.PromotionViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

/**
 * Created by NunePC on 6/4/2560.
 */

public class FragmentProSer2 extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;

    public FragmentProSer2() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_proser2,container,false);

        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {


        databaseReference = FirebaseDatabase.getInstance().getReference().child("promotion");
        Query query1 = databaseReference.orderByChild("timestamp");
        final DatabaseReference databaseRef = query1.getRef();
        Query dataQuery1 = databaseRef.orderByChild("service").equalTo("S02");
        //professor promotion feeds
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        //Order from latest data
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        final FirebaseRecyclerAdapter<DataPromotion, PromotionViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataPromotion, PromotionViewHolder>
                (DataPromotion.class, R.layout.promotion_row, PromotionViewHolder.class, dataQuery1) {

            @Override
            protected void populateViewHolder(PromotionViewHolder viewHolder, final DataPromotion model, final int position) {

                viewHolder.setImage(getActivity().getApplicationContext(), model.getImage());
                viewHolder.setPromotion(model.getPromotion());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setSale(model.getSale());

                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    //private static final String TAG = "Promotion";
                    final String cshow = getRef(position).getKey();

                    @Override
                    public void onClick(View view) {
                        //Log.w(TAG, "You clicked on "+position);
                        //firebaseRecyclerAdapter.getRef(position).removeValue();
                        //Toast.makeText(Promotion.this, "This is my Toast message!",
                        // Toast.LENGTH_LONG).show();
                        HashMap<String, Object> promotionValues = new HashMap<>();
                        promotionValues.put("key", cshow);
                        promotionValues.put("promotion", model.getPromotion());
                        promotionValues.put("image", model.getImage());
                        promotionValues.put("details", model.getDetails());
                        promotionValues.put("price", model.getPrice());
                        promotionValues.put("sale", model.getSale());
                        promotionValues.put("dateFrom", model.getDateFrom());
                        promotionValues.put("dateTo", model.getDateTo());
                        promotionValues.put("uid", model.getUid());
                        promotionValues.put("name", model.getName());
                        promotionValues.put("service", model.getService());
                        promotionValues.put("status", model.getStatus());
                        promotionValues.put("profile", model.getProfile());
                        Intent cPro = new Intent(getActivity(), PromotionDetails.class);
                        cPro.putExtra("promotion", promotionValues);
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

    public static FragmentProSer2 newInstance(){
        FragmentProSer2 fragment = new FragmentProSer2();
        Bundle args = new Bundle(); //Argument
        fragment.setArguments(args);
        return fragment;
    }

}
