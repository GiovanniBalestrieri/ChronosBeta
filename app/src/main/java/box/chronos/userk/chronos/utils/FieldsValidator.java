package box.chronos.userk.chronos.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * Created by userk on 14/12/16.
 */


public class FieldsValidator {

    private Context context;

    public FieldsValidator(Context context) {
        this.context = context;
    }


    private synchronized void clearError(final EditText view) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setError(null);
            }
        }, 1000);


    }

    public synchronized boolean validateEmail(EditText view, String message) {
        if (message == null || message.isEmpty())
            message = "Please enter a valid email";

        ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
        ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
                view.getText().toString()).matches()) {
            view.requestFocus();
            view.setError(ssbuilder);
            clearError(view);
            return true;
        }
        return false;
    }

    public synchronized boolean validateNotEmpty(final TextView view, String message) {
        if (message == null || message.isEmpty())
            message = "This field should not be empty";
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
        ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
        if (view.getText().toString().isEmpty()) {
            view.requestFocus();
            view.setError(ssbuilder);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setError(null);
                }
            }, 1000);

            return true;
        }
        return false;
    }

    public synchronized boolean validateNotEmpty(EditText view, String message) {
        if (message == null || message.isEmpty() || message.equalsIgnoreCase(""))
            message = "This field should not be empty";
        ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
        ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
        if (view.getText().toString().isEmpty()) {
            view.requestFocus();
            view.setError(ssbuilder);
            clearError(view);
            return true;
        }
        return false;
    }

    public synchronized boolean validateLength(EditText view, int minLength, int maxLength,
                                               String message) {
        if (view.getText().toString().equals("")
                || view.getText().toString().length() < minLength) {
            if (message == null || message.isEmpty())
                message = "Input should be more than " + minLength
                        + " characters.";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(
                    message);
            ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
            view.requestFocus();
            view.setError(ssbuilder);
            clearError(view);
            return true;
        }
        if (view.getText().toString().length() > maxLength) {
            if (message == null || message.isEmpty())
                message = "Input should be less than " + maxLength
                        + " characters.";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(
                    message);
            ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
            view.requestFocus();
            view.setError(ssbuilder);
            clearError(view);
            return true;
        }
        return false;
    }

    public synchronized boolean validateLength(TextView view, int minLength, int maxLength,
                                               String message) {
        if (view.getText().toString().equals("")
                || view.getText().toString().length() < minLength) {
            if (message == null || message.isEmpty())
                message = "Input should be more than " + minLength
                        + " characters.";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(
                    message);
            ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
            view.setError(ssbuilder);
            return true;
        }
        if (view.getText().toString().length() > maxLength) {
            if (message == null || message.isEmpty())
                message = "Input should be less than " + maxLength
                        + " characters.";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(
                    message);
            ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
            view.setError(ssbuilder);
            return true;
        }
        return false;
    }


    public synchronized boolean validateValidCharacters(EditText view, Pattern pattern,
                                                        String message) {
        if (!pattern.matcher(view.getText().toString()).matches()) {
            if (message == null || message.isEmpty())
                message = "This input does not fit the requiered pattern.";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(
                    message);
            ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
            view.requestFocus();
            view.setError(ssbuilder);
            clearError(view);
            return true;
        }
        return false;
    }

    public synchronized boolean validateUsernameWithoutSpace(EditText view,
                                                             String message) {
        if (view.getText().toString().toString().contains(" ")) {
            if (message == null || message.isEmpty())
                message = "This input does not fit the requiered pattern.";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
            ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
            view.requestFocus();
            view.setError(ssbuilder);
            clearError(view);
            return true;
        }
        return false;
    }

    public synchronized boolean validateUsernameSpace(EditText view, String message) {
        if (view.getText().toString().toString().trim().equalsIgnoreCase("")) {
            if (message == null || message.isEmpty())
                message = "This field should not be empty.";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
            ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
            view.requestFocus();
            view.setError(ssbuilder);
            clearError(view);
            return true;
        }
        return false;
    }

    public synchronized boolean validateNameSpace(EditText view, String message) {
        if (view.getText().toString().toString().trim().equalsIgnoreCase("")) {
            if (message == null || message.isEmpty())
                message = "This field should not be empty.";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
            ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
            view.requestFocus();
            view.setError(ssbuilder);
            clearError(view);
            return true;
        }
        return false;
    }

    public synchronized boolean validatePhoneNumberSpace(EditText view, String message) {
        if (view.getText().toString().toString().trim().equalsIgnoreCase("")) {
            if (message == null || message.isEmpty())
                message = "This field should not be empty.";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
            ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
            view.requestFocus();
            view.setError(ssbuilder);
            clearError(view);
            return true;
        }
        if ((view.getText().toString().toString().length() < 10 || view.getText().toString().toString().length() > 13)) {
            message = "Please enter the right phone number.";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(message);
            ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
            view.requestFocus();
            view.setError(ssbuilder);
            clearError(view);
            return true;
        }
        return false;
    }

    public synchronized boolean validatePasswordMatch(EditText password, EditText confirmPassword, String message) {


        if (!password.getText().toString().equalsIgnoreCase(confirmPassword.getText().toString())) {
            if (message == null || message.isEmpty())
                message = "Password Mismatch";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(
                    message);
            ssbuilder.setSpan(fgcspan, 0, message.length(), 0);
            confirmPassword.requestFocus();
            confirmPassword.setError(ssbuilder);
            clearError(confirmPassword);
            return true;
        }

        return false;

    }
}
