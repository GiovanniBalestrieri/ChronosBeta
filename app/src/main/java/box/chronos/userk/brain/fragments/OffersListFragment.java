package box.chronos.userk.brain.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import box.chronos.userk.brain.activities.MainActivity;
import box.chronos.userk.brain.activities.OfferPage;
import box.chronos.userk.brain.adapters.OfferAdapter;
import box.chronos.userk.brain.callbacks.IAsyncResponse;
import box.chronos.userk.brain.objects.Offer;
import box.chronos.userk.brain.R;
import box.chronos.userk.brain.objects.payloads.OffersResponse;
import box.chronos.userk.brain.serverRequest.AppUrls;
import box.chronos.userk.brain.serverRequest.RestInteraction;
import box.chronos.userk.brain.settings.Includes;
import box.chronos.userk.brain.utils.AppController;
import box.chronos.userk.brain.utils.Lists.ListUtilities;
import box.chronos.userk.brain.utils.RecycleItemClickListener;
import box.chronos.userk.brain.utils.UserSharedPreference;
import box.chronos.userk.brain.utils.Utility;
import box.chronos.userk.brain.utils.VideoUtility;

import static box.chronos.userk.brain.settings.Includes.all_categories;
import static box.chronos.userk.brain.utils.AppConstant.ALL_CATS;
import static box.chronos.userk.brain.utils.AppConstant.BUSINESSNAME_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.BUSINESS_ADD_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.BUSINESS_PHONE_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.CAT_ID_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.CAT_NAME;
import static box.chronos.userk.brain.utils.AppConstant.CAT_PHOTO_ACTIVE;
import static box.chronos.userk.brain.utils.AppConstant.CAT_PHOTO_DEF;
import static box.chronos.userk.brain.utils.AppConstant.DATA_RESP;
import static box.chronos.userk.brain.utils.AppConstant.DISCOUNT_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.DISTANCE;
import static box.chronos.userk.brain.utils.AppConstant.DISTANCE_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.FIVE_KM;
import static box.chronos.userk.brain.utils.AppConstant.GET_OFFERS_METHOD;
import static box.chronos.userk.brain.utils.AppConstant.GET_OFFERS_METHOD_ANON;
import static box.chronos.userk.brain.utils.AppConstant.LAT_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.LON_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.MESSAGE_KEY;
import static box.chronos.userk.brain.utils.AppConstant.METHOD_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.OFF_DESC_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.OFF_ID_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.OFF_NAME_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.OFF_PIC_ID_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.OFF_PIC_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.OFF_PIC_PATH_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.PAGE_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.PRICE_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.ROME_COORD_LAT;
import static box.chronos.userk.brain.utils.AppConstant.ROME_COORD_LON;
import static box.chronos.userk.brain.utils.AppConstant.TEN_KM_BOUND;
import static box.chronos.userk.brain.utils.AppConstant.ONE_RESP;
import static box.chronos.userk.brain.utils.AppConstant.SESSION_KEY_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.SUCCESS_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.TIMER_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.TOT_PAGES_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.USERID_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.WORLD_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.ZERO_RESP;

/**
 * Created by ChronosTeam on 27/02/2017.
 */
public class OffersListFragment extends Fragment {
    private static final String TAG = OffersListFragment.class.getSimpleName();
    private OfferAdapter adapter;
    private List<Offer> offerList = new ArrayList<Offer>();
    ArrayList<OffersResponse> arrayListNotification;
    private UserSharedPreference sharePrefs;
    private ImageView glideHeader;
    private RecyclerView recyclerView;
    private int pages = 1, maxPages = 2; // must be 2
    private String cat, world;
    private boolean loading = true, searching = false;
    LinearLayoutManager mLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private SearchView searchView;

    public OffersListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    /*
    @Override
    public void onResume(){
        super.onResume();

        setFields();
        //offerList.clear();
        requestAllGeoOffers();
    }
    */

    private void setFields() {
        arrayListNotification = new ArrayList<>();
        adapter = new OfferAdapter(getActivity(), offerList,recyclerView);
        //rv_notification_list.setAdapter(dataAdapterNotification);
        //offerList.clear();
        adapter.notifyDataSetChanged();
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.offers_list_main, container, false);

