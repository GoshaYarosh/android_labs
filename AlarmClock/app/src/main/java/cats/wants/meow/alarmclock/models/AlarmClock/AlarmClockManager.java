package cats.wants.meow.alarmclock.models.AlarmClock;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class AlarmClockManager extends BaseDaoImpl<AlarmClock, Integer> {

    public AlarmClockManager(ConnectionSource connectionSource,
                             Class<AlarmClock> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public AlarmClock getAlarmClock(int id) throws SQLException {
        return this.queryForId(id);
    }

    public List<AlarmClock> getAllAlarmClocks() throws SQLException {
        return this.queryForAll();
    }
}
