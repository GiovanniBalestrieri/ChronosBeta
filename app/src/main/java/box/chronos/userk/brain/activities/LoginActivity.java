package box.chronos.userk.brain.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import box.chronos.userk.brain.R;
import box.chronos.userk.brain.callbacks.IAsyncResponse;
import box.chronos.userk.brain.fragments.LoginFragment;
import box.chronos.userk.brain.serverRequest.AppUrls;
import box.chronos.userk.brain.serverRequest.RestInteraction;
import box.chronos.userk.brain.utils.AppConstant;
import box.chronos.userk.brain.utils.AppController;
import box.chronos.userk.brain.utils.UserSharedPreference;
import box.chronos.userk.brain.utils.Utility;

import static box.chronos.userk.brain.utils.AppConstant.ADDRESS_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.BIRTHDAY_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.BUSINESSNAME_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.CODE_RESP;
import static box.chronos.userk.brain.utils.AppConstant.DATA_RESP;
import static box.chronos.userk.brain.utils.AppConstant.DEV_TOKEN_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.DEV_TYPE_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.EMAIL_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.GENDER_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.LAT_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.LON_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.MAX_OFF_DIST_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.MAX_OFF_VIEW_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.MESSAGE_KEY;
import static box.chronos.userk.brain.utils.AppConstant.METHOD_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.ONE_RESP;
import static box.chronos.userk.brain.utils.AppConstant.OPTION_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.PASS_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.PHONE_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.PHOTO_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.REPEAT_OFF_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.SEL_CAT_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.SESSION_KEY_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.SIGNUP_METHOD;
import static box.chronos.userk.brain.utils.AppConstant.SUCCESS_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.USERID_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.USERNAME_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.USER_TYPE_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.ZERO_RESP;
import static box.chronos.userk.brain.utils.usertypes.TypeOfUsers.SHOP_USER;

/**
 * Created by userk on 08/03/17.
 */

