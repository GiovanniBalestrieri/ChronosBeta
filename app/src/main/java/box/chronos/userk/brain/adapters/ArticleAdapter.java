package box.chronos.userk.brain.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import box.chronos.userk.brain.objects.Offer;
import box.chronos.userk.brain.R;


/**
 * Created by ChronosTeam on 27/02/2017.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder>{

    private Context mContext;
    private List<Offer> offerList;
    //private final RecyclerView recyclerView;
    //private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public ArticleAdapter(Context mContext, List<Offer> mlicenceList, RecyclerView recyclerView) {
        this.mContext = mContext;
        //this.recyclerView = recyclerView;
        this.offerList = mlicenceList;
    }

    public ArticleAdapter(Context mContext, List<Offer> mlicenceList) {
        this.mContext = mContext;
        this.offerList = mlicenceList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView cat;
        public TextView shop_name;
        public ImageView thumbnail;
        public ImageView shop_logo;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_card_offer);
            cat = (TextView) view.findViewById(R.id.category_card_article);
            shop_name = (TextView) view.findViewById(R.id.shop_top_card_info);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail_card_article);
            shop_logo = (ImageView) view.findViewById(R.id.shop_logo_card_article);
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
        holder.shop_name.setText(off.getShop());

        //String urlImage = BASE_URL + LICENCES_IMG + lic.getId();

        // loading City cover using Glide library
        Log.d("OFFFFF","PIC: " + Integer.toString(offerList.get(position).getDrawable_thumb()));
        Glide.with(mContext).load(offerList.get(position).getDrawable_thumb()).
                        placeholder(R.drawable.progress_animation).into(holder.thumbnail);

        /*
            Glide.with(mContext)
                    .load(offerList.get(position).getDrawable_thumb())
                    //.placeholder(R.drawable.piwo_48)
                    //.transform(new CircleTransform(context))
                    .into(holder.thumbnail);

                    */
        /*
    } else {
            // make sure Glide doesn't load anything into this view until told otherwise
            Glide.clear(holder.imageView);
            // remove the placeholder (optional); read comments below
            holder.imageView.setImageDrawable(null);
        }

        */
        // thumbnail image
        //Log.d("ADAPTER", " URL: " + urlImage);
        //holder.thumbnail.setImageResource(offerList.get(position).getDrawable_thumb());

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

    @Override
    public int getItemCount() {
        return offerList.size();
    }
}
