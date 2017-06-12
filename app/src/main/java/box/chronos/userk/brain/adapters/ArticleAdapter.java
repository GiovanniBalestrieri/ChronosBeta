package box.chronos.userk.brain.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import box.chronos.userk.brain.activities.MainActivity;
import box.chronos.userk.brain.objects.Offer;
import box.chronos.userk.brain.R;
import box.chronos.userk.brain.utils.Lists.ListUtilities;

import static box.chronos.userk.brain.serverRequest.AppUrls.IMAGE_URL;
import static box.chronos.userk.brain.utils.AppConstant.EUR_SIGN;
import static box.chronos.userk.brain.utils.AppConstant.PERC_SIGN;
import static box.chronos.userk.brain.utils.algebra.MathUtils.fixFloatFormat;
import static box.chronos.userk.brain.utils.algebra.MathUtils.prepareDistanceArticle;


/**
 * Created by ChronosTeam on 27/02/2017.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> implements Filterable{

    private String TAG = ArticleAdapter.class.getSimpleName();
    private Context mContext;
    private List<Offer> offerList;
    private List<Offer> filteredData = null;

    private LayoutInflater mInflater;

    private ItemFilter mFilter = new ItemFilter();
    //private final RecyclerView recyclerView;
    //private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public ArticleAdapter(Context mContext, List<Offer> mArticlesList, RecyclerView recyclerView) {
        this.mContext = mContext;
        //this.recyclerView = recyclerView;
        this.offerList = mArticlesList;
        this.filteredData = mArticlesList;
    }

    public ArticleAdapter(Context mContext, List<Offer> mArticlesList) {
        this.mContext = mContext;
        this.offerList = mArticlesList;
        this.filteredData = mArticlesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView cat;
        public TextView shop_name;
        public TextView distance;
        public TextView price;
        public ImageView thumbnail;
        public ImageView shop_logo;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.shop_top_card_info_article);
            distance = (TextView) view.findViewById(R.id.distance_logo_card_article);
            cat = (TextView) view.findViewById(R.id.category_offer_card_article);
            shop_name = (TextView) view.findViewById(R.id.business_name_article_card);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail_card_article);
            shop_logo = (ImageView) view.findViewById(R.id.shop_logo_card_article);
            price = (TextView) view.findViewById(R.id.price_article);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_card, parent, false);
        return new MyViewHolder(itemView);
    }


    public Filter getFilter() {
        return mFilter;
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


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Offer off = filteredData.get(position);
        holder.title.setText(off.getTitle());
        holder.cat.setText(off.getCategory());
        holder.shop_name.setText(off.getBusinessname());
        holder.distance.setText(prepareDistanceArticle(off.getDistance()));


        holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.price.setText(fixFloatFormat(off.getPrice()) + EUR_SIGN);


        //holder.price.setText(off.getPrice() + EUR_SIGN);

        //String urlImage = BASE_URL + LICENCES_IMG + lic.getId();

        if (off.hasPicture()) {
            Map.Entry<String,String> entry = filteredData.get(position).getAvailablePictures().entrySet().iterator().next();
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key);
            System.out.println(value);

            String urlImage = IMAGE_URL + value;
            Log.d(TAG,"Image:\t" + urlImage);

            Glide.with(mContext).load(urlImage).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thumbnail);//placeholder(R.drawable.progress_animation)

        } else {
            Glide.with(mContext).load(R.drawable.empty).placeholder(R.drawable.progress_animation).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    public List<Offer> getOfferList(){
        return filteredData;
    }

    /* Used to restore original content after search dismission */
    public void restoreList(){
        this.filteredData = this.offerList;
        notifyDataSetChanged();
    }
}
