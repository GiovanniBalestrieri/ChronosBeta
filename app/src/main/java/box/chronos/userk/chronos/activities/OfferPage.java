package box.chronos.userk.chronos.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.util.FloatProperty;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import box.chronos.userk.chronos.callbacks.IAsyncResponse;
import box.chronos.userk.chronos.objects.Offer;
import box.chronos.userk.chronos.objects.Shop;
import box.chronos.userk.chronos.R;
import box.chronos.userk.chronos.serverRequest.AppUrls;
import box.chronos.userk.chronos.serverRequest.RestInteraction;
import box.chronos.userk.chronos.utils.AppController;
import box.chronos.userk.chronos.utils.UserSharedPreference;
import box.chronos.userk.chronos.utils.Utility;

import static box.chronos.userk.chronos.serverRequest.AppUrls.IMAGE_URL;
import static box.chronos.userk.chronos.utils.AppConstant.DELAY_TEN_SEC;
import static box.chronos.userk.chronos.utils.AppConstant.EUR_SIGN;
import static box.chronos.userk.chronos.utils.AppConstant.FIVE_KM;
import static box.chronos.userk.chronos.utils.AppConstant.K_METERS;
import static box.chronos.userk.chronos.utils.AppConstant.METERS;
import static box.chronos.userk.chronos.utils.AppConstant.METHOD_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.MORE_THAN_FIVE_KM;
import static box.chronos.userk.chronos.utils.AppConstant.MORE_THAN_ONE_KM;
import static box.chronos.userk.chronos.utils.AppConstant.OFF_ID_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.ONE_KM;
import static box.chronos.userk.chronos.utils.AppConstant.ONE_KM_FLOAT;
import static box.chronos.userk.chronos.utils.AppConstant.ONE_KM_INT;
import static box.chronos.userk.chronos.utils.AppConstant.ONE_RESP;
import static box.chronos.userk.chronos.utils.AppConstant.PERC_SIGN;
import static box.chronos.userk.chronos.utils.AppConstant.SESSION_KEY_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.SPENT_MORE_THAN_TEN_METHOD;
import static box.chronos.userk.chronos.utils.AppConstant.SUCCESS_PARAM;
import static box.chronos.userk.chronos.utils.AppConstant.USERID_PARAM;
import static box.chronos.userk.chronos.utils.AppController.TAG;
import static box.chronos.userk.chronos.utils.algebra.MathUtils.fixFloatFormat;

/**
 * Created by userk on 10/12/16.
 */

