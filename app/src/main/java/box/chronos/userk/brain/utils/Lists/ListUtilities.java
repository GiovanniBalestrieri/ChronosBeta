package box.chronos.userk.brain.utils.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import box.chronos.userk.brain.adapters.ArticleAdapter;
import box.chronos.userk.brain.adapters.CategoryAdapter;
import box.chronos.userk.brain.adapters.OfferAdapter;
import box.chronos.userk.brain.objects.Category;
import box.chronos.userk.brain.objects.Offer;

/**
 * Created by userk on 06/06/17.
 */

public class ListUtilities {

    public ListUtilities() {
    }

    /* Offers Utilities */
    public static void sortOffersDistanceAsc(List<Offer> offerList, OfferAdapter adapter) {
        if (offerList.size() > 2) {
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
        if (offerList.size() > 2) {
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
        if (offerList.size() > 2) {
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
        if (offerList.size() > 2) {
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
        if (offerList.size() > 2) {
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

        // TODO handle multiple results: string present in title and description
        // will the offer be displayed twice?
        for(Offer off : listOfOffers) {
            if (off.getBusinessname() != null) {
                if (off.getBusinessname().toLowerCase().contains(name.toLowerCase())) {
                    offerList.add(off);
                }
            }

            if (off.getTitle() != null) {
                if (off.getTitle().toLowerCase().contains(name.toLowerCase())) {
                    offerList.add(off);
                }
            }

            if (off.getDescription() != null) {
                if (off.getDescription().toLowerCase().contains(name.toLowerCase())) {
                    offerList.add(off);
                }
            }

            if (off.getPrice() != null) {
                if (off.getPrice().toLowerCase().contains(name.toLowerCase())) {
                    offerList.add(off);
                }
            }
        }

        return offerList;
    }

    /* Article Utilities */
    public static List<Offer> searchArticlesString(List<Offer> offerList, String query) {
        List<Offer> res = new ArrayList<Offer>();
        if (offerList.size() > 2) {
            List<Offer> a = findStringInOffers(offerList, query);
            if (a.size()>0) {
                // Clear only if needed
                //offerList.clear();
                res =  a;
            } else {
                return offerList;
            }
        }
        return res;
    }

    public static void sortArticlesDistanceDesc(List<Offer> offerList, ArticleAdapter adapter) {
        if (offerList.size() > 2) {
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
        if (offerList.size() > 2) {
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
        if (offerList.size() > 2) {
            Collections.sort(offerList, new Comparator<Offer>() {
                @Override
                public int compare(Offer o1, Offer o2) {
                    return Float.valueOf(o1.getPrice()).compareTo(Float.valueOf(o2.getPrice()));
                }
            });
            adapter.notifyDataSetChanged();
        }
    }


    /*
     * Categories Sort by item number
     */
    public static void sortCatItemDesc(List<Category> categoryList, CategoryAdapter adapter) {
        if (categoryList.size() > 2) {
            Collections.sort(categoryList, new Comparator<Category>() {
                @Override
                public int compare(Category o1, Category o2) {
                    return Float.valueOf(o2.getCount()).compareTo(Float.valueOf(o1.getCount()));
                }
            });
            adapter.notifyDataSetChanged();
        }
    }


}
