package com.example.captain_pc.beautyblinkcustomer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.captain_pc.beautyblinkcustomer.model.DataProfilePromote;
import com.example.captain_pc.beautyblinkcustomer.model.SearchByUserViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by NunePC on 6/2/2560.
 */

public class SpecificByUser extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private Query databaseQuery;

    EditText word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_search);


        word = (EditText) findViewById(R.id.word);

        word.setHint("Search by user");
        word.setFocusableInTouchMode(true);
        word.requestFocus();

        word.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ( (actionId == EditorInfo.IME_ACTION_SEARCH) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))) {


                    showSearch();
                    /*Intent cate = new Intent(SpecificSearch.this,SearchDetails.class);
                    cate.putExtra("search",search);
                    cate.putExtra("word",word.getText().toString());
                    startActivity(cate);
                    return true;*/

                }

                return false;
            }
        });



    }

    public void showSearch(){

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("profilepromote");
        //professor promotion feeds
        recyclerView =(RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        String search = word.getText().toString().toLowerCase();

        if(!search.equals("")){
            //Method to multiple queries
            databaseQuery = databaseReference.orderByChild("name").equalTo(search);

            //Order from latest data
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            final FirebaseRecyclerAdapter<DataProfilePromote,SearchByUserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataProfilePromote, SearchByUserViewHolder>
                    (DataProfilePromote.class,R.layout.specific_user_row,SearchByUserViewHolder.class,databaseQuery) {

                @Override
                protected void populateViewHolder(SearchByUserViewHolder viewHolder, DataProfilePromote model, final int position) {

                    viewHolder.setName(model.username);

                    if(!model.BeauticianProfile.equals("")){
                        viewHolder.setProfile(getApplicationContext(),model.BeauticianProfile);
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

    }



}
