package box.chronos.userk.chronos.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import box.chronos.userk.chronos.objects.Shop;
import box.chronos.userk.chronos.R;

/**
 * Created by userk on 11/12/16.
 */

public class ShopPages extends Activity {

    TextView shopName;
    TextView shopDesc;
    TextView shopAddress;
    ImageView shopProfile;
    ImageView shopWallpaper;
    Shop thisShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_profile);

        shopName = (TextView) findViewById(R.id.shop_name);
        shopDesc = (TextView) findViewById(R.id.shop_desc);
        shopAddress = (TextView) findViewById(R.id.dove_shop);
        shopProfile = (ImageView) findViewById(R.id.shop_logo);
        shopWallpaper = (ImageView) findViewById(R.id.shop_wallpaper);

        getInfoFromIntent();

        shopName.setText(thisShop.getShopName());
        shopDesc.setText(thisShop.getShopDescription());
        shopAddress.setText(thisShop.getShopAddress());

        Picasso.with(this).load(thisShop.getShopThumbnailProfile()).into( shopProfile);
        Picasso.with(this).load(thisShop.getShopThumbnailWallpaper()).into( shopWallpaper);

    }

    private void getInfoFromIntent() {
        thisShop = getIntent().getExtras().getParcelable("Shop");
    }
}
