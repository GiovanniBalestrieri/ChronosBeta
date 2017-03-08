package box.chronos.userk.chronos.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import box.chronos.userk.chronos.R;
import box.chronos.userk.chronos.UI.LoginFragment;

/**
 * Created by userk on 08/03/17.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


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


}
