package box.chronos.userk.chronos.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import box.chronos.userk.chronos.R;
import box.chronos.userk.chronos.activities.Code;
import box.chronos.userk.chronos.activities.LoginActivity;
import box.chronos.userk.chronos.activities.MainActivity;
import box.chronos.userk.chronos.callbacks.IAsyncResponse;
import box.chronos.userk.chronos.serverRequest.AppUrls;
import box.chronos.userk.chronos.serverRequest.RestInteraction;
import box.chronos.userk.chronos.utils.AppConstant;
import box.chronos.userk.chronos.utils.AppController;
import box.chronos.userk.chronos.utils.BlurBuilder;
import box.chronos.userk.chronos.utils.FieldsValidator;
import box.chronos.userk.chronos.utils.UserSharedPreference;
import box.chronos.userk.chronos.utils.Utility;

import static android.R.attr.id;
import static box.chronos.userk.chronos.utils.AppConstant.ADDRESS_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.BIRTHDAY_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.BUSINESSNAME_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.CODE_RESP;
import static box.chronos.userk.chronos.utils.AppConstant.DATA_RESP;
import static box.chronos.userk.chronos.utils.AppConstant.DEVICE_TYPE;
import static box.chronos.userk.chronos.utils.AppConstant.DEV_TOKEN_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.DEV_TYPE_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.EMAIL_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.GENDER_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.LAT_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.LOGIN_METHOD;
import static box.chronos.userk.chronos.utils.AppConstant.LON_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.MAX_OFF_DIST_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.MAX_OFF_VIEW_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.MESSAGE_KEY;
import static box.chronos.userk.chronos.utils.AppConstant.METHOD_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.ONE_RESP;
import static box.chronos.userk.chronos.utils.AppConstant.PASS_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.PHONE_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.PHOTO_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.REPEAT_OFF_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.SEL_CAT_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.SESSION_KEY_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.SIGNUP_METHOD;
import static box.chronos.userk.chronos.utils.AppConstant.SUCCESS_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.USERID_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.USERNAME_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.USER_TYPE_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.ZERO_RESP;
import static box.chronos.userk.chronos.utils.usertypes.TypeOfUsers.SHOP_USER;
import static box.chronos.userk.chronos.ux.AppMessage.CHECK_MAIL_MSG;
import static box.chronos.userk.chronos.ux.AppMessage.EMPTY_MAIL_MSG;
import static box.chronos.userk.chronos.ux.AppMessage.EMPTY_PASSWORD_MSG;

