package box.chronos.userk.chronos.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import box.chronos.userk.chronos.R;
import box.chronos.userk.chronos.callbacks.IAsyncResponse;
import box.chronos.userk.chronos.serverRequest.AppUrls;
import box.chronos.userk.chronos.serverRequest.RestInteraction;
import box.chronos.userk.chronos.utils.AppController;
import box.chronos.userk.chronos.utils.BlurBuilder;
import box.chronos.userk.chronos.utils.UserSharedPreference;
import box.chronos.userk.chronos.utils.Utility;

import static box.chronos.userk.chronos.settings.Intro.CODE_LEN;
import static box.chronos.userk.chronos.utils.AppConstant.CODE_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.CODE_RESP;
import static box.chronos.userk.chronos.utils.AppConstant.CODE_SKIP_METHOD;
import static box.chronos.userk.chronos.utils.AppConstant.CODE_VALIDATION_METHOD;
import static box.chronos.userk.chronos.utils.AppConstant.DATA_RESP;
import static box.chronos.userk.chronos.utils.AppConstant.DEVICE_TYPE;
import static box.chronos.userk.chronos.utils.AppConstant.DEV_TOKEN_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.DEV_TYPE_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.EMAIL_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.ERROR_RESP;
import static box.chronos.userk.chronos.utils.AppConstant.LAT_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.LOGIN_METHOD;
import static box.chronos.userk.chronos.utils.AppConstant.LON_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.METHOD_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.MSG_RESP;
import static box.chronos.userk.chronos.utils.AppConstant.PSS_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.UID_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.USERID_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.ZERO_RESP;

/**
 * Created by userk on 14/12/16.
 */

public class Code extends Activity {
    private static final String TAG = Code.class.getSimpleName();
    Button action;
    private TextInputLayout codeInput;
    private LinearLayout procedi;
    private EditText codeString;
    private UserSharedPreference sharePrefs;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code);

        findViewsAndSetup();
        //retrieveInfoFromIntentIfAvailable();
        retrieveInfoFromSharedPreferences();

        action.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

            }
        });

        procedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG).show();
                requestCodeCheck(1);
            }
        });


        /*
        // Setup Background
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.rome2);
        Bitmap image = BlurBuilder.blur(this.getApplicationContext(),bm, 5.0f);
        View v = procedi.getRootView();
        v.setBackground(new BitmapDrawable(this.getResources(), image));
        */

    }

    private void retrieveInfoFromSharedPreferences() {
        id = sharePrefs.getUserId();
    }

    /**
     * Retrieve info from Main Activity's Intent if Available or get it from AppController class
     */
    private void retrieveInfoFromIntentIfAvailable() {
        /*
        if (this.getIntent().getStringExtra(CAR_NAME) != null && !this.getIntent().getStringExtra(CAR_NAME).equals("")) {
            car_name = this.getIntent().getStringExtra(CAR_NAME);
        }
        else {
            car_name = AppController.getInstance().carNameSelezionata;
        }
        if (this.getIntent().getStringExtra(CAR_ID) != null && !this.getIntent().getStringExtra(CAR_ID).equals("")) {
            car_id = this.getIntent().getStringExtra(CAR_ID);
        } else {
            car_id = AppController.getInstance().carSelezionata;
        }
        if (this.getIntent().getStringExtra(LIC_ID) != null && !this.getIntent().getStringExtra(LIC_ID).equals("")) {
            lic_id = this.getIntent().getStringExtra(LIC_ID);
            AppController.getInstance().patenteSelezionata = lic_id;
        } else {
            lic_id = AppController.getInstance().patenteSelezionata;
        }
        if (this.getIntent().getStringExtra(MAP_ID) != null && !this.getIntent().getStringExtra(MAP_ID).equals("")) {
            map_id = this.getIntent().getStringExtra(MAP_ID);
        } else {
            map_id = AppController.getInstance().mappaSelezionata;
        }
        */
    }

    private void findViewsAndSetup() {

        sharePrefs = AppController.getPreference();
        //TextView code = (TextView) findViewById(R.id.code);
        codeInput = (TextInputLayout) findViewById(R.id.input_layout_code);
        codeString = (EditText) findViewById(R.id.code);

        action = (Button) findViewById(R.id.action_code);
        action.setVisibility(View.GONE);

        codeString.setGravity(Gravity.CENTER_HORIZONTAL);
        codeString.addTextChangedListener(new codeWatcher(codeString));

        procedi = (LinearLayout) findViewById(R.id.layout_invito);


    }

    private boolean  validateCode(){
        if (codeString.getText().toString().trim().isEmpty()) {
        // codeInput.setError(getString(R.string.err_msg_code));
            requestFocus(codeString);
            return false;
        } else {
            codeInput.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private class codeWatcher implements TextWatcher {

        private View v;

        private codeWatcher(View view){
            this.v = view;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (start==CODE_LEN - 1) {
                // TODO Remote Code check

                //Toast.makeText(getApplicationContext(),"Yolo",Toast.LENGTH_SHORT).show();
                //action.setVisibility(View.VISIBLE);

                requestCodeCheck(0);

            }
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
        int after) {
        }

        @Override
        public void afterTextChanged(Editable s)  {
            Intent i;
            switch (v.getId()) {
                case R.id.code:
                    validateCode();
                    break;
                /*
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
                    */
            }
        }
    }

    /*
     +  Starts Code check request
     */
    private void requestCodeCheck(int caseBtn) {

        Map<String, String> pairs = new HashMap<>();
        if (caseBtn == 0) { // send Code
            pairs.put(METHOD_PARAM, CODE_VALIDATION_METHOD);
            Log.d("CODE",codeString.getText().toString());
            pairs.put(CODE_PARAM,codeString.getText().toString());
        }
        else // Skip
            pairs.put(METHOD_PARAM, CODE_SKIP_METHOD);
        pairs.put(EMAIL_PARAM, sharePrefs.getUserEmail());
        pairs.put(UID_PARAM, id);

        RestInteraction interaction = new RestInteraction(this);
        interaction.setCallBack(new IAsyncResponse() {
            @Override
            public void onRestInteractionResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString(ERROR_RESP).equals(ZERO_RESP)) {
                        Log.d(TAG,"Richiesta associazione codice");
                        getJsonData(object);
                    } else {
                        Utility.showAlertDialog(Code.this, object.getString(MSG_RESP));
                        Log.d(TAG,"Errore nella richiesta codice");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG,"Errore nella richiesta codice");
                }
            }

            @Override
            public void onRestInteractionError(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
        interaction.makeServiceRequest(AppUrls.CODE_URL, pairs, TAG, "Code");
    }

    // get json data
    private void getJsonData(JSONObject object) {
        try {
            JSONObject jsonRootObject = new JSONObject(String.valueOf(object));
            String message = jsonRootObject.getString(MSG_RESP);
            String errorResp = jsonRootObject.getString(ERROR_RESP);

            Intent intent;
            intent = new Intent(this, MainActivity.class);
            if (errorResp.equals(ZERO_RESP)) {
                Utility.showAlertDialog(this.getApplication(), "Benvenuto");
            } else {
                Utility.showAlertDialog(this, message);
            }

            startActivity(intent);
            finish();

            this.overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
