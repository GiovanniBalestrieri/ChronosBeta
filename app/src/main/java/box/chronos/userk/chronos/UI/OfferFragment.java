package box.chronos.userk.chronos.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import box.chronos.userk.chronos.Activities.MainActivity;
import box.chronos.userk.chronos.R;

/**
 * Created by ChronosTeam on 27/02/2017.
 */

public class OfferFragment extends Fragment {
    private TextView title;
    private TextView description;
    private TextView price;
    private TextView discount;
    private TextView finalPrice;
    private TextView shop;
    private TextView shopChain;
    private TextView category;
    private TextView genre;


    public OfferFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.offer_new, container, false);

        ((MainActivity) getActivity()).lockHeader(true);




        return rootView;
    }
}