public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private FragmentManager fragmentManager;
    private UserSharedPreference sharePrefs;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private String email, facebook_id, picture, userName, gender, age;
    //private CallbackManager callbackmanager;
    private FirebaseAnalytics mFirebaseAnalytics;
    private boolean doubleBackToExitPressedOnce = false;

    public static LoginActivity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        setupLoginActivity();




        Fragment fragment = new LoginFragment();
        //tv_TopHeading.setText(getResources().getString(R.string.login));
        //back.setVisibility(View.GONE);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_body, fragment);
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    void setupLoginActivity() {
        self = LoginActivity.this;
        sharePrefs = AppController.getPreference();
        initGoogleSignUp();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);



        // Facebook Key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "box.chronos.userk.brain",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
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

    /**
     * Google Sign In
     */
    private void initGoogleSignUp() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            // Make the call to GoogleApiClient
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this  /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
    }

    /**
     * When G+ button is pressed
     */
    public void onGooglePlus() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Utility.showAlertDialog(LoginActivity.this, "Sola Google");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //callbackmanager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            if (data != null) {
                //callbackmanager.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            /*  NO deprecated
            Person mePerson = plus.people().get("me").execute();

            System.out.println("ID:\t" + mePerson.getId());
            System.out.println("Display Name:\t" + mePerson.getDisplayName());
            System.out.println("Image URL:\t" + mePerson.getImage().getUrl());
            System.out.println("Profile URL:\t" + mePerson.getUrl());
            */

            userName = acct.getDisplayName();
            email = acct.getEmail();
            //gender = acct.getGender();
            //Toast.makeText(this,"Info: user " + userName + "Display: "+acct.getDisplayName(),Toast.LENGTH_SHORT);
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);
            loginRequest(userName,email,"",AppConstant.DEVICE_TYPE);
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(this.getApplicationContext()," ERROR ",Toast.LENGTH_SHORT);
            //updateUI(false);
        }
    }

    // send request for facebook
    private void loginRequest(String userName, String email, String gender, String type) {
        Map<String, String> pairs = new HashMap<>();
        pairs.put(METHOD_PARAM, SIGNUP_METHOD);
        pairs.put(USERNAME_PARAM, userName);
        pairs.put(EMAIL_PARAM, email);
        pairs.put(GENDER_PARAM, gender);
        pairs.put(BIRTHDAY_PARAM, "0000-00-00");
        pairs.put(DEV_TYPE_PARAM, AppConstant.DEVICE_TYPE);
        pairs.put(DEV_TOKEN_PARAM, sharePrefs.getDeviceToken());
        pairs.put(LAT_PARAM, sharePrefs.getLatitude());
        pairs.put(LON_PARAM, sharePrefs.getLongitude());
        pairs.put(USER_TYPE_PARAM, "6");
        pairs.put(OPTION_PARAM, type);
        pairs.put(PASS_PARAM, "");

        RestInteraction intraction = new RestInteraction(this);
        intraction.setCallBack(new IAsyncResponse() {
            @Override
            public void onRestInteractionResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString(SUCCESS_PARAM).equalsIgnoreCase(ONE_RESP)) {
                        getJsonData(object);
                    } else {
                        Utility.showAlertDialog(LoginActivity.this, object.getString(MESSAGE_KEY));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRestInteractionError(String message) {
                Utility.showAlertDialog(LoginActivity.this, message);
            }
        });
        intraction.makeServiceRequest(AppUrls.COMMON_URL, pairs, TAG, "Dialog");


        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Test di login");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    /**
     * Saves Login information into shared Pref
     */
    public void getJsonData(JSONObject object) {
        try {
            JSONObject jsonRootObject = new JSONObject(String.valueOf(object));
            JSONArray jsonArray = jsonRootObject.optJSONArray(DATA_RESP);
            String code_status = jsonRootObject.getString(CODE_RESP);
            sharePrefs.setCodeStatus(code_status);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String codeResp = jsonRootObject.getString(CODE_RESP);

            sharePrefs.setUserId(jsonObject.getString(USERID_PARAM));
            sharePrefs.setUserName(jsonObject.getString(USERNAME_PARAM));
            sharePrefs.setUserEmail(jsonObject.getString(EMAIL_PARAM));
            sharePrefs.setSessionKey(jsonObject.getString(SESSION_KEY_PARAM));
            sharePrefs.setUserImage(jsonObject.getString(PHOTO_PARAM));
            sharePrefs.setGender(jsonObject.getString(GENDER_PARAM));
            sharePrefs.setMaxOfferDistance(jsonObject.getString(MAX_OFF_DIST_PARAM));
            sharePrefs.setMaxOfferView(jsonObject.getString(MAX_OFF_VIEW_PARAM));
            sharePrefs.setRepeatOffer(jsonObject.getString(REPEAT_OFF_PARAM));
            sharePrefs.setUserType(jsonObject.getString(USER_TYPE_PARAM));
            sharePrefs.setBirthday(jsonObject.getString(BIRTHDAY_PARAM));
            sharePrefs.setSelectedCatrgory(jsonObject.getString(SEL_CAT_PARAM));
            sharePrefs.setDefaultAddress(jsonObject.getString(ADDRESS_PARAM));
            sharePrefs.setUserPhoneNumber(jsonObject.getString(PHONE_PARAM));

            if (sharePrefs.getUserType().equals(SHOP_USER)) {
                // set personal phone to business phone
                sharePrefs.setBusinessphone(jsonObject.getString(PHONE_PARAM));
                sharePrefs.setBusinessaddress(jsonObject.getString(ADDRESS_PARAM));
                sharePrefs.setBusinessname(jsonObject.getString(BUSINESSNAME_PARAM));
            }

            sharePrefs.setIsFirstTimeUser(true);

            Intent intent;
            if (codeResp.equals(ZERO_RESP)) {
                intent = new Intent(LoginActivity.self, IntroActivity.class);
            } else {
                intent = new Intent(LoginActivity.self, MainActivity.class);
            }

            LoginActivity.self.startActivity(intent);
            LoginActivity.self.finish();

            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        int count = fragmentManager.getBackStackEntryCount();
        /*if(count == 2){
            back.setVisibility(View.GONE);
        }*/
        if (count == 1) {
            this.doubleBackToExitPressedOnce = true;
            if (doubleBackToExitPressedOnce) {
                finish();
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                return;
            }
        } else {
            super.onBackPressed();
        }
    }

    private void hitGooglePlus() {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            //showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
              //      hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
}
