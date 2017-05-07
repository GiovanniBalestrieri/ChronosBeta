package box.chronos.userk.chronos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import box.chronos.userk.chronos.R;
import box.chronos.userk.chronos.objects.Category;
import box.chronos.userk.chronos.objects.Offer;

import static box.chronos.userk.chronos.serverRequest.AppUrls.IMAGE_URL;
import static box.chronos.userk.chronos.serverRequest.AppUrls.SERVER_URL;


/**
 * Created by ChronosTeam on 27/02/2017.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{

    private Context mContext;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<Category> list;

    //private final RecyclerView recyclerView;
    //private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }


    public CategoryAdapter(Context mContext, List<Category> mList, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.recyclerView = recyclerView;
        this.list = mList;
    }

    public CategoryAdapter(Context mContext, List<Category> mList) {
        this.mContext = mContext;
        this.list = mList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView cat_id;
        public TextView shop_name;
        public ImageView thumbnail;
        public ImageView shop_logo;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_cat_card);
            cat_id = (TextView) view.findViewById(R.id.card_id_text);
            //shop_name = (TextView) view.findViewById(R.id.shop_top_card_info);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail_cat_card_b);
            //shop_logo = (ImageView) view.findViewById(R.id.shop_logo_card_article);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card_v2, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Category cat = list.get(position);
        holder.title.setText(cat.getCat_name());
        //holder.cat_id.setText(cat.getCat_id());
        //holder.shop_name.setText(cat.getCat_photo_active());

        String urlImage = IMAGE_URL + list.get(position).getCat_photo_active();

        int width= mContext.getResources().getDisplayMetrics().widthPixels;
        // loading Categories using Picasso library
        Picasso.with(mContext).load(urlImage)
                //.placeholder(R.drawable.scarpa_d)
                .centerCrop().resize(width,width)
                .into(holder.thumbnail);

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
        return list.size();
    }
}
