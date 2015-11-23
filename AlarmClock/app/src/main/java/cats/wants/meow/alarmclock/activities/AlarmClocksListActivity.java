package cats.wants.meow.alarmclock.activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;

import cats.wants.meow.alarmclock.R;
import cats.wants.meow.alarmclock.adapters.AlarmClocksListAdapter;
import cats.wants.meow.alarmclock.db.AlarmClockDAO;
import cats.wants.meow.alarmclock.db.DatabaseHelperFactory;
import cats.wants.meow.alarmclock.models.AlarmClock;

public class AlarmClocksListActivity extends ListActivity {

    public static final String ALARM_CLOCK_ID = "cat.wants.meow.app.ALARM_CLOCK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clocks_list);
        final Context context = this;

        try
        {
            AlarmClockDAO alarmClockDao = DatabaseHelperFactory.getHelper().getAlarmClockDAO();
            List<AlarmClock> alarmClocks = alarmClockDao.getAllAlarmClocks();
            setListAdapter(new AlarmClocksListAdapter(this, alarmClocks));
        }
        catch (SQLException ex) {
            Log.e(this.getClass().getSimpleName(), "Error getting access to db");
        }

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListAdapter alarmClocksList = ((ListView)parent).getAdapter();
                AlarmClock alarmClock = (AlarmClock)alarmClocksList.getItem(position);

                Intent intent = new Intent(context, AlarmClockDetailActivity.class);
                intent.putExtra(ALARM_CLOCK_ID, alarmClock.getId());
                startActivity(intent);
            }
        });
    }
}
