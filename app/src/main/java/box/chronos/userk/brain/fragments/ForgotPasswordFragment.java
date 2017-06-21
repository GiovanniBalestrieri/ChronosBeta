package box.chronos.userk.brain.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import box.chronos.userk.brain.R;
import box.chronos.userk.brain.activities.LoginActivity;
import box.chronos.userk.brain.callbacks.IAsyncResponse;
import box.chronos.userk.brain.serverRequest.AppUrls;
import box.chronos.userk.brain.serverRequest.RestInteraction;
import box.chronos.userk.brain.utils.Utility;

import static box.chronos.userk.brain.utils.constants.AppConstant.FORGOT_PSS_METHOD;

/**
 * Created by francesco on 27/03/2017.
 */

public class ForgotPasswordFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = ForgotPasswordFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private EditText et_email;
    private LinearLayout ll_send;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    public static ForgotPasswordFragment newInstance(String param1, String param2) {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
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
        View v = inflater.inflate(R.layout.forgot_pss_fragment, container, false);
        //LoginActivity.self.back.setVisibility(View.GONE);
        //LoginActivity.self.tv_TopHeading.setText(getResources().getString(R.string.forgot_password));

        et_email = (EditText) v.findViewById(R.id.et_Email_forgot);
        ll_send = (LinearLayout) v.findViewById(R.id.ll_send_email);
        ll_send.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_send_email:
                if (isValidEmailId(et_email.getText().toString())) {
                    requestSend();
                }
                break;
        }
    }

    // request send
    private void requestSend() {
        Map<String, String> pairs = new HashMap<>();
        pairs.put("method",FORGOT_PSS_METHOD);
        pairs.put("email", et_email.getText().toString());

        RestInteraction interaction = new RestInteraction(getActivity());
        interaction.setCallBack(new IAsyncResponse() {
            @Override
            public void onRestInteractionResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("success").equalsIgnoreCase("1")) {
                        Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                        LoginActivity.self.onBackPressed();
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

    boolean isValidEmailId(String email) {
        boolean isValid = false;
        Pattern pattern = Pattern.compile("^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);
        CharSequence inputStr = email;
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
