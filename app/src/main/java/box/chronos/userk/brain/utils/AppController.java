package box.chronos.userk.brain.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by userk on 14/12/16.
 */

public class AppController extends MultiDexApplication {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue, mImageRequestQueue;
    private static AppController mInstance;
    private static UserSharedPreference preference;
    private Thread threadDataBaseHandler;
    public static Handler handlerWorkerThread;
    public static final int USER_MODE = 1;
    public static final int DEALER_MODE = 2;
    public static int currentMode;
    private FieldsValidator validator;
    public static int currentLanguage;
    public static final int ENGLISH = 1;
    public static final int FRENCH = 2;
    public static final int ITALIAN = 3;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        preference = new UserSharedPreference(AppController.this);
        currentMode = USER_MODE;
        threadDataBaseHandler=new Thread(runnable);
        threadDataBaseHandler.start();
        currentLanguage = ITALIAN;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    public synchronized FieldsValidator getValidator() {
        if(validator==null)
            validator=new FieldsValidator(mInstance);
        return validator;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        handlerWorkerThread.getLooper().quit();
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static synchronized UserSharedPreference getPreference() {

        return preference;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public AppController() {
        super();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Looper.prepare();
            handlerWorkerThread = new Handler();
            Looper.loop();
        }
    };
}
