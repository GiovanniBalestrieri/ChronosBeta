package box.chronos.userk.brain.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import box.chronos.userk.brain.R;
import box.chronos.userk.brain.objects.Offer;
import box.chronos.userk.brain.utils.Lists.ListUtilities;

import static box.chronos.userk.brain.serverRequest.AppUrls.IMAGE_URL;
import static box.chronos.userk.brain.utils.AppConstant.EMPTY_STRING;
import static box.chronos.userk.brain.utils.AppConstant.EUR_SIGN;
import static box.chronos.userk.brain.utils.AppConstant.FIFTEEN_MIN;
import static box.chronos.userk.brain.utils.AppConstant.FIVE_KM;
import static box.chronos.userk.brain.utils.AppConstant.FOURTY_5_MIN;
import static box.chronos.userk.brain.utils.AppConstant.K_METERS;
import static box.chronos.userk.brain.utils.AppConstant.METERS;
import static box.chronos.userk.brain.utils.AppConstant.MORE_THAN_FIVE_KM;
import static box.chronos.userk.brain.utils.AppConstant.ONE_KM;
import static box.chronos.userk.brain.utils.AppConstant.PERC_SIGN;
import static box.chronos.userk.brain.utils.AppConstant.STRING_15_MIN;
import static box.chronos.userk.brain.utils.AppConstant.STRING_30_MIN;
import static box.chronos.userk.brain.utils.AppConstant.STRING_45_MIN;
import static box.chronos.userk.brain.utils.AppConstant.STRING_DUE;
import static box.chronos.userk.brain.utils.algebra.MathUtils.fixFloatFormat;
import static box.chronos.userk.brain.utils.algebra.MathUtils.prepareDistanceOffers;
import static box.chronos.userk.brain.ux.AppMessage.NO_OFFER_MSG;


/**
 * Created by ChronosTeam on 27/02/2017.
 */
