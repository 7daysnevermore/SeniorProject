package com.example.captain_pc.beautyblinkcustomer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private TextView birthday;
    private int year, month, day;
    private int yyyy, mm, dd;

    private RadioGroup radioGroup_gender;
    private RadioButton button_gender;

    private TextView inputEmail;
    private TextView inputPassword;
    private TextView inputFirstname;
    private TextView inputLastname;
    private TextView inputPhoneNo;
    private TextView inputAddr_num;
    private TextView inputAddr_s_dist;
    private TextView inputAddr_dist;
    private TextView inputAddr_province;
    private TextView inputAddr_code;
    private String input_gender;

    private int s01_price;
    private int s02_price;
    private int s03_price;
    private int s04_price;

    private EditText inputS01;
    private EditText inputS02;
    private EditText inputS03;
    private EditText inputS04;


    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                //
            }


        };

        //find view by id
        dateView = (TextView) findViewById(R.id.button1);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        birthday = (TextView) findViewById(R.id.birthdate);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.pass);
        inputFirstname = (EditText) findViewById(R.id.fname);
        inputLastname = (EditText) findViewById(R.id.lname);
        inputPhoneNo = (EditText) findViewById(R.id.phone);
        inputAddr_num = (EditText) findViewById(R.id.addressnum);
        inputAddr_s_dist = (EditText) findViewById(R.id.sub_district);
        inputAddr_dist = (EditText) findViewById(R.id.district);
        inputAddr_province = (EditText) findViewById(R.id.province);
        inputAddr_code = (EditText) findViewById(R.id.code);

        findViewById(R.id.btn_continue).setOnClickListener(this);

    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        birthday.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        yyyy = year;
        mm = month;
        dd = day;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_continue:

                addNewUser();
                break;
        }
    }

    public void addNewUser() {

        radioGroup_gender = (RadioGroup) findViewById(R.id.gender);
        int selectedId = radioGroup_gender.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        button_gender = (RadioButton) findViewById(selectedId);
        input_gender = button_gender.getText().toString();


        //Get value
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        //showDate(year, month + 1, day);

        final String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();
        final String fname = inputFirstname.getText().toString();
        final String lname = inputLastname.getText().toString();
        final String phone = inputPhoneNo.getText().toString();
        final String addr_num = inputAddr_num.getText().toString();
        final String addr_s_dist = inputAddr_s_dist.getText().toString();
        final String addr_dist = inputAddr_dist.getText().toString();
        final String addr_province = inputAddr_province.getText().toString();
        final String addr_code = inputAddr_code.getText().toString();

        //create user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(Register.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Register.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            //Get current to pull UID and email
                            mFirebaseAuth = FirebaseAuth.getInstance();
                            mFirebaseUser = mFirebaseAuth.getCurrentUser();

                            //create root of Beautician
                            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

                            DatabaseReference mUsersRef = mRootRef.child("beautician");

                            HashMap<String, Object> UserValues = new HashMap<>();
                            UserValues.put("email", email);
                            UserValues.put("firstname", fname);
                            UserValues.put("lastname", lname);
                            UserValues.put("phone", phone);
                            UserValues.put("birthday", dd + "/" + mm + "/" + yyyy);
                            UserValues.put("gender", input_gender);
                            UserValues.put("address_number", addr_num);
                            UserValues.put("address_sub_district", addr_s_dist);
                            UserValues.put("address_district", addr_dist);
                            UserValues.put("address_province", addr_province);
                            UserValues.put("address_code", addr_code);
                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put(mFirebaseUser.getUid(), UserValues);

                            mUsersRef.updateChildren(childUpdates);

                        }
                    }
                });


    }



    }