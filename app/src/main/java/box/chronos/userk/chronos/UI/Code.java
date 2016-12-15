package box.chronos.userk.chronos.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import box.chronos.userk.chronos.Activities.MainActivity;
import box.chronos.userk.chronos.Activities.SplashScreen;
import box.chronos.userk.chronos.R;

import static box.chronos.userk.chronos.Settings.Intro.CODE_LEN;

/**
 * Created by userk on 14/12/16.
 */

public class Code extends Activity {
    Button action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code);

        TextView code = (TextView) findViewById(R.id.code);
        action = (Button) findViewById(R.id.action_code);
        action.setVisibility(View.GONE);
        code.setGravity(Gravity.CENTER_HORIZONTAL);
        code.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start==CODE_LEN - 1) {
                    // TODO Remote Code check
                    Toast.makeText(getApplicationContext(),"yolo",Toast.LENGTH_SHORT).show();
                    //action.setVisibility(View.VISIBLE);


                    Intent i;
                    i = new Intent(Code.this, MainActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                }
            }


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
            }
        });

        action.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

            }
        });

    }
}
