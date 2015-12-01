package cats.wants.meow.alarmclock.models.AlarmClock;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import cats.wants.meow.alarmclock.models.Song.Song;

public class AlarmClockFactory {

    public static AlarmClock createAlarmClock() throws RuntimeException{
        return createAlarmClock("");
    }

    public static AlarmClock createAlarmClock(String name) throws RuntimeException{
        return createAlarmClock(name, new Date());
    }

    public static AlarmClock createAlarmClock(String name, Date date) throws RuntimeException{
        List<Song> songs = Song.enumerateSongs(new File(Song.SONGS_DIRECTORY));
        return createAlarmClock(name, date, songs.get(0));
    }

    public static AlarmClock createAlarmClock(String name, Date date, Song song) throws RuntimeException {
        try {
            AlarmClock alarmClock = new AlarmClock(name, date, song);
            AlarmClock.getAlarmClockManager().create(alarmClock);
            return alarmClock;
        }
        catch (SQLException ex) {
            throw new RuntimeException("Can't create new alarm clock");
        }
    }
}
