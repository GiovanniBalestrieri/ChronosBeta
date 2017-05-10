package box.chronos.userk.chronos.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
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

import static android.app.Activity.RESULT_OK;
import static box.chronos.userk.chronos.serverRequest.AppUrls.IMAGE_URL;
import static box.chronos.userk.chronos.serverRequest.AppUrls.SERVER_URL;
import static box.chronos.userk.chronos.utils.AppConstant.ADDRESS_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.BIRTHDAY_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.CODE_RESP;
import static box.chronos.userk.chronos.utils.AppConstant.DATA_RESP;
import static box.chronos.userk.chronos.utils.AppConstant.DEVICE_TYPE;
import static box.chronos.userk.chronos.utils.AppConstant.DEV_TYPE_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.EMAIL_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.FEMALE_STRING;
import static box.chronos.userk.chronos.utils.AppConstant.FEMALE_STRING_IT;
import static box.chronos.userk.chronos.utils.AppConstant.GENDER_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.GET_PROFILE_INFO_METHOD;
import static box.chronos.userk.chronos.utils.AppConstant.LOGIN_METHOD;
import static box.chronos.userk.chronos.utils.AppConstant.MALE_STRING;
import static box.chronos.userk.chronos.utils.AppConstant.MALE_STRING_IT;
import static box.chronos.userk.chronos.utils.AppConstant.METHOD_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.PHONE_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.PHOTO_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.SESSION_KEY_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.SUCCESS_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.UID_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.UPDATE_PROFILE_METHOD;
import static box.chronos.userk.chronos.utils.AppConstant.USERID_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.USERNAME_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.ZERO_RESP;
import static box.chronos.userk.chronos.utils.usertypes.TypeOfUsers.SHOP_USER;
import static box.chronos.userk.chronos.ux.AppMessage.CHECK_MAIL_MSG;
import static box.chronos.userk.chronos.ux.AppMessage.EMPTY_MAIL_MSG;
import static box.chronos.userk.chronos.ux.AppMessage.EMPTY_PASSWORD_MSG;

/**
 * Created by userk on 08/03/17.
 */
public class ProfileFragment extends Fragment implements DatePickerDialog.OnDateSetListener  {

    private static String TAG = ProfileFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String genderToSend; private String mParam2;
    private ImageView cover, editProfileBtn, profilePic;
    private EditText et_registredPassword, et_registredEmailId;
    private View view;
    private TextView userName, userGender, shortBio, userEmail, birthdayUser, userBusName, userBusAdd, userBusPhone;
    private LinearLayout userGenderLL, userGenderEditLL, shortBioLL,shortBioEditLL, birthdayLL, birthdayEditLL,
            userBusinessNameLL, userBusinessAddLL, userBusPhoneLL, userBusPhoneEditLL;
    private EditText unameET, busphoneET;
    private Spinner genderEdit;
    private UserSharedPreference sharePrefs;
    Button btpic, btnup;
    private Uri fileUri;
    String picturePath;
    Uri selectedImage;
    Bitmap photo;
    String ba1;
    private boolean toggleEdit = false;

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
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final Activity activity = getActivity();
        final View content = activity.findViewById(android.R.id.content).getRootView();


