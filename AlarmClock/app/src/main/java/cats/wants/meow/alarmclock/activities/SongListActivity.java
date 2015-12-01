package cats.wants.meow.alarmclock.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import cats.wants.meow.alarmclock.R;
import cats.wants.meow.alarmclock.adapters.SongListAdapter;
import cats.wants.meow.alarmclock.fragments.AlarmClocksListFragment;
import cats.wants.meow.alarmclock.models.AlarmClock.AlarmClock;
import cats.wants.meow.alarmclock.models.Song.Song;
import cats.wants.meow.alarmclock.models.Song.SongPlayer;

public class SongListActivity extends ListActivity {

    public final static String SONG_NAME = "cat.wants.meow.app.SONG_NAME";

    private AlarmClock alarmClock;

    protected void getAlarmClockModel() throws RuntimeException {
        int alarmClockId = getIntent().getIntExtra(AlarmClocksListFragment.ALARM_CLOCK_ID, -1);
        if (alarmClockId == -1) {
            throw new RuntimeException("Can't get alarm clock id from intent");
        }

        try {
            alarmClock = AlarmClock.getAlarmClockManager().getAlarmClock(alarmClockId);
        }
        catch(SQLException ex) {
            Log.e(this.getClass().getSimpleName(), "Error: " + ex.getMessage());
        }
    }

    protected void updateAlarmClockModel(Song song) {
        try {
            alarmClock.setSong(song);
            AlarmClock.getAlarmClockManager().update(alarmClock);
        }
        catch (SQLException ex) {
            Log.e(this.getClass().getSimpleName(), "Error: " + ex.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        getAlarmClockModel();

        List<Song> songs = Song.enumerateSongs(new File(Song.SONGS_DIRECTORY));
        setListAdapter(new SongListAdapter(this, songs));

        final SongListActivity context = this;
        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListAdapter songList = ((ListView)parent).getAdapter();
                Song song = (Song)songList.getItem(position);
                updateAlarmClockModel(song);

                Intent intent = context.getIntent();
                intent.setClass(context, AlarmClockDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SongPlayer.stop();
    }
}
