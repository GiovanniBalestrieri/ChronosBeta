package box.chronos.userk.chronos.fragments;

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
import box.chronos.userk.chronos.utils.AppController;
import box.chronos.userk.chronos.utils.BlurBuilder;
import box.chronos.userk.chronos.utils.FieldsValidator;
import box.chronos.userk.chronos.utils.UserSharedPreference;

import static box.chronos.userk.chronos.ux.AppMessage.CHECK_MAIL_MSG;

/**
 * Created by userk on 08/03/17.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {

    private static String TAG = SignUpFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;private String mParam2;
    private Button btn_login;
    private ImageView img_Google, img_Facebook;
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

    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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

        return view;
    }



    private void setupSignupFragment() {
        sharePrefs = AppController.getPreference();
        fv = new FieldsValidator(getContext());
    }


    private void attachListeners() {
        img_Google.setOnClickListener(this);
        img_Facebook.setOnClickListener(this);
        ll_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                if (TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString()) || TextUtils.isEmpty(etName.getText().toString())) {
                    if (TextUtils.isEmpty(etEmail.getText().toString())) {
                        Toast.makeText(getActivity(), "Please enter email", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(etEmail.getText().toString())) {
                        Toast.makeText(getActivity(), "Please enter password", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(etName.getText().toString())) {
                        Toast.makeText(getActivity(), "Inserisci un nome utente", Toast.LENGTH_SHORT).show();
                    }
                } else if (fv.validateEmail(etEmail,etEmail.getText().toString())) {
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



            case R.id.btnLinkToLoginScreen:
                LoginFragment loginFragment = new LoginFragment();
                LoginActivity.self.replaceFragment(loginFragment);
                break;


        }
    }

    // request for login
    private void requestForLogin() {

        Intent intent = new Intent(LoginActivity.self, Code.class);

        LoginActivity.self.startActivity(intent);
        LoginActivity.self.finish();

        /*
        Map<String, String> pairs = new HashMap<>();
        pairs.put("method", "userLogin");
        pairs.put("email", et_registredEmailId.getText().toString());
        pairs.put("password", et_registredPassword.getText().toString());
        pairs.put("devicetype", "2");
        pairs.put("devicetoken", sharePrefs.getDeviceToken());
        pairs.put("latitude", sharePrefs.getLatitude());
        pairs.put("longitude", sharePrefs.getLongitude());

        RestIntraction intraction = new RestIntraction(getActivity());
        intraction.setCallBack(new IAsyncResponse() {
            @Override
            public void onRestIntractionResponse(String response) {
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
            public void onRestIntractionError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });
        intraction.makeServiceRequest(AppUrl.COMMON_URL, pairs, TAG, "Dialog");

        */
    }

    // get json data
    private void getJsonData(JSONObject object) {
        try {
            JSONObject jsonRootObject = new JSONObject(String.valueOf(object));
            JSONArray jsonArray = jsonRootObject.optJSONArray("data");
            JSONObject jsonObject = jsonArray.getJSONObject(0);

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

            Intent intent = new Intent(LoginActivity.self, MainActivity.class);

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
        ll_register = (LinearLayout) view.findViewById(R.id.btnRegister);
        etEmail = (EditText) view.findViewById(R.id.registerEmail);
        etPassword = (EditText) view.findViewById(R.id.registerPassword);
        etName = (EditText) view.findViewById(R.id.registerName);
        btn_login = (Button) view.findViewById(R.id.btnLinkToLoginScreen);
        mCbShowPwd = (CheckBox) view.findViewById(R.id.cbShowPwd);
    }
}
