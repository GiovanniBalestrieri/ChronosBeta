package box.chronos.userk.brain.utils.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import box.chronos.userk.brain.adapters.ArticleAdapter;
import box.chronos.userk.brain.adapters.OfferAdapter;
import box.chronos.userk.brain.objects.Offer;

/**
 * Created by userk on 06/06/17.
 */

public class ListUtilities {

    public ListUtilities() {
    }

    /* Offers Utilities */
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


    /* Article Utilities */
    public static void sortArticlesDistanceAsc(List<Offer> offerList, ArticleAdapter adapter) {
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

    public static List<Offer> findStringInOffers(Iterable<Offer> listOfOffers, String name) {
        List<Offer> offerList = new ArrayList<Offer>();
        for(Offer off : listOfOffers) {
            if (off.getBusinessname() != null) {
                if (off.getBusinessname().contains(name)) {
                    offerList.add(off);
                }
            }

            if (off.getTitle() != null) {
                if (off.getTitle().contains(name)) {
                    offerList.add(off);
                }
            }

            if (off.getDescription() != null) {
                if (off.getDescription().contains(name)) {
                    offerList.add(off);
                }
            }

            if (off.getPrice() != null) {
                if (off.getPrice().contains(name)) {
                    offerList.add(off);
                }
            }
        }

        return offerList;
    }

    /* Article Utilities */
    public static void searchArticlesString(List<Offer> offerList, ArticleAdapter adapter, String query) {
        if (offerList.size() > 0) {
            List<Offer> a = findStringInOffers(offerList, query);
            offerList.clear();
            offerList = a;
            adapter.notifyDataSetChanged();
        }

    }

    public static void sortArticlesDistanceDesc(List<Offer> offerList, ArticleAdapter adapter) {
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

    public static void sortArticlesPriceDesc(List<Offer> offerList, ArticleAdapter adapter) {
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

    public static void sortArticlesPriceAsc(List<Offer> offerList, ArticleAdapter adapter) {
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
