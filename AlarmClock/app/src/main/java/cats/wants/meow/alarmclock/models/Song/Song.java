package cats.wants.meow.alarmclock.models.Song;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Song {

    private final File songFile;

    public Song(String songFilePath) {
        this.songFile = new File(songFilePath);
    }

    public Song(File songFile) {
        this.songFile = songFile;
    }

    public String getName() {
        return this.songFile.getName().replaceAll(".mp3", "");
    }

    public String getPath() {
        return this.songFile.getPath();
    }

    public final static String SONGS_DIRECTORY = "" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);

    public static List<Song> enumerateSongs(File rootDirectory) {
        List<Song> songs = new ArrayList<>();

        File[] files = rootDirectory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    songs.addAll(enumerateSongs(file));
                }

                String fileName = file.getName();
                if (fileName.endsWith(".mp3")) {
                    songs.add(new Song(file));
                }
            }
        }
        return songs;
    }
}
