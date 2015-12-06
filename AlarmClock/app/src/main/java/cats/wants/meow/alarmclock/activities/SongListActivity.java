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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        List<Song> songs = Song.enumerateSongs(new File(Song.SONGS_DIRECTORY));
        setListAdapter(new SongListAdapter(this, songs));

        final SongListActivity context = this;
        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListAdapter songList = ((ListView) parent).getAdapter();
                Song song = (Song) songList.getItem(position);

                Intent intent = new Intent();
                intent.putExtra(SONG_NAME, song.getPath());
                context.setResult(RESULT_OK, intent);
                context.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SongPlayer.stop();
    }
}