        sharePrefs = AppController.getPreference();


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_offer);
        recyclerView.setHasFixedSize(true);


        offerList = new ArrayList<>();
        adapter = new OfferAdapter(getActivity(), offerList, recyclerView);


        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, VideoUtility.dpToPx(10,getActivity()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        retrieveData();
        prepareOffers();

        ((MainActivity) getActivity()).requestForGps();
        //triggerTutorial(rootView);

        recyclerView.addOnItemTouchListener(

                new RecycleItemClickListener(getActivity(), new RecycleItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Offer lic = (Offer) offerList.get(position);
                        Log.d("YOLO", "Lincence: " + lic.getTitle() + " clicked");

                        // Create new fragment and transaction

                        Toast.makeText(getActivity(),"Offer",Toast.LENGTH_SHORT);
                        Intent i = new Intent(getActivity(),OfferPage.class);

                        i.putExtra("Offer",offerList.get(position));
                        if (lic.hasPicture()) {
                            List<String> list = new ArrayList<>();
                            for (int j = 0; j < lic.getAvailablePictures().size(); j++) {
                                Iterator it =lic.getAvailablePictures().entrySet().iterator();
                                while (it.hasNext()) {
                                    Map.Entry pair = (Map.Entry)it.next();
                                    System.out.println(pair.getKey() + " = " + pair.getValue());
                                    list.add((String) pair.getValue());
                                }
                                i.putStringArrayListExtra("pictures", (ArrayList<String>) list);

                            }
                        }
                        startActivity(i);

                    }
                }

                )
        );


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount && totalItemCount >= Integer.valueOf(sharePrefs.getMaxOfferView()))
                        {
                            loading = false;
                            Log.v("...", "Last Item Wow !");

                            pages++;
                            requestAllGeoOffers(pages);
                        }
                    }
                }
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Offerte");


        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);

        return rootView;
    }

    /**
     * Retrieve intent data from activity
     */
    public void retrieveData(){
        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("cat")) {
            cat = getArguments().getString("cat");
        }

        if (arguments != null && arguments.containsKey("world")) {
            world  = getArguments().getString("world");
        }
    }

    /**
     * Adding few cities for testing -> Implement dynamic loading
     */
    private void prepareOffers() {
        if (Includes.staticContent) {
            adapter.notifyDataSetChanged();
        } else {
            requestAllGeoOffers(pages);
            adapter.notifyDataSetChanged();
        }
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    // request for all notifications
    private void requestAllGeoOffers(int page) {
        Map<String, String> pairs = new HashMap<>();
        Boolean b = sharePrefs.getIsAnonymous();
        if (b != null && b){
            pairs.put(METHOD_PARAM, GET_OFFERS_METHOD_ANON);
        } else {
            pairs.put(METHOD_PARAM, GET_OFFERS_METHOD);
        }
        pairs.put(USERID_PARAM, sharePrefs.getUserId());
        pairs.put(SESSION_KEY_PARAM, sharePrefs.getSessionKey());
        // If world not empty
        if (world != null && !world.equals("")) {
            //pairs.put(WORLD_PARAM, SIX_KM_BOUND);
        }
        pairs.put(PAGE_PARAM, Integer.toString(page));

        pairs.put(WORLD_PARAM, TEN_KM_BOUND);


        if (sharePrefs.getLatitude().isEmpty())
            pairs.put(LAT_PARAM, ROME_COORD_LAT);
        else
            pairs.put(LAT_PARAM, sharePrefs.getLatitude());

        if (sharePrefs.getLongitude().isEmpty())
            pairs.put(LON_PARAM, ROME_COORD_LON);
        else
            pairs.put(LON_PARAM, sharePrefs.getLongitude());

        if (cat != null && !cat.equals("")) {
            pairs.put(CAT_ID_PARAM, cat);
        } else {
            if (all_categories)
                pairs.put(CAT_ID_PARAM, ALL_CATS);
            else
                pairs.put(CAT_ID_PARAM, sharePrefs.getSelectedCatrgory());
        }

        RestInteraction interaction = new RestInteraction(getActivity());
        interaction.setCallBack(new IAsyncResponse() {
            @Override
            public void onRestInteractionResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (pages==1)
                        offerList.clear();
                    if (response != null) {
                        if (object.getString(SUCCESS_PARAM).equalsIgnoreCase(ONE_RESP)) {
                            maxPages = Integer.valueOf(object.getString(TOT_PAGES_PARAM));
                            getJsonData(object);
                            // #bea
                            loading = false;
                        } else {
                            Utility.showAlertDialog(getActivity(), object.getString(MESSAGE_KEY));
                        }
                    } else {
                        Utility.showAlertDialog(getActivity(), object.getString(MESSAGE_KEY));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRestInteractionError(String message) {
                Utility.showAlertDialog(getActivity(), message);
            }
        });
        interaction.makeServiceRequest(AppUrls.COMMON_URL, pairs, TAG, "Dialog");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_sort_distance_asc:
                Toast.makeText(this.getActivity(),"Distanza crescente",Toast.LENGTH_SHORT);
                Log.d("OffersList","Sort Distance Asc");
                ListUtilities.sortOffersDistanceAsc(offerList,adapter);
                // Do Activity menu item stuff here
                return true;


            case R.id.action_sort_distance_desc:
                Toast.makeText(this.getActivity(),"Distanza decrescente",Toast.LENGTH_SHORT);
                Log.d("OffersList","Sort Distance Desc");
                ListUtilities.sortOffersDistanceDesc(offerList,adapter);
                // Not implemented here
                return false;


            case R.id.action_sort_price_asc:
                Toast.makeText(this.getActivity(),"Prezzo crescente",Toast.LENGTH_SHORT);
                Log.d("OffersList","Sort Price Asc");
                ListUtilities.sortOffersPriceAsc(offerList,adapter);
                // Do Activity menu item stuff here
                return true;
            case R.id.action_sort_price_desc:
                Toast.makeText(this.getActivity(),"Prezzo decrescente",Toast.LENGTH_SHORT);
                Log.d("OffersList","Sort Price Desc");
                ListUtilities.sortOffersPriceDesc(offerList,adapter);
                // Not implemented here
                return false;
            default:
                break;
        }

        return false;
    }

    // get json data
    private void getJsonData(JSONObject object) {
        try {
            JSONObject jsonRootObject = new JSONObject(String.valueOf(object));
            JSONArray jsonArray = jsonRootObject.optJSONArray(DATA_RESP);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Offer ld = new Offer();

                ld.setCategoryid(jsonObject.getString(CAT_ID_PARAM).toString());
                ld.setCategory(jsonObject.getString(CAT_NAME).toString());

                JSONArray picArray = jsonObject.optJSONArray(OFF_PIC_PARAM);
                if (picArray.length()>0) {
                    HashMap<String, String> picMap=new LinkedHashMap<>();
                    for (int j = 0; j < picArray.length(); j++) {
                        JSONObject picObj = picArray.getJSONObject(j);
                        String id = picObj.getString(OFF_PIC_ID_PARAM);
                        String picPath = picObj.getString(OFF_PIC_PATH_PARAM);
                        picMap.put(id,picPath);
                    }
                    ld.setAvailablePictures(picMap);
                }
                ld.setCategoryphoto(jsonObject.getString(CAT_PHOTO_DEF).toString());
                ld.setPhotoactive(jsonObject.getString(CAT_PHOTO_ACTIVE).toString());
                ld.setId_offer(jsonObject.getString(OFF_ID_PARAM).toString());
                ld.setBusinessname(jsonObject.getString(BUSINESSNAME_PARAM).toString());
                ld.setLatitude(jsonObject.getString(LAT_PARAM).toString());
                ld.setTitle(jsonObject.getString(OFF_NAME_PARAM).toString());
                ld.setLongitude(jsonObject.getString(LON_PARAM).toString());
                ld.setBusinessphone(jsonObject.getString(BUSINESS_PHONE_PARAM).toString());
                ld.setBusinessaddress(jsonObject.getString(BUSINESS_ADD_PARAM).toString());
                ld.setOfferdescription(jsonObject.getString(OFF_DESC_PARAM).toString());
                ld.setDistance(jsonObject.getString(DISTANCE_PARAM).toString());
                ld.setTimeout(jsonObject.getString(TIMER_PARAM).toString());
                ld.setPrice(jsonObject.getString(PRICE_PARAM).toString());
                ld.setDiscount(jsonObject.getString(DISCOUNT_PARAM).toString());
                if (jsonObject.getString(DISCOUNT_PARAM).toString().equals("") || jsonObject.getString(DISCOUNT_PARAM).toString().equals(ZERO_RESP))
                    Log.d(TAG, "Skipping article");
                else
                    offerList.add(ld);
            }
            // Default order by distance
            if (offerList.size()>0) {
                Collections.sort(offerList, new Comparator<Offer>() {
                    @Override
                    public int compare(Offer o1, Offer o2) {
                        return Float.valueOf(o1.getDistance()).compareTo(Float.valueOf(o2.getDistance()));
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate menu resource file.
        inflater.inflate(R.menu.offer_list_menu, menu);


        final MenuItem searchItem = menu.findItem(R.id.action_search_all);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener(){

                    public boolean onQueryTextChange(String newText){
                        // Text has changed. Apply filtering?

                        if (newText == null || newText.isEmpty() || newText.equals("")){
                            Log.d(TAG,"dismissing view");
                            adapter.restoreList();

                            // Minimize Search view
                            if (searching) {
                                //MenuItemCompat.collapseActionView(searchItem);
                                if (!searchView.isIconified())
                                    searchView.setIconified(true);
                            }

                            searching = false;
                        }  else {

                            searching = true;
                            adapter.getFilter().filter(newText.toString());
                        }

                        Log.d("SEARCH feature","searching");
                        return false;
                    }


                    public boolean onQueryTextSubmit(String query){
                        searching = true;
                        // Perform the final search
                        Log.d("SEARCH feature","final search submitted");


                        adapter.getFilter().filter(query.toString());
                        //offerListTemp = ListUtilities.searchArticlesString(offerList,query);

                        return  false;
                    }
                }
        );

        searchView.setOnCloseListener(new SearchView.OnCloseListener(){
            public boolean onClose(){

                //adapter = new ArticleAdapter(getActivity(), offerList, recyclerView);
                //adapter.notifyDataSetChanged();



                //requestAllArticles(pages);
                //if (!searchView.isIconified()) {
                //    searchView.setIconified(true);
                //}

                //searching = false;
                return true;
            }
        });
    }

}