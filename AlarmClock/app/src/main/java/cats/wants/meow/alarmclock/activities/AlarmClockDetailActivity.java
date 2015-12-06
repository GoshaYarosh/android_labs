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
import java.util.Date;

import cats.wants.meow.alarmclock.R;
import cats.wants.meow.alarmclock.fragments.AlarmClocksListFragment;
import cats.wants.meow.alarmclock.models.AlarmClock.AlarmClock;
import cats.wants.meow.alarmclock.models.Song.Song;

public class AlarmClockDetailActivity extends Activity {

    private static int REQUEST_CHOOSE_SONG = 0;

    private AlarmClock alarmClock;
    private EditText nameEditText;
    private TextView songNameTextView;
    private TimePicker alarmTime;
    private DatePicker alarmDate;
    private Button saveButton;
    private Button deleteButton;
    private Button chooseSongButton;

    protected void getAlarmClockModel() throws RuntimeException {
        Bundle bundle = getIntent().getBundleExtra(AlarmClocksListFragment.ALARM_CLOCK);
        if (bundle == null) {
            throw new RuntimeException("Can't get alarm clock from intent");
        }

        alarmClock = AlarmClock.fromBundle(bundle);
        if (alarmClock.getId() != null) {
            nameEditText.setText(alarmClock.getName());
            songNameTextView.setText(alarmClock.getSong().getName());
            alarmTime.setHour(alarmClock.getAlarmTime().getHours());
            alarmTime.setMinute(alarmClock.getAlarmTime().getMinutes());
            alarmDate.updateDate(
                    alarmClock.getAlarmTime().getYear(),
                    alarmClock.getAlarmTime().getMonth(),
                    alarmClock.getAlarmTime().getDay()
            );
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
            alarmClock.setName(nameEditText.getText().toString());
            AlarmClock.getAlarmClockManager().createOrUpdate(alarmClock);
        }
        catch (SQLException ex) {
            Log.e(this.getClass().getSimpleName(), "Error: " + ex.getMessage());
        }
    }

    protected void deleteAlarmClockModel() {
        try {
            AlarmClock.getAlarmClockManager().delete(alarmClock);
        } catch (SQLException e) {
            Log.e(this.getClass().getSimpleName(), "Error: " + e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) throws RuntimeException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock_detail);
        final AlarmClockDetailActivity context = this;

        nameEditText = (EditText)this.findViewById(R.id.name_edit_text);
        songNameTextView = (TextView)this.findViewById(R.id.song_name_text_view);
        saveButton = (Button)this.findViewById(R.id.save_button);
        chooseSongButton = (Button)this.findViewById(R.id.choose_song_button);
        deleteButton = (Button)this.findViewById(R.id.delete_button);
        alarmTime = (TimePicker)this.findViewById(R.id.alarm_time_picker);
        alarmDate = (DatePicker)this.findViewById(R.id.alarm_date_picker);

        getAlarmClockModel();
        boolean isAlarmClockNew = this.getIntent().getBooleanExtra(
                AlarmClocksListFragment.IS_ALARM_CLOCK_NEW,
                false
        );
        deleteButton.setVisibility(isAlarmClockNew ? View.GONE : View.VISIBLE);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.updateAlarmClockModel();
                context.finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.deleteAlarmClockModel();
                context.finish();
            }
        });

        chooseSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = context.getIntent();
                intent.setClass(context, SongListActivity.class);
                context.startActivityForResult(intent, REQUEST_CHOOSE_SONG);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String songFileName = data.getStringExtra(SongListActivity.SONG_NAME);
            alarmClock.setSong(new Song(songFileName));
            songNameTextView.setText(alarmClock.getSong().getName());
        }
    }
}
