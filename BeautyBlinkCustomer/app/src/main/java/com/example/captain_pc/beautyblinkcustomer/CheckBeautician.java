package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.captain_pc.beautyblinkcustomer.fragments.Search;
import com.example.captain_pc.beautyblinkcustomer.model.CheckBData;
import com.example.captain_pc.beautyblinkcustomer.model.DataPromotion;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CheckBeautician extends AppCompatActivity {
    HashMap<String, Object> requestValues;
    DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    String kreq,uid,uu,uk;
    Button accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_beautician);
        requestValues = (HashMap<String, Object>) getIntent().getExtras().getSerializable("request");
        kreq=requestValues.get("keyrequest").toString();
        Log.d("kreq",""+kreq);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("offer/"+kreq);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        uid = mFirebaseUser.getUid().toString();

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        final FirebaseRecyclerAdapter<CheckBData, CheckBeautician.CheckViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CheckBData, CheckBeautician.CheckViewHolder>
                (CheckBData.class, R.layout.beautician_checklist, CheckBeautician.CheckViewHolder.class, databaseReference) {

            @Override
            protected void populateViewHolder(CheckViewHolder viewHolder, CheckBData model, int position) {
                viewHolder.setStatus(model.getStatus());
                viewHolder.setMaxprice(model.getMaxprice());
                viewHolder.setRe(model.getBeauticianoffer());
                viewHolder.setUid(model.getUid());
                viewHolder.setKe(model.getKey());
                TextView tv = (TextView)viewHolder.mview.findViewById(R.id.uidBeau);
                TextView tk = (TextView)viewHolder.mview.findViewById(R.id.keyBeau);
                uu = tv.getText().toString();
                uk = tk.getText().toString();
                Log.d("UIDDD",""+uu);
                Log.d("UIDDD",""+uk);
                accept =(Button)viewHolder.mview.findViewById(R.id.goreq);
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("/customer-request/" +uid+"/"+kreq);

                        mRootRef.child("status").setValue("Unpaid");

                      DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference().child("/beauticianbusiness/" +uu+"/"+uk);
                        RootRef.child("afterofferstatus").setValue("Unpaid");

                        //mRootRef.child("color").setValue("#85FC56");
                        Intent intent = new Intent(CheckBeautician.this,MainActivity.class);
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

    public static class CheckViewHolder extends RecyclerView.ViewHolder {
        View mview;
        public CheckViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void setUid(String u){
            TextView tv = (TextView)mview.findViewById(R.id.uidBeau);
            tv.setText(u);
        }
        public void setKe(String ke){
            TextView tv = (TextView)mview.findViewById(R.id.keyBeau);
            tv.setText(ke);
        }
        public void setStatus(String status){
            TextView tv = (TextView)mview.findViewById(R.id.btnDes);
            tv.setText(status);
        }
        public void setMaxprice(String maxprice){
            TextView tv = (TextView)mview.findViewById(R.id.price_des);
            tv.setText(maxprice);
        }
        public void setRe(String r){
            TextView tv = (TextView)mview.findViewById(R.id.tRequirement);
            tv.setText(r);
        }
    }
}
