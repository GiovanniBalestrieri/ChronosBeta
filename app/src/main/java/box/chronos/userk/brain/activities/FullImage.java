package box.chronos.userk.brain.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import box.chronos.userk.brain.R;

/**
 * Created by userk on 05/05/17.
 */

public class FullImage extends AppCompatActivity {
    ImageView imgFullImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_full_screen);

        ///findViewBYID
        imgFullImage = (ImageView) findViewById(R.id.imageView1);
        String image = getIntent().getStringExtra("url_image");


        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_full_image);



        Glide.with(this)
                .load(image)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imgFullImage);



        imgFullImage.setOnTouchListener(new ImageMatrixTouchHandler(this));
    }

}
