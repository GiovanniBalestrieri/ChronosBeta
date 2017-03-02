package box.chronos.userk.chronos.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;

import com.ihsanbal.introview.IntroView;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;
import box.chronos.userk.chronos.R;
import box.chronos.userk.chronos.UI.Code;

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
                        .image(R.drawable.slide_one)
                        .title("Benvenuto")
                        .description("Description 3")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("We provide solutions to make you love your work");
                    }
                }, "Work with love"));
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.red2)
                        .buttonsColor(R.color.orange2)
                        //.possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
                        //.neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
                        .image(R.drawable.slide_two)
                        .title("Benvenuto")
                        .description("Description 3")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("We provide solutions to make you love your work");
                    }
                }, "Work with love"));
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.teal)
                        .buttonsColor(R.color.bluegrey)
                        //.possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
                        //.neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
                        .image(R.drawable.slide_three)
                        .title("Trova la tua offerta")
                        .description("Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet \nLorem ipsum dolor sit amet")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("We provide solutions to make you love your work");
                    }
                }, "Work with love"));
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.lightblue)
                        .buttonsColor(R.color.indigo)
                        //.possiblePermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_SMS})
                        //.neededPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
                        .image(R.drawable.slide_four)
                        .title("Sei pronto?")
                        //.description("Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet \nLorem ipsum dolor sit amet")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("We provide solutions to make you love your work");
                    }
                }, "Work with love"));


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
