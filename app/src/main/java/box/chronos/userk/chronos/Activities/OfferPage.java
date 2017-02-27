package box.chronos.userk.chronos.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import box.chronos.userk.chronos.Objects.Offer;
import box.chronos.userk.chronos.R;

/**
 * Created by userk on 10/12/16.
 */

public class OfferPage extends Activity {

    TextView offCat;
    TextView offTitle;
    TextView offPrice;
    TextView offDesc;
    TextView offDiscount;
    ImageView offImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_new);

        Offer offX = getIntent().getExtras().getParcelable("Offer");
        offCat = (TextView) findViewById(R.id.upper_bar_category_title);
        offTitle = (TextView) findViewById(R.id.offer_title_overlay);
        offPrice = (TextView) findViewById(R.id.price_offer);
        offDiscount = (TextView) findViewById(R.id.discount_offer);
        offDesc = (TextView) findViewById(R.id.offer_description);
        offImage = ( ImageView ) findViewById(R.id.thumbnail_offer);

        offCat.setText(offX.getCategories());
        offDesc.setText(offX.getDescription());
        offTitle.setText(offX.getTitle());
        offPrice.setText(offX.getPrice());
        offDiscount.setText(offX.getDiscount());


        Picasso.with(this).load(offX.getDrawable_thumb()).into( offImage);

    }
}
