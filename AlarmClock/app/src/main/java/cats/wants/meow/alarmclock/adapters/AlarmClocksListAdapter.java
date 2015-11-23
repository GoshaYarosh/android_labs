package cats.wants.meow.alarmclock.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cats.wants.meow.alarmclock.R;
import cats.wants.meow.alarmclock.models.AlarmClock;

public class AlarmClocksListAdapter extends ArrayAdapter<AlarmClock> {

    private final Activity activity;
    private final List<AlarmClock> alarmClocks;

    public AlarmClocksListAdapter(Activity activity, List<AlarmClock> alarmClocks) {
        super(activity, R.layout.listitem_alarm_clock, alarmClocks);
        this.activity = activity;
        this.alarmClocks = alarmClocks;
    }

    static class ViewHolder {
        public TextView nameTextView;
        public TextView timeTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View alarmClockView = convertView;
        ViewHolder viewHolder;

        if (alarmClockView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            alarmClockView = inflater.inflate(R.layout.listitem_alarm_clock, null, false);
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = (TextView)alarmClockView.findViewById(R.id.name);
            viewHolder.timeTextView = (TextView)alarmClockView.findViewById(R.id.time);
            alarmClockView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)alarmClockView.getTag();
        }

        AlarmClock alarmClock = this.alarmClocks.get(position);
        viewHolder.nameTextView.setText(alarmClock.getName());

        return alarmClockView;
    }
}
