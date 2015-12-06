package cats.wants.meow.alarmclock.models.Song;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class SongPlayer {

    private static MediaPlayer mediaPlayer;

    public static void play(Context context, Song song) {
        SongPlayer.stop();
        mediaPlayer = MediaPlayer.create(context, Uri.parse(song.getPath()));
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                SongPlayer.stop();
            }
        });
    }

    public static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