public class OfferPage extends Activity {
    TextView offCat;
    TextView offTitle;
    TextView finalPrice;
    TextView offPrice;
    TextView offDesc;
    TextView offDiscount;
    TextView doveShop;
    TextView distance;
    ImageView offImage;
    LinearLayout checkInside;
    String urlImage;
    HashMap<String,Shop> shops;
    String shopId;
    String offId;
    private UserSharedPreference sharePrefs;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offer_new);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hitApiAfterDelay();

        setupOfferPage();
        getShops();
        getViews();

        // TODO request for gps

        final Offer offX = getIntent().getExtras().getParcelable("Offer");

        fillViews(offX);

        shopId = offX.getShopId();
        offId = offX.getId_offer();


        checkInside.setOnClickListener(new View.OnClickListener(){
             @Override
            public void onClick(View v) {
                 // StartActivity Shop and pass data
                 Intent i = new Intent(getApplicationContext(),MapOffer.class);

                 i.putExtra("latitude",offX.getLatitude());
                 i.putExtra("address",offX.getBusinessaddress());
                 i.putExtra("busname",offX.getBusinessname());
                 i.putExtra("longitude",offX.getLongitude());
                 startActivity(i);

             }
        });

        if (urlImage != null && !urlImage.equals("")) {
            offImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), FullImage.class);
                    i.putExtra("url_image", urlImage);
                    startActivity(i);
                }
            });
        }
    }

    private void hitApiAfterDelay() {
        handler.postDelayed(new Runnable() {
            /*
             * Showing Delay api hit screen with ten second delay timer.
             */
            @Override
            public void run() {
                apiHitForParticularOfferDelay();
            }
        }, DELAY_TEN_SEC);
    }

    private void getViews(){
        checkInside = (LinearLayout) findViewById(R.id.checkInsideLayout);
        finalPrice = (TextView) findViewById(R.id.final_price_offer_page);
        offCat = (TextView) findViewById(R.id.upper_bar_category_title);
        offTitle = (TextView) findViewById(R.id.offer_title_overlay);
        offPrice = (TextView) findViewById(R.id.price_offer);
        offDiscount = (TextView) findViewById(R.id.discount_offer);
        offDesc = (TextView) findViewById(R.id.offer_description);
        distance = (TextView) findViewById(R.id.distance_offer_page_tv);
        doveShop = (TextView) findViewById(R.id.dove_si_trova);
        offImage = ( ImageView ) findViewById(R.id.thumbnail_offer);

    }

    private void fillViews(Offer offX) {
        offCat.setText(offX.getCategory());
        offDesc.setText(offX.getOfferdescription());
        offTitle.setText(offX.getTitle());
        offTitle.setTypeface(null, Typeface.BOLD);

        doveShop.setText(offX.getBusinessname());
        distance.setText(prepareDistance(offX.getDistance()));

        offPrice.setText(offX.getPrice() + EUR_SIGN);
        offPrice.setPaintFlags(offPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        offDiscount.setText(offX.getDiscount() + PERC_SIGN);
        finalPrice.setText(computeFinalPrice(offX.getPrice(), offX.getDiscount()));


        ArrayList<String> picList = getIntent().getStringArrayListExtra("pictures");

        String urlCat = IMAGE_URL + offX.getCategoryphoto();
        //
        // Picasso.with(mContext).load(urlCat).into(holder.thumbnail_cat);
        //Picasso.with(this).load(urlCat).into(shop_logo);

        if (picList.size()>0) {
            urlImage = IMAGE_URL + picList.get(0);
            Glide.with(this).load(urlImage).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(offImage);//placeholder(R.drawable.progress_animation)
        } else {
            offImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.empty, null));
        }


    }

    // Get user and session info
    private void setupOfferPage() {
        sharePrefs = AppController.getPreference();
    }

    // api hit view timer not used for android
    private void apiHitForParticularOfferDelay() {
        Map<String, String> pairs = new HashMap<>();
        pairs.put(METHOD_PARAM, SPENT_MORE_THAN_TEN_METHOD);
        pairs.put(USERID_PARAM, sharePrefs.getUserId());
        pairs.put(SESSION_KEY_PARAM, sharePrefs.getSessionKey());
        pairs.put(OFF_ID_PARAM, offId);

        RestInteraction interaction = new RestInteraction(this);
        interaction.setCallBack(new IAsyncResponse() {
            @Override
            public void onRestInteractionResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString(SUCCESS_PARAM).equalsIgnoreCase(ONE_RESP)) {
                        Log.d("1= SEC","RICEVUTO");
                    } else {
                        Utility.showAlertDialog(getApplicationContext(), object.getString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRestInteractionError(String message) {
                Utility.showAlertDialog(getApplicationContext(), message);
            }
        });
        interaction.makeServiceRequest(AppUrls.COMMON_URL, pairs, TAG, "hit");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    public String prepareDistance(String d) {
        String res = MORE_THAN_ONE_KM;
        float distance_km = Float.valueOf(d);
        if ( distance_km > Float.valueOf(FIVE_KM)){
            res = MORE_THAN_FIVE_KM;
        }  else if ( distance_km < Float.valueOf(FIVE_KM) && distance_km > Float.valueOf(ONE_KM)){
            res = String.format("%.0f", distance_km) + K_METERS;
        } else if ( distance_km < Float.valueOf(ONE_KM)){
            Log.d("AAAA",Float.toString(distance_km*1000.0f));
            res = String.format("%.0f", distance_km*1000.0f) + METERS;
        }
        return res;
    }

    public String computeFinalPrice(String init, String sconto){
        String res = "";
        Float ini = Float.valueOf(fixFloatFormat(init));
        Float disc = Float.valueOf(fixFloatFormat(sconto));
        Float fin = ini*(1- disc/100.f);
        res = String.format("%.2f",fin) + EUR_SIGN;
        return res;
    }

    public void fullScreen() {

        // BEGIN_INCLUDE (get_current_ui_flags)
        // The UI options currently enabled are represented by a bitfield.
        // getSystemUiVisibility() gives us that bitfield.
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("OFFER PAGE", "Turning immersive mode mode off. ");
        } else {
            Log.i("OFFERPAGE", "Turning immersive mode mode on.");
        }

        // Navigation bar hiding:  Backwards compatible to ICS.
        if (Build.VERSION.SDK_INT >= 14) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //END_INCLUDE (set_ui_flags)
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
