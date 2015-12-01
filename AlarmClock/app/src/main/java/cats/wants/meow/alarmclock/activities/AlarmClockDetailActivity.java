package cats.wants.meow.alarmclock.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import cats.wants.meow.alarmclock.R;
import cats.wants.meow.alarmclock.fragments.AlarmClocksListFragment;
import cats.wants.meow.alarmclock.models.AlarmClock.AlarmClock;

public class AlarmClockDetailActivity extends Activity {

    private AlarmClock alarmClock;
    private EditText messageEditText;
    private TextView songNameTextView;
    private TimePicker alarmTime;
    private DatePicker alarmDate;
    private Button saveButton;
    private Button chooseSongButton;

    protected void getAlarmClockModel() throws RuntimeException {
        int alarmClockId = getIntent().getIntExtra(AlarmClocksListFragment.ALARM_CLOCK_ID, -1);
        if (alarmClockId == -1) {
            throw new RuntimeException("Can't get alarm clock id from intent");
        }

        try {
            alarmClock = AlarmClock.getAlarmClockManager().getAlarmClock(alarmClockId);

            messageEditText.setText(alarmClock.getName());
            songNameTextView.setText(alarmClock.getSong().getName());

            alarmTime.setHour(alarmClock.getAlarmTime().getHours());
            alarmTime.setMinute(alarmClock.getAlarmTime().getMinutes());

            alarmDate.updateDate(
                    alarmClock.getAlarmTime().getYear(),
                    alarmClock.getAlarmTime().getMonth(),
                    alarmClock.getAlarmTime().getDay()
            );
        }
        catch(SQLException ex) {
            Log.e(this.getClass().getSimpleName(), "Error: " + ex.getMessage());
        }
    }

    protected void updateAlarmClockModel() {
        try {
            Date date = new Date();
            date.setHours(alarmTime.getHour());
            date.setMinutes(alarmTime.getMinute());
            date.setDate(alarmDate.getDayOfMonth());
            date.setMonth(alarmDate.getMonth());
            date.setYear(alarmDate.getYear());

            alarmClock.setAlarmTime(date);
            alarmClock.setName(messageEditText.getText().toString());
            AlarmClock.getAlarmClockManager().update(alarmClock);
        }
        catch (SQLException ex) {
            Log.e(this.getClass().getSimpleName(), "Error: " + ex.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) throws RuntimeException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock_detail);
        final AlarmClockDetailActivity context = this;

        messageEditText = (EditText)this.findViewById(R.id.name_edit_text);
        songNameTextView = (TextView)this.findViewById(R.id.song_name_text_view);
        saveButton = (Button)this.findViewById(R.id.save_button);
        chooseSongButton = (Button)this.findViewById(R.id.choose_song_button);
        alarmTime = (TimePicker)this.findViewById(R.id.alarm_time_picker);
        alarmDate = (DatePicker)this.findViewById(R.id.alarm_date_picker);
        getAlarmClockModel();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.updateAlarmClockModel();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

        chooseSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = context.getIntent();
                intent.setClass(context, SongListActivity.class);
                startActivity(intent);
            }
        });
    }
}