public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder>  implements Filterable {

    private String TAG = "OfferAdapter";
    private Context mContext;
    private List<Offer> offerList;
    private final RecyclerView recyclerView;
    private List<Offer> filteredData = null;
    private ItemFilter mFilter = new ItemFilter();
    //private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public OfferAdapter(Context mContext, List<Offer> mlicenceList, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.recyclerView = recyclerView;
        this.offerList = mlicenceList;
        this.filteredData = mlicenceList;
    }


    public Filter getFilter() {
        return mFilter;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView cat;
        public TextView shop_name;
        public ImageView thumbnail;
        public ImageView thumbnail_cat;
        public TextView distance;
        public ImageView shop_logo;
        public TextView price;
        public TextView discount;
        public TextView finalPrice;
        public TextView timeout;
        public LinearLayout topLL, actionLL,midLL;
        private ProgressBar progressBar;

        public MyViewHolder(View view) {
            super(view);
            //title = (TextView) view.findViewById(R.id.title_card_offer);
            //cat = (TextView) view.findViewById(R.id.category_card_article);
            shop_name = (TextView) view.findViewById(R.id.shop_top_card_info);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail_card_article);
            distance = (TextView) view.findViewById(R.id.distance_logo_card_article);
            discount = (TextView) view.findViewById(R.id.tv_discount_offer_card);
            timeout = (TextView) view.findViewById(R.id.timeout_card_offer);
            finalPrice = (TextView) view.findViewById(R.id.tv_final_price_card);
            actionLL = (LinearLayout) view.findViewById(R.id.action_info_card);
            midLL = (LinearLayout) view.findViewById(R.id.mid_info_card);
            topLL = (LinearLayout) view.findViewById(R.id.top_info_card_ll);
            //thumbnail_cat = (ImageView) view.findViewById(R.id.thumbnail_card_article_cat);
            //shop_logo = (ImageView) view.findViewById(R.id.shop_logo_card_article);
            price = (TextView) view.findViewById(R.id.price_card_article);

            progressBar = (ProgressBar) view.findViewById(R.id.progress_offer_card);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offers_card_v3, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Log.d(TAG,"Item position: " + Integer.toString(position));
        Offer off = filteredData.get(position);
        if (off.isDummy() == 0) {
            holder.shop_name.setText(off.getBusinessname()/*.toUpperCase()*/);

            if (off.getDiscount().isEmpty()) {
                holder.price.setVisibility(View.GONE);
                holder.discount.setText(off.getPrice() + EUR_SIGN);
                holder.finalPrice.setVisibility(View.GONE);
            } else {
                Log.d("OfferAdapter", "Discount: " + Float.valueOf(fixFloatFormat(off.getDiscount())));
                if (Float.valueOf(fixFloatFormat(off.getDiscount())) == 0.0f) {
                    holder.price.setVisibility(View.GONE);
                    holder.discount.setText(off.getPrice() + EUR_SIGN);
                    holder.finalPrice.setVisibility(View.GONE);
                } else {
                    holder.price.setVisibility(View.VISIBLE);
                    holder.price.setText(off.getPrice() + EUR_SIGN);
                    holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.discount.setText(fixFloatFormat(off.getDiscount()) + PERC_SIGN);
                    holder.finalPrice.setVisibility(View.VISIBLE);
                    holder.finalPrice.setText(computeFinalPrice(off.getPrice(), off.getDiscount()));
                }
            }
            holder.distance.setText(prepareDistanceOffers(off.getDistance()));

            // Visibility Gone Action
            holder.actionLL.setVisibility(View.GONE);

            String[] time = off.getTimeout().split(":");
            int min = Integer.parseInt(time[0].trim());
            holder.timeout.setText(prepareTimeout(min));

            if (min >= FOURTY_5_MIN) {
                //holder.topLL.setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.top_card_timeout_green));
                holder.timeout.setTextColor(ContextCompat.getColor(this.mContext, R.color.top_card_timeout_green));
            } else if (min < FOURTY_5_MIN && min >= FIFTEEN_MIN) {
                //holder.topLL.setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.top_card_timeout_orange));
                holder.timeout.setTextColor(ContextCompat.getColor(this.mContext, R.color.top_card_timeout_orange));
            } else if (min < FIFTEEN_MIN) {
                //holder.topLL.setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.top_card_timeout_red));
                holder.timeout.setTextColor(ContextCompat.getColor(this.mContext, R.color.top_card_timeout_red));
            } else {
                //holder.topLL.setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.top_card_timeout_green));
                holder.timeout.setTextColor(ContextCompat.getColor(this.mContext, R.color.top_card_timeout_green));
            }


            String urlCat = IMAGE_URL + off.getCategoryphoto();
            //
            // Picasso.with(mContext).load(urlCat).into(holder.thumbnail_cat);
            //Picasso.with(mContext).load(urlCat).into(holder.shop_logo);


            Log.d(TAG, "Price:\t" + off.getPrice());
            Log.d(TAG, "BusinessName:\t" + off.getBusinessname());
            Log.d(TAG, "Discount:\t" + off.getDiscount());

            if (off.hasPicture()) {
                Map.Entry<String, String> entry = filteredData.get(position).getAvailablePictures().entrySet().iterator().next();
                String key = entry.getKey();
                String value = entry.getValue();
                System.out.println(key);
                System.out.println(value);

                String urlImage = IMAGE_URL + value;
                Log.d(TAG, "Image:\t" + urlImage);

                Glide.with(mContext).load(urlImage)
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                holder.progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                holder.progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(holder.thumbnail);//placeholder(R.drawable.progress_animation)

            } else {
                Glide.with(mContext).load(R.drawable.empty).placeholder(R.drawable.progress_animation).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thumbnail);
            }
        } else {
            // No offer available
            Log.d("OfferAdapter","No offer");
            holder.actionLL.setVisibility(View.GONE);
            holder.topLL.setVisibility(View.GONE);
            holder.midLL.setVisibility(View.GONE);
            holder.shop_name.setText(NO_OFFER_MSG);
            holder.distance.setText(EMPTY_STRING);
            Glide.with(mContext).load(R.drawable.test4).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thumbnail);
            holder.progressBar.setVisibility(View.GONE);

        }

    }

    private String prepareTimeout(int min) {
        String res;
        if (min >= FOURTY_5_MIN){
            res = STRING_45_MIN;
        } else if (min < FOURTY_5_MIN && min >= FIFTEEN_MIN) {
            res = STRING_30_MIN;
        } else if (min < FIFTEEN_MIN) {
            res = STRING_15_MIN;
        } else {
            res = STRING_DUE;
        }
        return res;
    }

    private String prepareDistance(String d) {
        String res = "0";
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

    public void touchIt() {
        notifyDataSetChanged();
    }

    // Computes final price given an init and sconto string
    public String computeFinalPrice(String init, String sconto){
        String res = "";

        Float ini = Float.valueOf(fixFloatFormat(init));
        Float disc = Float.valueOf(fixFloatFormat(sconto));
        Float fin = ini*(1- disc/100.f);
        res = String.format("%.2f",fin) + EUR_SIGN;
        return res;
    }


    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Offer> list = offerList;

            int count = list.size();
            List<Offer> nlist = new ArrayList<Offer>(count);

            nlist = ListUtilities.searchArticlesString(list,filterString);

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<Offer>) results.values;
            notifyDataSetChanged();
        }

    }

    /* Used to restore original content after search dismission */
    public void restoreList(){
        this.filteredData = this.offerList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }
}
