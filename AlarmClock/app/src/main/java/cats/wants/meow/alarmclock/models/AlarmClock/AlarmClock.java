package cats.wants.meow.alarmclock.models.AlarmClock;

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

    public int getId() {
        return this.id;
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

    public static AlarmClockManager getAlarmClockManager() throws SQLException {
        if (alarmClockManager == null) {
            alarmClockManager = new AlarmClockManager(DatabaseHelperFactory.getHelper().getConnectionSource(), AlarmClock.class);
        }
        return alarmClockManager;
    }
}
