package cats.wants.meow.alarmclock.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;

import cats.wants.meow.alarmclock.R;
import cats.wants.meow.alarmclock.db.AlarmClockDAO;
import cats.wants.meow.alarmclock.db.DatabaseHelperFactory;
import cats.wants.meow.alarmclock.models.AlarmClock;

public class AlarmClockDetailActivity extends Activity {

    private AlarmClock alarmClock;
    private EditText messageEditText;
    private Button saveButton;

    protected void findAlarmClockModel() throws RuntimeException {
        Intent intent = getIntent();
        int alarmClockId = intent.getIntExtra(AlarmClocksListActivity.ALARM_CLOCK_ID, -1);
        if (alarmClockId == -1) {
            throw new RuntimeException("Can't get alarm clock id from intent");
        }

        try {
            AlarmClockDAO alarmClockDao = DatabaseHelperFactory.getHelper().getAlarmClockDAO();
            alarmClock = alarmClockDao.getAlarmClock(alarmClockId);

            messageEditText.setText(alarmClock.getName());
        }
        catch(SQLException ex) {
            Log.e(this.getClass().getSimpleName(), "Exception: " + ex.getMessage());
        }
    }

    protected void updateAlarmClockModel() {
        try {
            AlarmClockDAO alarmClockDao = DatabaseHelperFactory.getHelper().getAlarmClockDAO();
            alarmClock.setName(messageEditText.getText().toString());
            alarmClockDao.update(alarmClock);
        }
        catch (SQLException ex) {
            Log.e(this.getClass().getSimpleName(), "Exception: " + ex.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) throws RuntimeException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock_detail);

        messageEditText = (EditText)this.findViewById(R.id.name_edit_text);
        saveButton = (Button)this.findViewById(R.id.save_button);

        findAlarmClockModel();

        final AlarmClockDetailActivity context = this;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.updateAlarmClockModel();
                Intent intent = new Intent(context, AlarmClocksListActivity.class);
                startActivity(intent);
            }
        });
    }
}
