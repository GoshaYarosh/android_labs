package cats.wants.meow.alarmclock.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cats.wants.meow.alarmclock.R;
import cats.wants.meow.alarmclock.activities.AlarmClockDetailActivity;
import cats.wants.meow.alarmclock.adapters.AlarmClocksListAdapter;
import cats.wants.meow.alarmclock.models.AlarmClock.AlarmClock;
import cats.wants.meow.alarmclock.models.AlarmClock.AlarmClockFactory;

public class AlarmClocksListFragment extends Fragment {

    public static int CREATE_ALARM_CLOCK = 1;
    public static int UPDATE_ALARM_CLOCK = 2;
    public static final String ALARM_CLOCK = "cat.wants.meow.app.ALARM_CLOCK";
    public static final String IS_ALARM_CLOCK_NEW = "cat.wants.meow.app.IS_ALARM_CLOCK_NEW";

    public AlarmClocksListFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            ListView alarmClocksListView = (ListView)this.getView().findViewById(R.id.alarm_clocks_list_view);
            List<AlarmClock> alarmClocks= AlarmClock.getAlarmClockManager().getAllAlarmClocks();
            alarmClocksListView.setAdapter(new AlarmClocksListAdapter(this.getActivity(), alarmClocks));
        } catch (SQLException e) {
            Log.e(this.getClass().getSimpleName(), "Error: " + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Activity context = this.getActivity();
        View view = inflater.inflate(R.layout.fragment_alarm_clocks_list, container, false);
        Button addAlarmClockButton = (Button)view.findViewById(R.id.add_alarm_clock_button);
        ListView alarmClocksListView = (ListView)view.findViewById(R.id.alarm_clocks_list_view);

        try
        {
            List<AlarmClock> alarmClocks = AlarmClock.getAlarmClockManager().getAllAlarmClocks();
            alarmClocksListView.setAdapter(new AlarmClocksListAdapter(this.getActivity(), alarmClocks));
        }
        catch (SQLException ex) {
            Log.e(this.getClass().getSimpleName(), "Error: " + ex.getMessage());
        }

        alarmClocksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListAdapter alarmClocksList = ((ListView) parent).getAdapter();
                AlarmClock alarmClock = (AlarmClock) alarmClocksList.getItem(position);

                Intent intent = new Intent(context, AlarmClockDetailActivity.class);
                intent.putExtra(ALARM_CLOCK, AlarmClock.toBundle(alarmClock));
                intent.putExtra(IS_ALARM_CLOCK_NEW, false);
                context.startActivity(intent);
            }
        });

        addAlarmClockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AlarmClockDetailActivity.class);
                intent.putExtra(ALARM_CLOCK, AlarmClock.toBundle(new AlarmClock()));
                intent.putExtra(IS_ALARM_CLOCK_NEW, true);
                context.startActivity(intent);
            }
        });

        final GestureLibrary gestureLibrary = GestureLibraries.fromRawResource(this.getActivity(), R.raw.gestures);
        gestureLibrary.load();
        final GestureOverlayView gestures = (GestureOverlayView)view.findViewById(R.id.gesture_view);
        gestures.addOnGesturePerformedListener(new GestureOverlayView.OnGesturePerformedListener() {
            @Override
            public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
                ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);
                if (predictions.size() > 0) {
                    Prediction prediction = predictions.get(0);
                    if (prediction.score > 1.0) {
                        if (prediction.name.equals("Add alarm clock")) {
                            Intent intent = new Intent(context, AlarmClockDetailActivity.class);
                            intent.putExtra(ALARM_CLOCK, AlarmClock.toBundle(new AlarmClock()));
                            intent.putExtra(IS_ALARM_CLOCK_NEW, true);
                            context.startActivity(intent);
                        }
                    }
                }
            }
        });

        return view;
    }

}
