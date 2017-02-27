package box.chronos.userk.chronos.Activities;
/**
 * Created by userk on 9/23/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import box.chronos.userk.chronos.Activities.MainActivity;
import box.chronos.userk.chronos.R;
import box.chronos.userk.chronos.UI.Code;
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
                i = new Intent(SplashScreen.this, Code.class);
                startActivity(i);
                finish();
                //overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                /*
                if(sharePrefs.getIsFirstTimeUser()) {
                    i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                }else{
                    i = new Intent(SplashScreen.this, Code.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                }
                */
            }
        }, SPLASH_TIME_OUT);
    }

}
