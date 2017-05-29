package box.chronos.userk.brain.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
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



        Picasso.with(this).load(image).into(imgFullImage); //.placeholder(R.drawable.piwo_48)
        imgFullImage.setOnTouchListener(new ImageMatrixTouchHandler(this));
    }

}
