package box.chronos.userk.brain.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import agency.tango.materialintroscreen.animations.ViewTranslationWrapper;
import box.chronos.userk.brain.R;
import box.chronos.userk.brain.activities.MainActivity;
import box.chronos.userk.brain.utils.AppController;
import box.chronos.userk.brain.utils.UserSharedPreference;

import static box.chronos.userk.brain.serverRequest.AppUrls.FAQ_URL;
import static box.chronos.userk.brain.ux.AppMessage.FAQ_TITLE;

/**
 * Created by userk on 20/06/17.
 */

public class HelpFAQ extends Fragment {
    private UserSharedPreference sharePrefs;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharePrefs = AppController.getPreference();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final Activity activity = getActivity();
        //final View content = activity.findViewById(android.R.id.content).getRootView();
        view = inflater.inflate(R.layout.faq, container, false);

        ((MainActivity) getActivity()).drawer.closeDrawer(Gravity.LEFT);
        WebView myWebView = (WebView) view.findViewById(R.id.webview);
        myWebView.loadUrl(FAQ_URL);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(FAQ_TITLE);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);

        return view;
    }
}
