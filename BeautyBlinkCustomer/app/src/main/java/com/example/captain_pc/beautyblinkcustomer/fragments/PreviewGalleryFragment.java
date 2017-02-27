package com.example.captain_pc.beautyblinkcustomer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.captain_pc.beautyblinkcustomer.BeauticianProfile;
import com.example.captain_pc.beautyblinkcustomer.R;
import com.example.captain_pc.beautyblinkcustomer.model.DataGallery;
import com.example.captain_pc.beautyblinkcustomer.model.GalleryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by NunePC on 24/2/2560.
 */

public class PreviewGalleryFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference,databaseOrder;

    BeauticianProfile beauti;

    Query query1,query2;

    public PreviewGalleryFragment(){ super(); }

    public static PreviewGalleryFragment newInstance(){
        PreviewGalleryFragment fragment = new PreviewGalleryFragment();
        Bundle args = new Bundle(); //Argument
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_preview_gallery,container,false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView){

        beauti = (BeauticianProfile) getActivity();

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("beautician-gallery"+"/"+beauti.b_uid);

        query1 = databaseReference.orderByChild("timestamp");
        //final DatabaseReference databaseRef = query1.getRef();
        //query2 = databaseRef.orderByValue();
        //professor promotion feeds
        recyclerView =(RecyclerView)rootView.findViewById(R.id.recyclerview_gall);
        recyclerView.setHasFixedSize(true);

        //Order from latest data
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),3);
        mLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        final FirebaseRecyclerAdapter<DataGallery,GalleryViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataGallery, GalleryViewHolder>
                (DataGallery.class,R.layout.gallery_row,GalleryViewHolder.class,query1) {

            @Override
            protected void populateViewHolder(GalleryViewHolder viewHolder, final DataGallery model, final int position) {

                viewHolder.setImage(getActivity().getApplicationContext(),model.getImage());

                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    //private static final String TAG = "Promotion";
                    final String cshow = getRef(position).getKey();
                    @Override
                    public void onClick(View view) {
                        //Log.w(TAG, "You clicked on "+position);
                        //firebaseRecyclerAdapter.getRef(position).removeValue();
                        //Toast.makeText(Promotion.this, "This is my Toast message!",
                        // Toast.LENGTH_LONG).show();
                        /*HashMap<String, Object> galleryValues = new HashMap<>();
                        galleryValues.put("key",cshow);
                        galleryValues.put("caption",model.getCaption());
                        galleryValues.put("image",model.getImage());
                        galleryValues.put("uid",model.getUid());
                        galleryValues.put("username",model.getName());
                        galleryValues.put("timestamp",model.getTimestamp());
                        Intent cPro = new Intent(getActivity(),GalleryDetails.class);
                        cPro.putExtra("gallery",  galleryValues);
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