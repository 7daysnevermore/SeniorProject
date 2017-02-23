package com.example.captain_pc.beautyblinkcustomer.fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.captain_pc.beautyblinkcustomer.BeauticianProfile;
import com.example.captain_pc.beautyblinkcustomer.R;
import com.example.captain_pc.beautyblinkcustomer.decorators.EventDecorator;
import com.example.captain_pc.beautyblinkcustomer.decorators.HighlightWeekendsDecorator;
import com.example.captain_pc.beautyblinkcustomer.decorators.MySelectorDecorator;
import com.example.captain_pc.beautyblinkcustomer.decorators.OneDayDecorator;
import com.example.captain_pc.beautyblinkcustomer.model.DataPlanner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by NunePC on 24/2/2560.
 */

public class PreviewPlannerFragment extends Fragment implements OnDateSelectedListener {

    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();

    MaterialCalendarView materialCalendarView;
    Integer dd,mm,yyyy;
    private RecyclerView recyclerView;
    BeauticianProfile beauti;
    View view;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference databaseReference;


    public PreviewPlannerFragment(){ super(); }

    public static PreviewPlannerFragment newInstance(){
        PreviewPlannerFragment fragment = new PreviewPlannerFragment();
        Bundle args = new Bundle(); //Argument
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_preview_planner,container,false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView){

        view = rootView;
        beauti = (BeauticianProfile) getActivity();
        //Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        materialCalendarView = (MaterialCalendarView) rootView.findViewById(R.id.calendarView);

        materialCalendarView.setOnDateChangedListener(this);
        materialCalendarView.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        Calendar instance = Calendar.getInstance();
        materialCalendarView.setSelectedDate(instance.getTime());

        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR), Calendar.JANUARY, 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR), Calendar.DECEMBER, 31);

        materialCalendarView.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit();

        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());

        materialCalendarView.addDecorators(
                new MySelectorDecorator(getActivity()),
                new HighlightWeekendsDecorator(),
                oneDayDecorator
        );




    }

    @Override
    public void onStart(){ super.onStart();

    }

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

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //If you change a decorate, you need to invalidate decorators
        oneDayDecorator.setDate(date.getDate());


    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        private FirebaseAuth mFirebaseAuth;
        private FirebaseUser mFirebaseUser;


        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final CalendarDay[] day = new CalendarDay[1];
            final Calendar calendar = Calendar.getInstance();
            final ArrayList<CalendarDay> dates = new ArrayList<>();

            for (int i=0;i<beauti.listevent.size();i++){

                int mo = beauti.listevent.get(i).get("month")-1;
                int da = beauti.listevent.get(i).get("day");
                int ye = beauti.listevent.get(i).get("year");
                calendar.set(ye,mo,da);
                day[0] = CalendarDay.from(calendar);
                dates.add(day[0]);


            }


            return dates;
        }


        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            materialCalendarView.addDecorator(new EventDecorator(Color.RED, calendarDays));

        }
    }

}