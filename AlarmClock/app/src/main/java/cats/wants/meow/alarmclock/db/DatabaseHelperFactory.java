package cats.wants.meow.alarmclock.db;

import android.content.Context;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class DatabaseHelperFactory {

    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper getHelper() {
        return databaseHelper;
    }

    public static void createDatabaseHelper(Context context) {
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public static void realeaseHelper() {
        OpenHelperManager.releaseHelper();
        databaseHelper = null;
    }
}

