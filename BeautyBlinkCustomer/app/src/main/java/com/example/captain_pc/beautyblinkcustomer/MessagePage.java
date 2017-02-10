package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.captain_pc.beautyblinkcustomer.fragments.Request;
import com.example.captain_pc.beautyblinkcustomer.model.ChatStructure;
import com.example.captain_pc.beautyblinkcustomer.model.DataRequest;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MessagePage extends AppCompatActivity {
    private DatabaseReference ref ;
    private Button sendMsg;
    private RecyclerView recyclerView;
    private LinearLayout create_request;
    private EditText inputMsg;
    private TextView chatConv;
    private String username,event,key;
    private DatabaseReference root;
    private String temp_key;
    HashMap<String, Object> messageValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_page);
        messageValues = (HashMap<String, Object>) getIntent().getExtras().getSerializable("message");
        //ref.updateChildren(messageValues);

        sendMsg = (Button)findViewById(R.id.btn_send);
        inputMsg = (EditText)findViewById(R.id.msg_input);
        //chatConv = (TextView)findViewById(R.id.descC);
        key = messageValues.get("key").toString();
        event = messageValues.get("event").toString();

        recyclerView =(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        ref = FirebaseDatabase.getInstance().getReference().getRoot().child("message").child(key);
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String,Object>keyfofmsg = new HashMap<String, Object>();
                temp_key=ref.push().getKey();
                ref.updateChildren(keyfofmsg);

                Map<String,Object>map2 = new HashMap<String, Object>();
                map2.put("type","customer");
                map2.put("name","test");
                map2.put("key",key);
                map2.put("tkey",temp_key);
                map2.put("msg",inputMsg.getText().toString());
                ref.child(temp_key).updateChildren(map2);

                DatabaseReference refforkey = FirebaseDatabase.getInstance().getReference().getRoot().child("keyforchat");
                Map<String,Object>mapkey = new HashMap<String, Object>();
                mapkey.put("key",key);
                mapkey.put("tkey",temp_key);
                mapkey.put("type","customer");
                mapkey.put("name","test");
                mapkey.put("msg",inputMsg.getText().toString());
                refforkey.child(key).updateChildren(mapkey);
            }
        });
        final FirebaseRecyclerAdapter<ChatStructure,MessagePage.chatViewholder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ChatStructure, MessagePage.chatViewholder>
                (ChatStructure.class,R.layout.chatty,MessagePage.chatViewholder.class,ref){
            @Override
            protected void populateViewHolder(MessagePage.chatViewholder viewHolder, ChatStructure model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setMsg(model.getMsg());


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


        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private String chat_msg,chat_user_name,type,k,tk;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){

            chat_msg = (String) ((DataSnapshot)i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot)i.next()).getValue();
            k=(String)((DataSnapshot)i.next()).getValue();
            tk=(String)((DataSnapshot)i.next()).getValue();
            type=(String)((DataSnapshot)i.next()).getValue();
          //chatConv.append(chat_user_name +" : "+chat_msg +" \n");
        }


    }
    public static class chatViewholder extends RecyclerView.ViewHolder  {
        View mview;
        public chatViewholder(View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void setName(String name){
            ChatStructure c = new ChatStructure();

            TextView n =(TextView)mview.findViewById(R.id.userName);
            n.setText(name);
        }
        public void setMsg(String msg){
            TextView m = (TextView)mview.findViewById(R.id.msgText);
            m.setText(msg);
        }
    }
}
