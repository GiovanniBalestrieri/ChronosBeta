package box.chronos.userk.chronos.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import box.chronos.userk.chronos.R;
import box.chronos.userk.chronos.activities.Code;
import box.chronos.userk.chronos.activities.LoginActivity;
import box.chronos.userk.chronos.activities.MainActivity;
import box.chronos.userk.chronos.callbacks.IAsyncResponse;
import box.chronos.userk.chronos.serverRequest.AppUrls;
import box.chronos.userk.chronos.serverRequest.RestInteraction;
import box.chronos.userk.chronos.utils.AppController;
import box.chronos.userk.chronos.utils.BlurBuilder;
import box.chronos.userk.chronos.utils.FieldsValidator;
import box.chronos.userk.chronos.utils.UserSharedPreference;
import box.chronos.userk.chronos.utils.Utility;

import static box.chronos.userk.chronos.utils.AppConstant.CODE_RESP;
import static box.chronos.userk.chronos.utils.AppConstant.DATA_RESP;
import static box.chronos.userk.chronos.utils.AppConstant.DEVICE_TYPE;
import static box.chronos.userk.chronos.utils.AppConstant.LOGIN_METHOD;
import static box.chronos.userk.chronos.utils.AppConstant.ONE_RESP;
import static box.chronos.userk.chronos.utils.AppConstant.ZERO_RESP;
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
    private EditText et_registredPassword, et_registredEmailId;
    private LinearLayout ll_login;
    private TextView tv_SignUp, tv_ForgotPassword;
    private UserSharedPreference sharePrefs;
    private FieldsValidator fv;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final Activity activity = getActivity();
        final View content = activity.findViewById(android.R.id.content).getRootView();

        View view = inflater.inflate(R.layout.login_fragment, container, false);

        setupLoginFragment();
        findViews(view);
        attachListeners();

        // Setup Background
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test5c);
        Bitmap image = BlurBuilder.blur(getActivity().getApplicationContext(),bm);
        view.setBackground(new BitmapDrawable(activity.getResources(), image));

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

    // request for login
    private void requestForLogin() {
        /*
        Intent intent = new Intent(LoginActivity.self, Code.class);
        LoginActivity.self.startActivity(intent);
        LoginActivity.self.finish();
        */

        Map<String, String> pairs = new HashMap<>();
        pairs.put("method", LOGIN_METHOD);
        pairs.put("email", et_registredEmailId.getText().toString().trim());
        pairs.put("password", et_registredPassword.getText().toString());
        pairs.put("devicetype", DEVICE_TYPE);
        pairs.put("devicetoken", sharePrefs.getDeviceToken());
        pairs.put("latitude", sharePrefs.getLatitude());
        pairs.put("longitude", sharePrefs.getLongitude());

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
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
        interaction.makeServiceRequest(AppUrls.COMMON_URL, pairs, TAG, "Dialog");

    }

    // get json data
    private void getJsonData(JSONObject object) {
        try {
            JSONObject jsonRootObject = new JSONObject(String.valueOf(object));
            JSONArray jsonArray = jsonRootObject.optJSONArray(DATA_RESP);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String codeResp = jsonRootObject.getString(CODE_RESP);

            sharePrefs.setUserId(jsonObject.getString("userid").toString());
            sharePrefs.setUserName(jsonObject.getString("username").toString());
            sharePrefs.setUserEmail(jsonObject.getString("email").toString());
            sharePrefs.setUserImage(jsonObject.getString("photo").toString());
            sharePrefs.setSessionKey(jsonObject.getString("sessionkey").toString());
            sharePrefs.setGender(jsonObject.getString("gender").toString());
            sharePrefs.setMaxOfferDistance(jsonObject.getString("maxofferdistance").toString());
            sharePrefs.setMaxOfferView(jsonObject.getString("maxofferview").toString());
            sharePrefs.setRepeatOffer(jsonObject.getString("repeatoffer").toString());
            sharePrefs.setUserType(jsonObject.getString("usertype").toString());
            sharePrefs.setBirthday(jsonObject.getString("birthday").toString());
            sharePrefs.setDefaultAddress(jsonObject.getString("address").toString());
            sharePrefs.setUserPhoneNumber(jsonObject.getString("phonenumber").toString());
            sharePrefs.setSelectedCatrgory(jsonObject.getString("selctedcategory").toString());

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

    private void findViews(View view) {
        img_Google = (ImageView) view.findViewById(R.id.img_Google);
        img_Facebook = (ImageView) view.findViewById(R.id.img_Facebook);
        ll_login = (LinearLayout) view.findViewById(R.id.tv_Login_v2);
        et_registredEmailId = (EditText) view.findViewById(R.id.et_registredEmailId);
        et_registredPassword = (EditText) view.findViewById(R.id.et_registredPassword);

        tv_ForgotPassword = (TextView) view.findViewById(R.id.tv_ForgotPassword);
        tv_SignUp = (TextView) view.findViewById(R.id.tv_SignUp);
    }
}
