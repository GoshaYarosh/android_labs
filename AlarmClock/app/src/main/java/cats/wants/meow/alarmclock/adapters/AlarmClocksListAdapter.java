package cats.wants.meow.alarmclock.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cats.wants.meow.alarmclock.R;
import cats.wants.meow.alarmclock.models.AlarmClock.AlarmClock;

public class AlarmClocksListAdapter extends ArrayAdapter<AlarmClock> {

    static class ViewHolder {

        public TextView nameTextView;
        public TextView timeTextView;
        public ViewHolder(View view) {
            this.nameTextView = (TextView)view.findViewById(R.id.name);
            this.timeTextView = (TextView)view.findViewById(R.id.time);
        }

    }

    public AlarmClocksListAdapter(Activity activity, List<AlarmClock> alarmClocks) {
        super(activity, R.layout.listitem_alarm_clock, alarmClocks);
    }

    @Override
    public View getView(int position, View alarmClockView, ViewGroup parent) {
        if (alarmClockView == null) {
            Activity activity = (Activity)this.getContext();
            LayoutInflater inflater = activity.getLayoutInflater();
            alarmClockView = inflater.inflate(R.layout.listitem_alarm_clock, null, false);
            alarmClockView.setTag(new ViewHolder(alarmClockView));
        }

        AlarmClock alarmClock = this.getItem(position);
        ViewHolder viewHolder = (ViewHolder)alarmClockView.getTag();
        viewHolder.nameTextView.setText(alarmClock.getName());
        return alarmClockView;
    }
}
