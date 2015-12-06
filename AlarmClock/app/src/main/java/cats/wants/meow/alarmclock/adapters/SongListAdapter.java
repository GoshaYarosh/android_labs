package cats.wants.meow.alarmclock.adapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import cats.wants.meow.alarmclock.R;
import cats.wants.meow.alarmclock.models.Song.Song;
import cats.wants.meow.alarmclock.models.Song.SongPlayer;

public class SongListAdapter extends ArrayAdapter<Song> {

    static class ViewHolder {
        public final TextView nameTextView;
        public final Button startButton;
        public final Button stopButton;

        public ViewHolder(View view) {
            this.nameTextView = (TextView)view.findViewById(R.id.song_file_name);
            this.startButton = (Button)view.findViewById(R.id.play_song_button);
            this.stopButton = (Button)view.findViewById(R.id.stop_song_button);
        }
    }

    public SongListAdapter(Context context, List<Song> objects) {
        super(context, R.layout.listitem_song, objects);
    }

    @Override
    public View getView(int position, View songView, ViewGroup parent) {
        if (songView == null) {
            Activity activity = (Activity)this.getContext();
            LayoutInflater inflatter = activity.getLayoutInflater();
            songView = inflatter.inflate(R.layout.listitem_song, null, false);
            songView.setTag(new ViewHolder(songView));
        }

        final Song song = this.getItem(position);
        final Context context = this.getContext();
        ViewHolder viewHolder = (ViewHolder)songView.getTag();
        viewHolder.nameTextView.setText(song.getName());

        viewHolder.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongPlayer.play(context, song);
            }
        });

        viewHolder.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongPlayer.stop();
            }
        });

        return songView;
    }
}
