package cats.wants.meow.alarmclock.models.AlarmClock;

import android.os.Bundle;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;

import cats.wants.meow.alarmclock.db.DatabaseHelperFactory;
import cats.wants.meow.alarmclock.models.Song.Song;

@DatabaseTable(tableName = "alarm_clocks")
public class AlarmClock {

    private static AlarmClockManager alarmClockManager;

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private Date alarmTime;

    @DatabaseField(canBeNull = false)
    private String songFileName;

    public AlarmClock() {

    }

    public AlarmClock(String name, Date alarmTime, Song song) {
        this.name = name;
        this.alarmTime = alarmTime;
        this.songFileName = song.getPath();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAlarmTime() {
        return this.alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Song getSong() {
        return new Song(new File(this.songFileName));
    }

    public void setSong(Song song) {
        this.songFileName = song.getPath();
    }

    public static Bundle toBundle(AlarmClock alarmClock) {
        Bundle bundle = new Bundle();
        if (alarmClock.getId() != null) {
            bundle.putBoolean("is_alarm_clock_new", false);
            bundle.putInt("alarm_clock_id", alarmClock.getId());
            bundle.putString("alarm_clock_time", alarmClock.getAlarmTime().toString());
            bundle.putString("alarm_clock_name", alarmClock.getName());
            bundle.putString("alarm_clock_song", alarmClock.getSong().getPath());
        } else {
            bundle.putBoolean("is_alarm_clock_new", true);
        }
        return bundle;
    }

    public static AlarmClock fromBundle(Bundle bundle) {
        AlarmClock alarmClock = new AlarmClock();
        if (!bundle.getBoolean("is_alarm_clock_new")) {
            alarmClock.setId(bundle.getInt("alarm_clock_id"));
            alarmClock.setName(bundle.getString("alarm_clock_name"));
            alarmClock.setAlarmTime(new Date(Date.parse(bundle.getString("alarm_clock_time"))));
            alarmClock.setSong(new Song(bundle.getString("alarm_clock_song")));
        }
        return alarmClock;
    }

    public static AlarmClockManager getAlarmClockManager() throws SQLException {
        if (alarmClockManager == null) {
            alarmClockManager = new AlarmClockManager(DatabaseHelperFactory.getHelper().getConnectionSource(), AlarmClock.class);
        }
        return alarmClockManager;
    }
}
