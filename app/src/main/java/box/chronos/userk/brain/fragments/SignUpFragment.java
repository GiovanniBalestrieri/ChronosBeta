package box.chronos.userk.brain.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import box.chronos.userk.brain.R;
import box.chronos.userk.brain.activities.IntroActivity;
import box.chronos.userk.brain.activities.LoginActivity;
import box.chronos.userk.brain.activities.MainActivity;
import box.chronos.userk.brain.callbacks.IAsyncResponse;
import box.chronos.userk.brain.serverRequest.AppUrls;
import box.chronos.userk.brain.serverRequest.RestInteraction;
import box.chronos.userk.brain.utils.constants.AppConstant;
import box.chronos.userk.brain.utils.application.AppController;
import box.chronos.userk.brain.utils.images.BlurBuilder;
import box.chronos.userk.brain.utils.forms.FieldsValidator;
import box.chronos.userk.brain.utils.application.UserSharedPreference;
import box.chronos.userk.brain.utils.Utility;

import static box.chronos.userk.brain.utils.constants.AppConstant.ADDRESS_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.BIRTHDAY_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.CODE_RESP;
import static box.chronos.userk.brain.utils.constants.AppConstant.DEV_TOKEN_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.DEV_TYPE_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.EMAIL_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.GENDER_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.LAT_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.LON_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.MAX_OFF_DIST_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.MAX_OFF_VIEW_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.METHOD_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.PASS_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.PHOTO_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.REPEAT_OFF_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.SEL_CAT_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.SESSION_KEY_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.SIGNUP_METHOD;
import static box.chronos.userk.brain.utils.constants.AppConstant.USERID_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.USERNAME_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.USER_TYPE_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.ZERO_RESP;
import static box.chronos.userk.brain.ux.AppMessage.CHECK_MAIL_MSG;
import static box.chronos.userk.brain.ux.AppMessage.EMPTY_FIELD_MSG;
import static box.chronos.userk.brain.ux.AppMessage.EMPTY_PASSWORD_MSG;
import static box.chronos.userk.brain.ux.AppMessage.EMPTY_SEX_MSG;
import static box.chronos.userk.brain.ux.AppMessage.EMPTY_USER_MSG;

