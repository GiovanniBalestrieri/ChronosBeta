package box.chronos.userk.chronos.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static box.chronos.userk.chronos.utils.AppConstant.ADDRESS_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.BIRTHDAY_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.CODE_RESP;
import static box.chronos.userk.chronos.utils.AppConstant.DATA_RESP;
import static box.chronos.userk.chronos.utils.AppConstant.DEVICE_TYPE;
import static box.chronos.userk.chronos.utils.AppConstant.EMAIL_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.GENDER_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.GET_PROFILE_INFO_METHOD;
import static box.chronos.userk.chronos.utils.AppConstant.LOGIN_METHOD;
import static box.chronos.userk.chronos.utils.AppConstant.METHOD_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.PHONE_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.PHOTO_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.SESSION_KEY_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.UID_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.USERID_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.USERNAME_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.ZERO_RESP;
import static box.chronos.userk.chronos.ux.AppMessage.CHECK_MAIL_MSG;
import static box.chronos.userk.chronos.ux.AppMessage.EMPTY_MAIL_MSG;
import static box.chronos.userk.chronos.ux.AppMessage.EMPTY_PASSWORD_MSG;

/**
 * Created by userk on 08/03/17.
 */
public class ProfileFragment extends Fragment {

    private static String TAG = ProfileFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1; private String mParam2;
    private ImageView cover, editProfileBtn, profilePic;
    private EditText et_registredPassword, et_registredEmailId;
    private LinearLayout ll_login;
    private TextView userName, shortBio, userEmail, birthdayUser, businessNameUser, phoneUser, addressUser;
    private UserSharedPreference sharePrefs;

    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharePrefs = AppController.getPreference();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final Activity activity = getActivity();
        final View content = activity.findViewById(android.R.id.content).getRootView();


        View view = inflater.inflate(R.layout.profile_v1, container, false);
        findViews(view);
        requestProfileInfo();


        cover.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.business_black, null));

        return view;
    }


    // request for login
    private void requestProfileInfo() {

        Map<String, String> pairs = new HashMap<>();
        pairs.put(METHOD_PARAM, GET_PROFILE_INFO_METHOD);
        pairs.put(USERID_PARAM, sharePrefs.getUserId());
        pairs.put(SESSION_KEY_PARAM, sharePrefs.getSessionKey());

        RestInteraction interaction = new RestInteraction(getActivity());
        interaction.setCallBack(new IAsyncResponse() {
            @Override
            public void onRestInteractionResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equalsIgnoreCase("1")) {
                        getJsonData(object);

                        fillViews();
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

            sharePrefs.setUserId(jsonObject.getString(USERID_PARAM).toString());
            sharePrefs.setUserName(jsonObject.getString(USERNAME_PARAM).toString());
            sharePrefs.setUserEmail(jsonObject.getString(EMAIL_PARAM).toString());
            sharePrefs.setUserImage(jsonObject.getString(PHOTO_PARAM).toString());
            sharePrefs.setSessionKey(jsonObject.getString(SESSION_KEY_PARAM).toString());
            sharePrefs.setGender(jsonObject.getString(GENDER_PARAM).toString());
            sharePrefs.setBirthday(jsonObject.getString(BIRTHDAY_PARAM).toString());
            sharePrefs.setDefaultAddress(jsonObject.getString(ADDRESS_PARAM).toString());
            sharePrefs.setUserPhoneNumber(jsonObject.getString(PHONE_PARAM).toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void findViews(View view) {
        cover = (ImageView) view.findViewById(R.id.header_cover_image);
        editProfileBtn = (ImageView) view.findViewById(R.id.edit_profile_button);
        profilePic = ( ImageView) view.findViewById(R.id.user_profile_photo);
        shortBio = (TextView) view.findViewById(R.id.user_profile_short_bio);
        userName = (TextView) view.findViewById(R.id.user_profile_name);
        userEmail = (TextView) view.findViewById(R.id.tv_profile_user_email);
        //userName = (TextView) view.findViewById(R.id.user_profile_name);


    }

    private void fillViews() {
        userEmail.setText(sharePrefs.getUserEmail());
        userName.setText(sharePrefs.getUserDispayName());
    }
}
