package box.chronos.userk.chronos.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import box.chronos.userk.chronos.objects.Offer;
import box.chronos.userk.chronos.R;

/**
 * Created by ChronosTeam on 27/02/2017.
 */
public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder>{

    private Context mContext;
    private List<Offer> offerList;
    private final RecyclerView recyclerView;
    //private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public OffersAdapter(Context mContext, List<Offer> mlicenceList, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.recyclerView = recyclerView;
        this.offerList = mlicenceList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView overflow;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_card_offer);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail_card_offer);
            overflow = (ImageView) view.findViewById(R.id.overflow_card_offer);
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
        Offer lic = offerList.get(position);
        holder.title.setText(lic.getTitle());

        //String urlImage = BASE_URL + LICENCES_IMG + lic.getId();

        // loading City cover using Glide library
        // Glide.with(mContext).load(lic.getThumbnail()).into(holder.thumbnail);
        // thumbnail image
        //Log.d("ADAPTER", " URL: " + urlImage);
        holder.thumbnail.setImageResource(offerList.get(position).getDrawable_thumb());

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
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
