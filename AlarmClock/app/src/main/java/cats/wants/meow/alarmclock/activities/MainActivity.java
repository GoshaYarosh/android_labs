package cats.wants.meow.alarmclock.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cats.wants.meow.alarmclock.R;
import cats.wants.meow.alarmclock.fragments.AboutFragment;
import cats.wants.meow.alarmclock.fragments.AlarmClocksListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.alarm_clocks_list_fragment_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.framgent_container, new AlarmClocksListFragment())
                                .commit();
                    }
                });

        this.findViewById(R.id.about_fragment_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.framgent_container, new AboutFragment())
                                .commit();
                    }
                });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framgent_container, new AlarmClocksListFragment())
                .commit();
    }
}
