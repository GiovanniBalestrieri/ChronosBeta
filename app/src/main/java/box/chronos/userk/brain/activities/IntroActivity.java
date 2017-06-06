package box.chronos.userk.brain.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;
import box.chronos.userk.brain.R;

import static box.chronos.userk.brain.ux.AppMessage.SLIDE_DESC_1;
import static box.chronos.userk.brain.ux.AppMessage.SLIDE_DESC_2;
import static box.chronos.userk.brain.ux.AppMessage.SLIDE_DESC_3;
import static box.chronos.userk.brain.ux.AppMessage.SLIDE_DESC_4;
import static box.chronos.userk.brain.ux.AppMessage.SLIDE_TITLE_1;
import static box.chronos.userk.brain.ux.AppMessage.SLIDE_TITLE_2;
import static box.chronos.userk.brain.ux.AppMessage.SLIDE_TITLE_3;
import static box.chronos.userk.brain.ux.AppMessage.SLIDE_TITLE_4;

/**
 * Created by ChronosTeam on 01/03/2017.
 */

public class IntroActivity extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        IntroView mIntroView = new IntroView(this);

        mIntroView.init(getSupportFragmentManager(),
                getResources().getStringArray(R.array.titles),
                getResources().getStringArray(R.array.texts),
                R.drawable.slide_one,
                R.drawable.slide_two,
                R.drawable.slide_three,
                R.drawable.slide_four);
        */
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.blue2)
                        .buttonsColor(R.color.teal)
                        //.possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
                        //.neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
                        .image(R.drawable.p1negozi)
                        .title(SLIDE_TITLE_1)
                        .description(SLIDE_DESC_1)
                        .build());
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.red2)
                        .buttonsColor(R.color.orange2)
                        //.possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
                        //.neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
                        .image(R.drawable.p2carrello)
                        .title(SLIDE_TITLE_2)
                        .description(SLIDE_DESC_2)
                        .build());
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.teal)
                        .buttonsColor(R.color.bluegrey)
                        //.possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
                        //.neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
                        .image(R.drawable.p3mappa)
                        .title(SLIDE_TITLE_3)
                        .description(SLIDE_DESC_3)
                        .build());
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.lightblue)
                        .buttonsColor(R.color.indigo)
                        //.possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
                        //.neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
                        .image(R.drawable.p4limousine)
                        .title(SLIDE_TITLE_4)
                        .description(SLIDE_DESC_4)
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("Scopri i nostri partners");
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.chronosbox.com/html/partners.html"));
                        startActivity(browserIntent);
                    }
                }, "Partners"));


        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });



        }

    @Override
    public void onFinish() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
