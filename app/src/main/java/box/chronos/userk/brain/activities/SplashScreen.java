package box.chronos.userk.brain.activities;
/**
 * Created by userk on 9/23/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import box.chronos.userk.brain.R;
import box.chronos.userk.brain.utils.application.AppController;
import box.chronos.userk.brain.utils.application.UserSharedPreference;

import static box.chronos.userk.brain.utils.constants.AppConstant.ONE_RESP;
import static box.chronos.userk.brain.ux.AppMessage.ANONYMOUS_MESSAGE;

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
        fullScreen();
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
                boolean b = sharePrefs.getIsAnonymous();
                if (a && sharePrefs.getCode_status().equals(ONE_RESP) && !b) {
                    i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                } /* else if (sharePrefs.getCode_status().equals("0")) {
                    i = new Intent(SplashScreen.this, Code.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                } */ else {
                    i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(SplashScreen.this, ANONYMOUS_MESSAGE ,Toast.LENGTH_LONG);
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                }

            }
        }, SPLASH_TIME_OUT);
    }


    public void fullScreen() {

        // BEGIN_INCLUDE (get_current_ui_flags)
        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("OFFER PAGE", "Turning immersive mode mode off. ");
        } else {
            Log.i("OFFERPAGE", "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
    }

}
