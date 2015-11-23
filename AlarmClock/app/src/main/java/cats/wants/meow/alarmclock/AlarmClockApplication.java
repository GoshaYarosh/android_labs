package cats.wants.meow.alarmclock;

import android.app.Application;

import cats.wants.meow.alarmclock.db.DatabaseHelperFactory;

public class AlarmClockApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseHelperFactory.createDatabaseHelper(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        DatabaseHelperFactory.realeaseHelper();
        super.onTerminate();
    }
}
