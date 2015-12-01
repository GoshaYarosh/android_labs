package cats.wants.meow.alarmclock.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cats.wants.meow.alarmclock.R;

public class AboutFragment extends Fragment {

    native static String nativeGetAppName();

    static {
        System.loadLibrary("alarm_clock_app");
    }

    public AboutFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        ((TextView)view.findViewById(R.id.app_name_text_view)).setText(nativeGetAppName());
        return view;
    }
}
