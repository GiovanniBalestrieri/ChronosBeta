package box.chronos.userk.chronos.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import box.chronos.userk.chronos.R;

import static box.chronos.userk.chronos.settings.Intro.CODE_LEN;

/**
 * Created by userk on 14/12/16.
 */

public class Code extends Activity {
    Button action;
    private TextInputLayout codeInput;
    private EditText codeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code);

        //TextView code = (TextView) findViewById(R.id.code);
        codeInput = (TextInputLayout) findViewById(R.id.input_layout_code);
        codeString = (EditText) findViewById(R.id.code);

        action = (Button) findViewById(R.id.action_code);
        action.setVisibility(View.GONE);

        codeString.setGravity(Gravity.CENTER_HORIZONTAL);
        codeString.addTextChangedListener(new codeWatcher(codeString));

        action.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

            }
        });

    }

    private boolean  validateCode(){
        if (codeString.getText().toString().trim().isEmpty()) {
            codeInput.setError(getString(R.string.err_msg_code));
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
                //Toast.makeText(getApplicationContext(),"yolo",Toast.LENGTH_SHORT).show();
                //action.setVisibility(View.VISIBLE);
                Intent i = new Intent(Code.this, IntroActivity.class);
                startActivity(i);
                finish();
                //overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
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
}
