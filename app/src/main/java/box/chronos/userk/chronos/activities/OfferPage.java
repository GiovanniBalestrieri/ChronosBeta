package box.chronos.userk.chronos.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import box.chronos.userk.chronos.objects.Offer;
import box.chronos.userk.chronos.objects.Shop;
import box.chronos.userk.chronos.R;

import static box.chronos.userk.chronos.serverRequest.AppUrls.IMAGE_URL;

/**
 * Created by userk on 10/12/16.
 */

public class OfferPage extends AppCompatActivity {

    TextView offCat;
    TextView offTitle;
    TextView offPrice;
    TextView offDesc;
    TextView offDiscount;
    ImageView offImage;
    LinearLayout checkInside;
    HashMap<String,Shop> shops;
    String shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_new);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getShops();

        Offer offX = getIntent().getExtras().getParcelable("Offer");

        checkInside = (LinearLayout) findViewById(R.id.checkInsideLayout);
        offCat = (TextView) findViewById(R.id.upper_bar_category_title);
        offTitle = (TextView) findViewById(R.id.offer_title_overlay);
        offPrice = (TextView) findViewById(R.id.price_offer);
        offDiscount = (TextView) findViewById(R.id.discount_offer);
        offDesc = (TextView) findViewById(R.id.offer_description);
        offImage = ( ImageView ) findViewById(R.id.thumbnail_offer);

        offCat.setText(offX.getCategory());
        offDesc.setText(offX.getOfferdescription());
        offTitle.setText(offX.getTitle());
        offPrice.setText(offX.getPrice());
        offDiscount.setText(offX.getDiscount());

        shopId = offX.getShopId();

        ArrayList<String> picList = getIntent().getStringArrayListExtra("pictures");

        String urlCat = IMAGE_URL + offX.getCategoryphoto();
        //
        // Picasso.with(mContext).load(urlCat).into(holder.thumbnail_cat);
        //Picasso.with(this).load(urlCat).into(shop_logo);

        if (picList.size()>0) {


            String urlImage = IMAGE_URL + picList.get(0);


            Picasso.with(this).load(urlImage).into(offImage); //.placeholder(R.drawable.piwo_48)

        } else {
            Picasso.with(this).load(R.drawable.empty).into(offImage);
        }

        //Picasso.with(this).load(offX.getDrawable_thumb()).into(offImage);

        checkInside.setOnClickListener(new View.OnClickListener(){
             @Override
            public void onClick(View v) {
                 // StartActivity Shop and pass data

                 Intent i = new Intent(getApplicationContext(),ShopPages.class);

                 i.putExtra("Shop",shops.get(shopId));
                 startActivity(i);
             }
        });
    }

    private void getShops(){

        Shop s1 = new Shop("1","Kent","viale Somalia 2","Fondato nel 1955, il Negozio Kent è Bottegha Storica di Roma. Specializzato in abbigliamento ed accessori uomo donna, con servizio di sartoria su misura per la realizzazione di abiti e camicie personalizzate");
        Shop s2 = new Shop("2","WB","viale Somalia 114","Articoli di abbigliamento per uomo donna e bambino. Il massimo della qualità italiana a portata di mano");
        Shop s3 = new Shop("3","UnitK","Piazza del popolo 3","Ingegneria robotica dal 2019, pionieri dell'interazione uomo macchina e dell'intelligenza artificiale");

        s2.setShopThumbnailProfile(R.drawable.wb_profile);
        s2.setShopThumbnailWallpaper(R.drawable.wb_wallpaper);
        s1.setShopThumbnailProfile(R.drawable.ken_profile);
        s1.setShopThumbnailWallpaper(R.drawable.kent_wall);
        /*
        Shop[] shops = new Shop[]{
                s1,
                s2,
                s3};
        */
        shops = new HashMap<String,Shop>();
        shops.put(s1.getShopId(),s1);
        shops.put(s2.getShopId(),s2);
        shops.put(s3.getShopId(),s3);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        //Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        //startActivityForResult(myIntent, 0);
        return true;

    }
}
