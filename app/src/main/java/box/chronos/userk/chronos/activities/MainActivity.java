package box.chronos.userk.chronos.activities;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import box.chronos.userk.chronos.R;
import box.chronos.userk.chronos.callbacks.IAsyncResponse;
import box.chronos.userk.chronos.fragments.CategoriesGridFragment;
import box.chronos.userk.chronos.fragments.OffersListFragment;
import box.chronos.userk.chronos.fragments.ProfileFragment;
import box.chronos.userk.chronos.serverRequest.AppUrls;
import box.chronos.userk.chronos.serverRequest.RestInteraction;
import box.chronos.userk.chronos.utils.AppController;
import box.chronos.userk.chronos.utils.GpsTracker;
import box.chronos.userk.chronos.utils.UserSharedPreference;
import box.chronos.userk.chronos.utils.Utility;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static box.chronos.userk.chronos.serverRequest.AppUrls.IMAGE_URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String TAG = MainActivity.class.getSimpleName();
    AppBarLayout appBarLayout;
    private GpsTracker gps;
    public static Stack<Fragment> stackFragment;
    public AlertDialog myAlertDialog;
    CoordinatorLayout coordinatorLayout;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private Toolbar mToolbar;
    private InputMethodManager imm;
    public DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    private boolean doubleBackToExitPressedOnce = false;
    private UserSharedPreference sharePrefs;
    public static MainActivity self;
    private String locationValue, latitudeValue, longitudeValue;
    public TextView nick_u, email_u;
    LinearLayout profileNav;
    public ImageView profPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //initCollapsingToolbar();
        setupEnvironment();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.BLACK);
        }
        requestForGps();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        OffersListFragment fragment = new OffersListFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment,"Offers");

        fragmentTransaction.commit();

        profileNav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Log.d("NAVIGATION","\t\tPROFILE");

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                ProfileFragment profileFragment = new ProfileFragment();
                fragmentTransaction.add(R.id.fragment_container,profileFragment,"profile");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        // Dismiss Dialog
        ///Utility.showAlertDialog();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        toggle.onConfigurationChanged(newConfig);
    }

    /**
     * Setup environment
     */
    public void setupEnvironment() {
        //stackFragment = new Stack<Fragment>();
        self = this;
        imm = (InputMethodManager) self.getSystemService(Context.INPUT_METHOD_SERVICE);
        sharePrefs = AppController.getPreference();

        if (Build.VERSION.SDK_INT >= 23) {
            isReadContactPermissionGranted();
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        View vv = navigationView.getHeaderView(0);

        profileNav = (LinearLayout) vv.findViewById(R.id.ll_nav_profile);
        nick_u = (TextView) vv.findViewById(R.id.tv_UserName);
        email_u = (TextView) vv.findViewById(R.id.tv_UserEmail);

        updateProfilePictureNav();
    }

    public void updateProfilePictureNav(){
        email_u.setText(sharePrefs.getUserEmail());
        nick_u.setText(sharePrefs.getUserName());
        String urlImage = IMAGE_URL + sharePrefs.getUserImage();
        //Glide.with(this).load(urlImage).into(profPic);
        drawer.invalidate();
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

    }

    public void lockHeader(boolean val){
        appBarLayout.setExpanded(val);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        if (id==android.R.id.home){
            if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                drawer.closeDrawer(Gravity.RIGHT);
            } else {
                drawer.openDrawer(Gravity.RIGHT);
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.categories) {
            Toast.makeText(this,"Categories",Toast.LENGTH_SHORT);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            CategoriesGridFragment fragment = new CategoriesGridFragment();
            fragmentTransaction.replace(R.id.fragment_container, fragment,"Categories");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();



        } /*else if (id == R.id.nav_slideshow) {

        } */else if (id == R.id.nav_offers) {
            Toast.makeText(this,"Intorno a te",Toast.LENGTH_SHORT);

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


            OffersListFragment fragment = new OffersListFragment();
            fragmentTransaction.replace(R.id.fragment_container, fragment,"Categories");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
        /*else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }*/ else if (id == R.id.nav_logout) {

            requestForLogout();

            LoginManager.getInstance().logOut();
            //AppController.currentMode = 1;
            //Intent intent = new Intent(MainActivity.self, LoginActivity.class);
            //startActivity(intent);
            //finish();
            //sharePrefs.clearPrefrence();
            //MainActivity.self.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        } /*else if (id == R.id.nav_switch) {
            Toast.makeText(MainActivity.self,"SWITCH",Toast.LENGTH_SHORT);
            Log.d("NAVIGATION","Switch Selected");
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // request for logout
    private void requestForLogout() {
        Map<String, String> pairs = new HashMap<>();
        pairs.put("method", "userLogOut");
        pairs.put("userid", sharePrefs.getUserId());
        pairs.put("sessionkey", sharePrefs.getSessionKey());

        RestInteraction interaction = new RestInteraction(MainActivity.self);
        interaction.setCallBack(new IAsyncResponse() {
            @Override
            public void onRestInteractionResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equalsIgnoreCase("1")) {

                        // Facebook sync
                        //LoginManager.getInstance().logOut();
                        //Utility.showAlertDialog(self, object.getString("message"));

                    } else {
                        //Utility.showAlertDialog(self, object.getString("message"));
                    }

                    AppController.currentMode = 1;
                    Intent intent = new Intent(MainActivity.self, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    sharePrefs.clearPrefrence();
                    MainActivity.self.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onRestInteractionError(String message) {
                Utility.showAlertDialog(MainActivity.self, message);
            }
        });
        interaction.makeServiceRequest(AppUrls.COMMON_URL, pairs, TAG, "no");
    }

    public void isReadContactPermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionsNeeded = new ArrayList<String>();
            List<String> permissionsList = new ArrayList<String>();

            if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION)) {
                permissionsNeeded.add("GPS");
            }

            if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissionsNeeded.add("Read Storage");
            }

            if (!addPermission(permissionsList, Manifest.permission.CAMERA)) {
                permissionsNeeded.add("Camera");
            }

            if (permissionsList.size() > 0) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                return;
            }
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= 23)

            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);

                // Check for Rationale Option
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        return true;
    }

    // request for gps
    public void requestForGps() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        if (gps == null)
            gps = new GpsTracker(this);

        if (gps.canGetLocation()) {

            gps.getLocation();
            locationValue = gps.getAddress();
            latitudeValue = String.valueOf(gps.getLatitude());
            longitudeValue = String.valueOf(gps.getLongitude());

            sharePrefs.setLatitude(gps.getLatitude());
            sharePrefs.setLongitude(gps.getLongitude());
        } else {
            gps.showSettingsAlert();
            return;
        }
    }



}
