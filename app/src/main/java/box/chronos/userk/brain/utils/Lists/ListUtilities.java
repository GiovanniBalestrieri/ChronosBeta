package box.chronos.userk.brain.utils.Lists;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import box.chronos.userk.brain.adapters.OfferAdapter;
import box.chronos.userk.brain.objects.Offer;

/**
 * Created by userk on 06/06/17.
 */

public class ListUtilities {

    public ListUtilities() {
    }

    public static void sortOffersDistanceAsc(List<Offer> offerList, OfferAdapter adapter) {
        if (offerList.size() > 0) {
            Collections.sort(offerList, new Comparator<Offer>() {
                @Override
                public int compare(Offer o1, Offer o2) {
                    return Float.valueOf(o1.getDistance()).compareTo(Float.valueOf(o2.getDistance()));
                }
            });
            adapter.notifyDataSetChanged();
        }
    }

    public static void sortOffersDistanceDesc(List<Offer> offerList, OfferAdapter adapter) {
        if (offerList.size() > 0) {
            Collections.sort(offerList, new Comparator<Offer>() {
                @Override
                public int compare(Offer o1, Offer o2) {
                    return Float.valueOf(o2.getDistance()).compareTo(Float.valueOf(o1.getDistance()));
                }
            });
            adapter.notifyDataSetChanged();
        }
    }

    public static void sortOffersPriceDesc(List<Offer> offerList, OfferAdapter adapter) {
        if (offerList.size() > 0) {
            Collections.sort(offerList, new Comparator<Offer>() {
                @Override
                public int compare(Offer o1, Offer o2) {
                    return Float.valueOf(o2.getPrice()).compareTo(Float.valueOf(o1.getPrice()));
                }
            });
            adapter.notifyDataSetChanged();
        }
    }

    public static void sortOffersPriceAsc(List<Offer> offerList, OfferAdapter adapter) {
        if (offerList.size() > 0) {
            Collections.sort(offerList, new Comparator<Offer>() {
                @Override
                public int compare(Offer o1, Offer o2) {
                    return Float.valueOf(o1.getPrice()).compareTo(Float.valueOf(o2.getPrice()));
                }
            });
            adapter.notifyDataSetChanged();
        }
    }


}