/**
 * Created by userk on 08/03/17.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private static String TAG = LoginFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;private String mParam2;
    private ImageView img_Google, img_Facebook;
    LoginButton loginButton;
    private EditText et_registredPassword, et_registredEmailId;
    private LinearLayout ll_login;
    private TextView tv_SignUp, tv_ForgotPassword;
    private UserSharedPreference sharePrefs;
    private FieldsValidator fv;
    CallbackManager callbackManager;
    private Activity activity;
    private View view;
    public LoginFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.d(TAG,"FACEBOOK OK ATTEMPT");
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.d(TAG,"FACEBOOK ATTEMPT CANCELLED");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.d(TAG,"FACEBOOK ATTEMPT ERROR");
                    }
                });
        try {;
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(
                    "com.example.packagename",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        //FacebookSdk.sdkInitialize(getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = getActivity();
        //content = activity.findViewById(android.R.id.content).getRootView();

        view = inflater.inflate(R.layout.login_fragment, container, false);

        setupLoginFragment();
        findViews(view);
        setupLoginBtnFB();
        attachListeners();

        customizeBackground();

        return view;
    }

    private void setupLoginFragment() {
        sharePrefs = AppController.getPreference();
        fv = new FieldsValidator(getContext());
    }

    private void attachListeners() {
        tv_ForgotPassword.setOnClickListener(this);
        tv_SignUp.setOnClickListener(this);
        img_Google.setOnClickListener(this);
        img_Facebook.setOnClickListener(this);
        ll_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_Login_v2:
                if (TextUtils.isEmpty(et_registredEmailId.getText().toString()) || TextUtils.isEmpty(et_registredPassword.getText().toString())) {
                    if (TextUtils.isEmpty(et_registredEmailId.getText().toString())) {
                        Toast.makeText(getActivity(), EMPTY_MAIL_MSG, Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(et_registredPassword.getText().toString())) {
                        Toast.makeText(getActivity(), EMPTY_PASSWORD_MSG, Toast.LENGTH_SHORT).show();
                    }
                } else if (fv.isEmailInvalid(et_registredEmailId,et_registredEmailId.getText().toString())) {
                    Toast.makeText(getActivity(), CHECK_MAIL_MSG, Toast.LENGTH_SHORT).show();
                } else {
                    requestForLogin();
                }
                break;

            case R.id.img_Google:
                ((LoginActivity) (getActivity())).onGooglePlus();
                break;

            case R.id.img_Facebook:
                //((LoginActivity) (getActivity())).onFblogin();
                    loginButton.performClick();

                // ((LoginActivity)(getActivity())).onFbloginMethod();
                // ((LoginActivity)(getActivity())).doFblogin();
                break;

            case R.id.tv_ForgotPassword:
                ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
                LoginActivity.self.replaceFragment(forgotPasswordFragment);
                break;

            case R.id.tv_SignUp:
                SignUpFragment signUpFragment = new SignUpFragment();
                LoginActivity.self.replaceFragment(signUpFragment);
                break;
        }
    }

    // request for login: STD
    private void requestForLogin() {
        /*
        Intent intent = new Intent(LoginActivity.self, Code.class);
        LoginActivity.self.startActivity(intent);
        LoginActivity.self.finish();
        */

        Map<String, String> pairs = new HashMap<>();
        pairs.put(METHOD_PARAM, LOGIN_METHOD);
        pairs.put(EMAIL_PARAM, et_registredEmailId.getText().toString().trim());
        pairs.put(PASS_PARAM, et_registredPassword.getText().toString());
        pairs.put(DEV_TYPE_PARAM, DEVICE_TYPE);
        pairs.put(DEV_TOKEN_PARAM, sharePrefs.getDeviceToken());
        pairs.put(LAT_PARAM, sharePrefs.getLatitude());
        pairs.put(LON_PARAM, sharePrefs.getLongitude());

        RestInteraction interaction = new RestInteraction(getActivity());
        interaction.setCallBack(new IAsyncResponse() {
            @Override
            public void onRestInteractionResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString(SUCCESS_PARAM).equalsIgnoreCase(ONE_RESP)) {
                        getJsonData(object);
                    } else {
                        Utility.showAlertDialog(getActivity(), object.getString(MESSAGE_KEY));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRestInteractionError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
        interaction.makeServiceRequest(AppUrls.COMMON_URL, pairs, TAG, "Dialog");

    }

    // get json data STD
    private void getJsonData(JSONObject object) {
        try {
            JSONObject jsonRootObject = new JSONObject(String.valueOf(object));
            JSONArray jsonArray = jsonRootObject.optJSONArray(DATA_RESP);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String codeResp = jsonRootObject.getString(CODE_RESP);


            String code_status = jsonRootObject.getString(CODE_RESP);
            sharePrefs.setCodeStatus(code_status);

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
            sharePrefs.setSelectedCatrgory(jsonObject.getString(SEL_CAT_PARAM));

            if (sharePrefs.getUserType().equals(SHOP_USER)) {
                // set personal phone to business phone
                sharePrefs.setBusinessphone(jsonObject.getString(PHONE_PARAM));
                sharePrefs.setBusinessaddress(jsonObject.getString(ADDRESS_PARAM));
                sharePrefs.setBusinessname(jsonObject.getString(BUSINESSNAME_PARAM));
            }

            sharePrefs.setIsFirstTimeUser(true);
            sharePrefs.setIsGroupActive(false);

            Intent intent;
            if (codeResp.equals(ZERO_RESP)) {
                intent = new Intent(LoginActivity.self, Code.class);
            } else {
                intent = new Intent(LoginActivity.self, MainActivity.class);
            }

            LoginActivity.self.startActivity(intent);
            LoginActivity.self.finish();

            getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Setup Background
    private void customizeBackground() {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test5c);
        Bitmap image = BlurBuilder.blur(getActivity().getApplicationContext(), bm);
        view.setBackground(new BitmapDrawable(activity.getResources(), image));
    }

    private void findViews(View view) {
        img_Google = (ImageView) view.findViewById(R.id.img_Google);
        img_Facebook = (ImageView) view.findViewById(R.id.img_Facebook);
        ll_login = (LinearLayout) view.findViewById(R.id.tv_Login_v2);
        et_registredEmailId = (EditText) view.findViewById(R.id.et_registredEmailId);
        et_registredPassword = (EditText) view.findViewById(R.id.et_registredPassword);

        tv_ForgotPassword = (TextView) view.findViewById(R.id.tv_ForgotPassword);
        tv_SignUp = (TextView) view.findViewById(R.id.tv_SignUp);

        loginButton = (LoginButton) view.findViewById(R.id.login_button);


    }

    private void setupLoginBtnFB() {

        List<String> permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile", "AccessToken");
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        //login_button.setReadPermissions(Arrays.asList("public_profile","email"));
        // If using in a fragment
        loginButton.setFragment(this);


        // Other app specific specialization
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        System.out.println("Facebook Success");
                        String accessToken = loginResult.getAccessToken().getToken();
                        Log.i("accessToken", accessToken);
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object,
                                                            GraphResponse response) {
                                        Log.i("LoginActivity",response.toString());

                                        try {
                                            String id = object.getString("id");
                                            if (object.has("picture")) {
                                                String picture = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                            }

                                            try {
                                                URL profile_pic = new URL(
                                                        "http://graph.facebook.com/" + id + "/picture?type=large");
                                                Log.i("profile_pic",
                                                        profile_pic + "");
                                            } catch (MalformedURLException e) {
                                                e.printStackTrace();
                                            }
                                            String name = object.getString("name");
                                            String email = object.getString("email");
                                            String gender = object.getString("gender");
                                            //String birthday = object.getString("birthday");

                                            sendRequestForFacebookLogin(name, email, gender, AppConstant.FACEBOOK);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields","id,name,email,gender,age_range,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut();
                        }
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("LoginActivity", exception.toString());
                        System.out.println("onError");
                        if (exception instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                Log.i("Errore Facebook", "4");
                                LoginManager.getInstance().logOut();
                            }
                        }
                    }
                });

    }
    // send request for facebook
    private void sendRequestForFacebookLogin(String userName, String email, String gender, String type) {
        Map<String, String> pairs = new HashMap<>();
        pairs.put("method", SIGNUP_METHOD);
        pairs.put("username", userName);
        pairs.put("email", email);
        pairs.put("gender", gender);
        pairs.put("birthday", "0000-00-00");
        // TODO change usertype
        pairs.put("usertype", "4");
        pairs.put("devicetype", DEVICE_TYPE);
        pairs.put("devicetoken", sharePrefs.getDeviceToken());
        pairs.put("latitude", sharePrefs.getLatitude());
        pairs.put("longitude", sharePrefs.getLongitude());
        pairs.put("option", type);

        RestInteraction interaction = new RestInteraction(getActivity());
        interaction.setCallBack(new IAsyncResponse() {
            @Override
            public void onRestInteractionResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equalsIgnoreCase("1")) {
                        //Utility.showAlertDialog(getActivity(), object.getString("message"));
                        getJsonDataFromFacebook(object);
                    } else {
                        Utility.showAlertDialog(getActivity(), object.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRestInteractionError(String message) {
                Utility.showAlertDialog(getActivity(), message);
            }
        });
        interaction.makeServiceRequest(AppUrls.COMMON_URL, pairs, TAG, "Dialog");
    }

    // get data from facebook login
    private void getJsonDataFromFacebook(JSONObject object) {
        try {
            JSONObject jsonRootObject = new JSONObject(String.valueOf(object));
            JSONArray jsonArray = jsonRootObject.optJSONArray("data");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String codeResp = jsonRootObject.getString(CODE_RESP);


            String code_status = jsonRootObject.getString(CODE_RESP);
            sharePrefs.setCodeStatus(code_status);
            sharePrefs.setUserId(jsonObject.getString("userid").toString());
            sharePrefs.setUserName(jsonObject.getString("username").toString());
            sharePrefs.setUserEmail(jsonObject.getString("email").toString());
            sharePrefs.setSessionKey(jsonObject.getString("sessionkey").toString());
            sharePrefs.setUserImage(jsonObject.getString("photo").toString());
            sharePrefs.setGender(jsonObject.getString("gender").toString());
            sharePrefs.setMaxOfferDistance(jsonObject.getString("maxofferdistance").toString());
            sharePrefs.setMaxOfferView(jsonObject.getString("maxofferview").toString());
            sharePrefs.setRepeatOffer(jsonObject.getString("repeatoffer").toString());
            sharePrefs.setUserType(jsonObject.getString("usertype").toString());
            sharePrefs.setBirthday(jsonObject.getString("birthday").toString());
            sharePrefs.setSelectedCatrgory(jsonObject.getString("selctedcategory").toString());
            sharePrefs.setDefaultAddress(jsonObject.getString("address").toString());
            sharePrefs.setUserPhoneNumber(jsonObject.getString("phonenumber").toString());

            if (sharePrefs.getUserType().equals(SHOP_USER)) {
                // set personal phone to business phone
                sharePrefs.setBusinessphone(jsonObject.getString(PHONE_PARAM));
                sharePrefs.setBusinessaddress(jsonObject.getString(ADDRESS_PARAM));
                sharePrefs.setBusinessname(jsonObject.getString(BUSINESSNAME_PARAM));
            }
            sharePrefs.setIsGroupActive(false);

            sharePrefs.setIsFirstTimeUser(true);

            Intent intent;
            if (codeResp.equals(ZERO_RESP)) {
                intent = new Intent(LoginActivity.self, Code.class);
            } else {
                intent = new Intent(LoginActivity.self, MainActivity.class);
            }

            LoginActivity.self.startActivity(intent);
            LoginActivity.self.finish();

            getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            Log.d(TAG,e.toString());
        }
    }
}