/**
 * Created by userk on 08/03/17.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {

    private static String TAG = SignUpFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1, mParam2, gender;
    private Button btn_login;
    private ImageView img_Google, img_Facebook;
    private RadioGroup gender_radioGroup;
    private RadioButton rb;
    private RadioButton male_rb, female_rb;
    private FieldsValidator validator;
    private EditText etPassword, etName, etEmail;
    private LinearLayout ll_register;
    private TextView tv_SignUp, tv_ForgotPassword;
    private UserSharedPreference sharePrefs;
    private FieldsValidator fv;
    private CheckBox mCbShowPwd;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final Activity activity = getActivity();
        final View content = activity.findViewById(android.R.id.content).getRootView();

        View view = inflater.inflate(R.layout.register_fragment_2, container, false);

        setupSignupFragment();
        findViews(view);
        attachListeners();


        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test5c);
        Bitmap image = BlurBuilder.blur(getActivity().getApplicationContext(),bm);
        view.setBackground(new BitmapDrawable(activity.getResources(), image));


        // add onCheckedListener on checkbox
        // when user clicks on this checkbox, this is the handler.
        mCbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // checkbox status is changed from uncheck to checked.
                if (!isChecked) {
                    // show password
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        gender_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    gender = rb.getText().toString().trim();
                }
            }
        });
        return view;
    }



    private void setupSignupFragment() {
        sharePrefs = AppController.getPreference();
        fv = new FieldsValidator(getContext());
    }


    private void attachListeners() {
        //img_Google.setOnClickListener(this);
        //img_Facebook.setOnClickListener(this);
        ll_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                if (TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString()) || TextUtils.isEmpty(etName.getText().toString())) {
                    if (TextUtils.isEmpty(etEmail.getText().toString())) {
                        Toast.makeText(getActivity(), CHECK_MAIL_MSG, Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                        Toast.makeText(getActivity(), EMPTY_PASSWORD_MSG, Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(etName.getText().toString()))
                        Toast.makeText(getActivity(), EMPTY_USER_MSG, Toast.LENGTH_SHORT).show();
                } else if (fv.isEmailInvalid(etEmail,etEmail.getText().toString())) {
                    Toast.makeText(getActivity(), CHECK_MAIL_MSG, Toast.LENGTH_SHORT).show();
                } else if (fv.validateUsernameSpace(etName, EMPTY_USER_MSG))
                    return;
                else if (fv.validateUsernameSpace(etPassword, EMPTY_FIELD_MSG))
                    return;
                else if (fv.validateUsernameSpace(etEmail, EMPTY_FIELD_MSG))
                    return;
                else if (TextUtils.isEmpty(gender)) {
                    Toast.makeText(getActivity(), EMPTY_SEX_MSG, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    requestSignUp();
                }
                break;

            //case R.id.img_Google:
                //((LoginActivity) (getActivity())).onGooglePlus();
             //   break;

            //case R.id.img_Facebook:
                //((LoginActivity) (getActivity())).onFblogin();
                // ((LoginActivity)(getActivity())).onFbloginMethod();
                // ((LoginActivity)(getActivity())).doFblogin();
                //break;

            case R.id.btnLinkToLoginScreen:
                LoginFragment loginFragment = new LoginFragment();
                LoginActivity.self.replaceFragment(loginFragment);
                break;
        }
    }

    // request for signup
    private void requestSignUp() {
        Map<String, String> pairs = new HashMap<>();
        pairs.put(METHOD_PARAM, SIGNUP_METHOD);
        pairs.put(USERNAME_PARAM, etName.getText().toString().trim());
        pairs.put(EMAIL_PARAM, etEmail.getText().toString().trim());
        pairs.put(GENDER_PARAM, gender);
        //pairs.put("birthday", et_dateBirth.getText().toString());
        pairs.put(DEV_TYPE_PARAM, AppConstant.DEVICE_TYPE);
        pairs.put(DEV_TOKEN_PARAM, sharePrefs.getDeviceToken());
        pairs.put(LAT_PARAM, sharePrefs.getLatitude());
        pairs.put(LON_PARAM, sharePrefs.getLongitude());
        pairs.put(USER_TYPE_PARAM, "4");
        pairs.put("option", "3");
        pairs.put(PASS_PARAM, etPassword.getText().toString());

        RestInteraction interaction = new RestInteraction(getActivity());
        interaction.setCallBack(new IAsyncResponse() {
            @Override
            public void onRestInteractionResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equalsIgnoreCase("1")) {
                        getJsonData(object);
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

    private void getJsonData(JSONObject object) {
        try {
            JSONObject jsonRootObject = new JSONObject(String.valueOf(object));
            JSONArray jsonArray = jsonRootObject.optJSONArray("data");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String codeResp = jsonRootObject.getString(CODE_RESP);


            String code_status = jsonRootObject.getString(CODE_RESP);
            sharePrefs.setCodeStatus(code_status);

            /*
                sharePrefs.setUserId(jsonObject.getString("userid").toString());
                sharePrefs.setUserName(jsonObject.getString("username").toString());
                sharePrefs.setUserEmail(jsonObject.getString("email").toString());
                sharePrefs.setSessionKey(jsonObject.getString("sessionkey").toString());
                sharePrefs.setGender(jsonObject.getString("gender").toString());
                sharePrefs.setMaxOfferDistance(jsonObject.getString("maxofferdistance").toString());
                sharePrefs.setMaxOfferView(jsonObject.getString("maxofferview").toString());
                sharePrefs.setRepeatOffer(jsonObject.getString("repeatoffer").toString());
            */

            sharePrefs.setUserName(jsonObject.getString(USERNAME_PARAM));
            sharePrefs.setUserEmail(jsonObject.getString(EMAIL_PARAM));
            sharePrefs.setUserId(jsonObject.getString(USERID_PARAM));
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

            Intent intent;
            if (codeResp.equals(ZERO_RESP)) {
                intent = new Intent(LoginActivity.self, IntroActivity.class);
            } else {
                intent = new Intent(LoginActivity.self, MainActivity.class);
            }

            LoginActivity.self.startActivity(intent);
            LoginActivity.self.finish();

            sharePrefs.setIsFirstTimeUser(true);
            sharePrefs.setIsAnonymous(false);
            sharePrefs.setIsGroupActive(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void findViews(View view) {
        img_Google = (ImageView) view.findViewById(R.id.img_Google);
        img_Facebook = (ImageView) view.findViewById(R.id.img_Facebook);
        ll_register = (LinearLayout) view.findViewById(R.id.btnRegister);
        etEmail = (EditText) view.findViewById(R.id.registerEmail);
        etPassword = (EditText) view.findViewById(R.id.registerPassword);
        etName = (EditText) view.findViewById(R.id.registerName);
        btn_login = (Button) view.findViewById(R.id.btnLinkToLoginScreen);
        mCbShowPwd = (CheckBox) view.findViewById(R.id.cbShowPwd);
        gender_radioGroup = (RadioGroup) view.findViewById(R.id.gender_rg);
    }
}
