package box.chronos.userk.chronos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.FloatProperty;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

import box.chronos.userk.chronos.R;
import box.chronos.userk.chronos.objects.Offer;

import static box.chronos.userk.chronos.serverRequest.AppUrls.IMAGE_URL;
import static box.chronos.userk.chronos.utils.AppConstant.METERS;
import static box.chronos.userk.chronos.utils.AppConstant.MORE_THAN_ONE_KM;
import static box.chronos.userk.chronos.utils.AppConstant.ONE_KM;


/**
 * Created by ChronosTeam on 27/02/2017.
 */
public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder>{

    private Context mContext;
    private List<Offer> offerList;
    private final RecyclerView recyclerView;
    //private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public OfferAdapter(Context mContext, List<Offer> mlicenceList, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.recyclerView = recyclerView;
        this.offerList = mlicenceList;
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

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_card_offer);
            cat = (TextView) view.findViewById(R.id.category_card_article);
            shop_name = (TextView) view.findViewById(R.id.shop_top_card_info);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail_card_article);
            distance = (TextView) view.findViewById(R.id.distance_logo_card_article);
            //thumbnail_cat = (ImageView) view.findViewById(R.id.thumbnail_card_article_cat);
            shop_logo = (ImageView) view.findViewById(R.id.shop_logo_card_article);
            price = (TextView) view.findViewById(R.id.price_card_article);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offers_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Offer off = offerList.get(position);
        holder.title.setText(off.getTitle());
        holder.cat.setText(off.getCategory());
        holder.shop_name.setText(off.getBusinessname().toUpperCase());
        holder.price.setText(off.getPrice() + " €");

        holder.distance.setText(prepareDistance(off.getDistance()));


        String urlCat = IMAGE_URL + off.getCategoryphoto();
        //
        // Picasso.with(mContext).load(urlCat).into(holder.thumbnail_cat);
        Picasso.with(mContext).load(urlCat).into(holder.shop_logo);

        if (off.hasPicture()) {
            Map.Entry<String,String> entry = offerList.get(position).getAvailablePictures().entrySet().iterator().next();
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key);
            System.out.println(value);

            String urlImage = IMAGE_URL + value;

            Picasso.with(mContext).load(urlImage).into(holder.thumbnail); //.placeholder(R.drawable.piwo_48)

        } else {
            Picasso.with(mContext).load(R.drawable.empty).into(holder.thumbnail);
        }
    }

    public String prepareDistance(String d) {
        String res = MORE_THAN_ONE_KM;
        float distance = Float.valueOf(d);
        if ( distance > Float.valueOf(ONE_KM)){
            res = MORE_THAN_ONE_KM;
        } else {
            res = String.format("%.0f", distance*1000) + METERS;
        }
        return res;
    }

    /*
    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "Comment  " , Toast.LENGTH_SHORT).show();

        if (v instanceof ImageView){
            mListener.onTomato((ImageView)v);
        } else {
            mListener.onPotato(v);
        }
    }
    */

    public void touchIt() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }
}
