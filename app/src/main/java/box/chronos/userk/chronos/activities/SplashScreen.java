package box.chronos.userk.chronos.activities;
/**
 * Created by userk on 9/23/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import box.chronos.userk.chronos.R;
import box.chronos.userk.chronos.utils.AppController;
import box.chronos.userk.chronos.utils.UserSharedPreference;

/**
 * Created by userK on 9/16/16.
 */
public class SplashScreen extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private UserSharedPreference sharePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        sharePrefs = AppController.getPreference();

        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                Intent i;
                boolean a = sharePrefs.getIsFirstTimeUser();
                if (a) {
                    i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                } else {
                    i = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                }

            }
        }, SPLASH_TIME_OUT);
    }

}
