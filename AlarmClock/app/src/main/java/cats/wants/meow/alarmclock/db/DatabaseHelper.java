package cats.wants.meow.alarmclock.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.Date;

import cats.wants.meow.alarmclock.models.AlarmClock;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "alarm_clock_app.db";
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private AlarmClockDAO alarmClockDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, AlarmClock.class);
            this.alarmClockDao.create(new AlarmClock("First alarm clock", new Date()));
            this.alarmClockDao.create(new AlarmClock("Second alarm clock", new Date()));
            this.alarmClockDao.create(new AlarmClock("Third alarm clock", new Date()));
        }
        catch (SQLException e) {
            Log.e(TAG, "Error creating db: " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, AlarmClock.class, true);
            onCreate(db, connectionSource);
        }
        catch (SQLException e) {
            Log.e(TAG, "Error upgrading db: " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    public AlarmClockDAO getAlarmClockDAO() throws SQLException {
        if (alarmClockDao == null) {
            alarmClockDao = new AlarmClockDAO(getConnectionSource(), AlarmClock.class);
        }
        return alarmClockDao;
    }

    @Override
    public void close() {
        super.close();
        alarmClockDao = null;
    }
}