        view = inflater.inflate(R.layout.profile_v1, container, false);
        findViews(view);
        requestProfileInfo();


        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //upload();
                //clickpic();
                Utility.showAlertDialog(getActivity(),"Ottieni più punti per modificare l'immagine del profilo");
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleEdit = !toggleEdit;
                changeLayout();
            }
        });

        birthdayUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toggleEdit) {
                    setupTimer();
                }
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Profilo");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);

        return view;
    }

    private void setupTimer() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ProfileFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void changeLayout() {
        // switch between cases
        if (toggleEdit) {
            // Edit Mode
            editProfileBtn.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_done_black_24dp, null));

            userName.setVisibility(View.GONE);
            unameET.setVisibility(View.VISIBLE);
            unameET.setText(sharePrefs.getUserName());
            unameET.setGravity(View.TEXT_ALIGNMENT_CENTER);

            if (sharePrefs.getUserType().equals(SHOP_USER)) {

                userBusPhoneLL.setVisibility(View.GONE);
                userBusPhoneEditLL.setVisibility(View.VISIBLE);
                busphoneET.setText(sharePrefs.getBusinessphone());

            }

            userGenderEditLL.setVisibility(View.VISIBLE);
            userGenderLL.setVisibility(View.GONE);
        } else {
            // normal mode

            updateProfileInfo();



        }
    }

    void updateProfileInfo() {

        // TODO send new picture and retrieve new path


        Map<String, String> pairs = new HashMap<>();
        pairs.put(METHOD_PARAM, UPDATE_PROFILE_METHOD);
        pairs.put(USERID_PARAM, sharePrefs.getUserId());
        pairs.put(SESSION_KEY_PARAM, sharePrefs.getSessionKey());


        // First upload new information then update sharedPreferences
        pairs.put(USERNAME_PARAM, unameET.getText().toString());
        pairs.put(BIRTHDAY_PARAM, birthdayUser.getText().toString());
        // TODO check if genderToSend is always valid
        pairs.put(GENDER_PARAM, genderToSend);
        pairs.put(PHONE_PARAM, busphoneET.getText().toString());

        RestInteraction interaction = new RestInteraction(getActivity());
        interaction.setCallBack(new IAsyncResponse() {
            @Override
            public void onRestInteractionResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    getJsonData(object);
                    updateViews();
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

    private void updateViews() {
        editProfileBtn.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_mode_edit_black_24dp, null));

        userName.setVisibility(View.VISIBLE);
        userName.setText(unameET.getText());
        unameET.setVisibility(View.GONE);


        if (sharePrefs.getUserType().equals(SHOP_USER)) {

            userBusPhoneLL.setVisibility(View.VISIBLE);
            userBusPhoneEditLL.setVisibility(View.GONE);
            userBusPhone.setText(busphoneET.getText());

        }

        userGenderEditLL.setVisibility(View.GONE);
        userGenderLL.setVisibility(View.VISIBLE);
        fillGender();
    }

    /*
    private void upload() {
        // Image location URL
        Log.e("path", "----------------" + picturePath);

        // Image
        Bitmap bm = BitmapFactory.decodeFile(picturePath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bao);
        byte[] ba = bao.toByteArray();
        //ba1 = Base64.encodeBytes(ba);

        Log.e("base64", "-----" + ba1);

        // Upload image to server
        new uploadToServer().execute();

    }
    */

    /*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {

            selectedImage = data.getData();
            photo = (Bitmap) data.getExtras().get("data");

            // Cursor to get image uri to display

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getApplicationContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ImageView imageView = (ImageView) findViewById(R.id.Imageprev);
            imageView.setImageBitmap(photo);
        }
    }
*/

    /*
    public class uploadToServer extends AsyncTask<Void, Void, String> {

        private ProgressDialog pd = new ProgressDialog(getActivity());
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Wait image uploading!");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
            nameValuePairs.add(new BasicNameValuePair("ImageName", System.currentTimeMillis() + ".jpg"));
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(SERVER_URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                String st = EntityUtils.toString(response.getEntity());
                Log.v("log_tag", "In the try Loop" + st);

            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
            }
            return "Success";

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();
        }
    }

    */


    // request profile information
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
            String success = jsonRootObject.getString(SUCCESS_PARAM);

            // Update sharedPreferences iif success == 1
            if (success.equals("1")) {
                String a = jsonObject.getString(USERID_PARAM);
                String b = jsonObject.getString(USERNAME_PARAM);
                String c = jsonObject.getString(EMAIL_PARAM);
                String d = jsonObject.getString(PHOTO_PARAM);
                String e = jsonObject.getString(GENDER_PARAM);
                String f = jsonObject.getString(BIRTHDAY_PARAM);
                String g = jsonObject.getString(ADDRESS_PARAM);
                String h = jsonObject.getString(PHONE_PARAM);

                sharePrefs.setUserId(jsonObject.getString(USERID_PARAM));
                sharePrefs.setUserName(jsonObject.getString(USERNAME_PARAM));
                sharePrefs.setUserEmail(jsonObject.getString(EMAIL_PARAM));
                sharePrefs.setUserImage(jsonObject.getString(PHOTO_PARAM));
                sharePrefs.setGender(jsonObject.getString(GENDER_PARAM));
                sharePrefs.setBirthday(jsonObject.getString(BIRTHDAY_PARAM));
                sharePrefs.setDefaultAddress(jsonObject.getString(ADDRESS_PARAM));
                sharePrefs.setBusinessphone(jsonObject.getString(PHONE_PARAM));

                // Update Navigation drawer information
                ((MainActivity) getActivity()).updateProfilePictureNav();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void findViews(View view) {
        cover = (ImageView) view.findViewById(R.id.header_cover_image);
        editProfileBtn = (ImageView) view.findViewById(R.id.edit_profile_button);
        profilePic = (ImageView) view.findViewById(R.id.user_profile_photo);
        shortBio = (TextView) view.findViewById(R.id.user_profile_short_bio);
        userName = (TextView) view.findViewById(R.id.user_profile_name);



        unameET = (EditText) view.findViewById(R.id.user_profile_name_edit);
        unameET.setVisibility(View.GONE);

        userEmail = (TextView) view.findViewById(R.id.tv_profile_user_email);

        userGenderLL = (LinearLayout) view.findViewById(R.id.ll_user_gender_info);
        userGenderEditLL = (LinearLayout) view.findViewById(R.id.ll_user_gender_info_edit);
        userGenderEditLL.setVisibility(View.GONE);
        userGender = (TextView) view.findViewById(R.id.tv_profile_user_gender);

        genderEdit = (Spinner) view.findViewById(R.id.sp_profile_user_gender);

        genderEdit.setOnItemSelectedListener(new ItemSelectedListener());
        if (sharePrefs.getGender().equals(MALE_STRING)) {
            genderEdit.setSelection(0);
        } else if (sharePrefs.getGender().equals(FEMALE_STRING)) {
            genderEdit.setSelection(1);
        }
        fillGender();


        birthdayUser = (TextView) view.findViewById(R.id.tv_profile_user_birth);





        // Usertype 2 Shop
        userBusName = (TextView) view.findViewById(R.id.tv_profile_bus_name);

        userBusPhone = (TextView) view.findViewById(R.id.tv_profile_bus_phone);
        busphoneET = (EditText) view.findViewById(R.id.et_profile_user_phone_edit);

        userBusPhoneLL = (LinearLayout) view.findViewById(R.id.ll_user_bus_phone);


        userBusPhoneEditLL = (LinearLayout) view.findViewById(R.id.ll_user_bus_phone_edit);
        userBusPhoneEditLL.setVisibility(View.GONE);

        userBusAdd = (TextView) view.findViewById(R.id.tv_profile_bus_address);
        userBusinessNameLL = (LinearLayout) view.findViewById(R.id.ll_user_bus_name);
        userBusinessAddLL = (LinearLayout) view.findViewById(R.id.ll_user_bus_address);


        if (!sharePrefs.getUserType().equals(SHOP_USER)){
            userBusPhoneLL.setVisibility(View.GONE);
            userBusinessAddLL.setVisibility(View.GONE);
            userBusinessNameLL.setVisibility(View.GONE);
        }

        cover.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.business_black, null));
    }

    private void fillViews() {

        if(!sharePrefs.getUserImage().equals("")) {
            String urlImage = IMAGE_URL + sharePrefs.getUserImage();
            ((MainActivity) getActivity()).updateProfilePictureNav();
            //Picasso.with(getActivity()).load(urlImage).into(((MainActivity) getActivity()).profPic);
            Picasso.with(getActivity()).load(urlImage).into(profilePic);
        } else {
            Picasso.with(getActivity()).load(R.drawable.user_dummy).into(profilePic);
        }

        userEmail.setText(sharePrefs.getUserEmail());
        userName.setText(sharePrefs.getUserName());

        fillGender();

        birthdayUser.setText(sharePrefs.getBirthday());

        if (sharePrefs.getUserType().equals(SHOP_USER)){
            Log.d(TAG,"\tadd: " + sharePrefs.getBusinessaddress() + "\t phone:" + sharePrefs.getBusinessphone() + "\tname: " +sharePrefs.getBusinessname());
            userBusAdd.setText(sharePrefs.getBusinessaddress());
            userBusPhone.setText(sharePrefs.getBusinessphone());
            userBusName.setText(sharePrefs.getBusinessname());
        }

    }

    private void fillGender(){
        String[] genders = getResources().getStringArray(R.array.android_dropdown_arrays);
        if (sharePrefs.getGender().equals(MALE_STRING)) {
            userGender.setText(genders[0]);
        } else if (sharePrefs.getGender().equals(FEMALE_STRING)) {
            userGender.setText(genders[1]);
        }
    }

    /*
    private void clickpic() {
        // Check Camera
        if (getActivity().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // Open default camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, 100);

        } else {
            Toast.makeText(getActivity(), "Camera not supported", Toast.LENGTH_LONG).show();
        }
    }
    */


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year+"-"+dayOfMonth+"-"+(monthOfYear+1);
        birthdayUser.setText(date);
    }

    public class ItemSelectedListener implements AdapterView.OnItemSelectedListener {

        //get strings of first item
        String firstItem = String.valueOf(genderEdit.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            String a = parent.getItemAtPosition(pos).toString();
            Log.d("AAA","\t\t"+a);

            if (a.equals(MALE_STRING_IT)) {
                genderToSend = MALE_STRING;
            } else if (a.equals(FEMALE_STRING_IT)) {
                genderToSend = FEMALE_STRING;
            }

            Log.d("AAA","\t\t"+genderToSend);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }
}
