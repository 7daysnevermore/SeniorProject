package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.captain_pc.beautyblinkcustomer.fragments.SearchByPlace;
import com.example.captain_pc.beautyblinkcustomer.fragments.SearchByUser;
import com.example.captain_pc.beautyblinkcustomer.fragments.SearchLatest;
import com.example.captain_pc.beautyblinkcustomer.fragments.SearchNearby;
import com.example.captain_pc.beautyblinkcustomer.fragments.SearchPopular;
import com.example.captain_pc.beautyblinkcustomer.fragments.SearchPrice;


/**
 * Created by NunePC on 31/1/2560.
 */

public class SpecificSearch extends AppCompatActivity implements View.OnClickListener {

    EditText word;
    String wording,search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_search);

        if(savedInstanceState==null){
            //first create
            //Place fragment

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentcontainer,new SearchByUser())
                    .commit();
        }

        search = getIntent().getStringExtra("search");
        wording = getIntent().getStringExtra("word");

        word = (EditText) findViewById(R.id.word);

        word.setHint(search);
        word.setFocusableInTouchMode(true);
        word.requestFocus();

        word.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ( (actionId == EditorInfo.IME_ACTION_SEARCH) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))) {

                    Intent cate = new Intent(SpecificSearch.this,SearchDetails.class);
                    cate.putExtra("search",search);
                    cate.putExtra("word",word.getText().toString());
                    startActivity(cate);
                    return true;

                }

                return false;
            }
        });


        //tab button
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.user).setOnClickListener(this);
        findViewById(R.id.place).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                Intent cate = new Intent(SpecificSearch.this,SearchDetails.class);
                cate.putExtra("search",search);
                cate.putExtra("word","");
                startActivity(cate);
                break;
            case R.id.user:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, SearchByUser.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.place:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentcontainer, SearchByPlace.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;

        }
    }



}
