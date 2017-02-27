package com.example.captain_pc.beautyblinkcustomer.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.captain_pc.beautyblinkcustomer.PromotionDetails;
import com.example.captain_pc.beautyblinkcustomer.R;
import com.example.captain_pc.beautyblinkcustomer.SearchDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.captain_pc.beautyblinkcustomer.model.DataPromotion;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import java.util.HashMap;

public class Search extends Fragment {

    private EditText mF;
    private Button mB;
    private DatabaseReference mRef;
    private TextView postP;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private RelativeLayout btn_cate1, btn_cate2, btn_cate3, btn_cate4;

    public Search() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        mRef = FirebaseDatabase.getInstance().getReference();
        // mRef.setValue(getContext());
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(final View rootView) {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("promotion");
        Query query1 = databaseReference.orderByChild("timestamp");
        //professor promotion feeds
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //Order from latest data
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setOrientation(GridLayoutManager.VERTICAL);


        final FirebaseRecyclerAdapter<DataPromotion, PromotionViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataPromotion, PromotionViewHolder>
                (DataPromotion.class, R.layout.promotion_row, PromotionViewHolder.class, query1) {

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


        rootView.findViewById(R.id.btn_cate1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cate = new Intent(getActivity(),SearchDetails.class);
                cate.putExtra("search","S01");
                cate.putExtra("word","");
                cate.putExtra("lat", "");
                cate.putExtra("lng", "");
                startActivity(cate);
            }
        });
        rootView.findViewById(R.id.btn_cate2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cate = new Intent(getActivity(),SearchDetails.class);
                cate.putExtra("search","S02");
                cate.putExtra("word","");
                cate.putExtra("lat", "");
                cate.putExtra("lng", "");
                startActivity(cate);
            }
        });
        rootView.findViewById(R.id.btn_cate3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cate = new Intent(getActivity(),SearchDetails.class);
                cate.putExtra("search","S03");
                cate.putExtra("word","");
                cate.putExtra("lat", "");
                cate.putExtra("lng", "");
                startActivity(cate);
            }
        });
        rootView.findViewById(R.id.btn_cate4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cate = new Intent(getActivity(),SearchDetails.class);
                cate.putExtra("search","S04");
                cate.putExtra("word","");
                cate.putExtra("lat", "");
                cate.putExtra("lng", "");
                startActivity(cate);
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore Instance State here
        }
    }

    public static Search newInstance() {
        Search fragment = new Search();
        Bundle args = new Bundle(); //Argument
        fragment.setArguments(args);
        return fragment;
    }



    public static class PromotionViewHolder extends RecyclerView.ViewHolder  {

        View mview;

        public PromotionViewHolder(View itemView){
            super(itemView);
            mview=itemView;

        }

        public void setPromotion(String promotion){
            TextView post_promotion = (TextView)mview.findViewById(R.id.promotion);
            post_promotion.setText(promotion);
        }
        public void setImage(Context context, String image){
            ImageView img = (ImageView)mview.findViewById(R.id.post_image);

            Picasso.with(context).load(image).fit().centerCrop().into(img);
        }
        public void setPrice(String price){
            TextView post_price = (TextView)mview.findViewById(R.id.price);
            post_price.setText(price+" Baht");
            post_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        public void setSale(String sale){
            TextView post_sale= (TextView)mview.findViewById(R.id.sale);
            post_sale.setText(sale);
        }
    }
}


