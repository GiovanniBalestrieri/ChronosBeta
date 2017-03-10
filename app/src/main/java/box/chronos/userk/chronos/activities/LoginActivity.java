package box.chronos.userk.chronos.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import box.chronos.userk.chronos.R;
import box.chronos.userk.chronos.fragments.LoginFragment;
import box.chronos.userk.chronos.utils.AppController;
import box.chronos.userk.chronos.utils.UserSharedPreference;

/**
 * Created by userk on 08/03/17.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private FragmentManager fragmentManager;
    private UserSharedPreference sharePrefs;

    public static LoginActivity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        self = LoginActivity.this;
        sharePrefs = AppController.getPreference();


        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
*/


        Fragment fragment;
        fragment = new LoginFragment();
        //tv_TopHeading.setText(getResources().getString(R.string.login));
        //back.setVisibility(View.GONE);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }


    public void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;
        boolean fragmentPopped;
        fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped && fragmentManager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.enter_from_left, R.anim.exit_to_right);
            ft.replace(R.id.container_body, fragment, fragmentTag);
            ft.addToBackStack(backStateName);
            ft.commitAllowingStateLoss();
        }
    }


}
