package box.chronos.userk.brain.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import box.chronos.userk.brain.R;
import box.chronos.userk.brain.activities.ArticlePage;
import box.chronos.userk.brain.activities.MainActivity;
import box.chronos.userk.brain.adapters.ArticleAdapter;
import box.chronos.userk.brain.callbacks.IAsyncResponse;
import box.chronos.userk.brain.objects.Offer;
import box.chronos.userk.brain.objects.payloads.OffersResponse;
import box.chronos.userk.brain.serverRequest.AppUrls;
import box.chronos.userk.brain.serverRequest.RestInteraction;
import box.chronos.userk.brain.settings.Includes;
import box.chronos.userk.brain.utils.application.AppController;
import box.chronos.userk.brain.utils.Lists.ListUtilities;
import box.chronos.userk.brain.utils.Lists.RecycleItemClickListener;
import box.chronos.userk.brain.utils.application.UserSharedPreference;
import box.chronos.userk.brain.utils.Utility;
import box.chronos.userk.brain.utils.video.VideoUtility;

import static box.chronos.userk.brain.settings.Includes.all_categories;
import static box.chronos.userk.brain.utils.constants.AppConstant.ALL_CATS;
import static box.chronos.userk.brain.utils.constants.AppConstant.BUSINESSNAME_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.BUSINESS_ADD_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.BUSINESS_PHONE_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.CAT_ID_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.CAT_NAME;
import static box.chronos.userk.brain.utils.constants.AppConstant.CAT_PHOTO_ACTIVE;
import static box.chronos.userk.brain.utils.constants.AppConstant.CAT_PHOTO_DEF;
import static box.chronos.userk.brain.utils.constants.AppConstant.DATA_RESP;
import static box.chronos.userk.brain.utils.constants.AppConstant.DISCOUNT_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.DISTANCE_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.GET_ARTICLES_METHOD;
import static box.chronos.userk.brain.utils.constants.AppConstant.GET_ARTICLES_METHOD_ANON;
import static box.chronos.userk.brain.utils.constants.AppConstant.LAT_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.LON_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.MESSAGE_KEY;
import static box.chronos.userk.brain.utils.constants.AppConstant.METHOD_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.OFF_DESC_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.OFF_ID_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.OFF_NAME_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.OFF_PIC_ID_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.OFF_PIC_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.OFF_PIC_PATH_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.ONE_RESP;
import static box.chronos.userk.brain.utils.constants.AppConstant.PAGE_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.PRICE_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.ROME_COORD_LAT;
import static box.chronos.userk.brain.utils.constants.AppConstant.ROME_COORD_LON;
import static box.chronos.userk.brain.utils.constants.AppConstant.SESSION_KEY_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.SUCCESS_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.TEN_KM_BOUND;
import static box.chronos.userk.brain.utils.constants.AppConstant.TIMER_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.TOT_PAGES_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.USERID_PARAM;
import static box.chronos.userk.brain.utils.constants.AppConstant.WORLD_PARAM;
import static box.chronos.userk.brain.ux.AppMessage.ARTICLE_TITLE;

/**
 * Created by ChronosTeam on 27/02/2017.
 */
public class ArticleListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = OffersListFragment.class.getSimpleName();
    private ArticleAdapter adapter;
    private List<Offer> offerList = new ArrayList<Offer>(), offerListTemp = new ArrayList<Offer>();
    ArrayList<OffersResponse> arrayListNotification;
    private UserSharedPreference sharePrefs;
    private ImageView glideHeader;
    private RecyclerView recyclerView;
    private String cat, world;
    private int pages=1, maxPages = 2; // must be 2
    private boolean loading = true, searching = false;
    LinearLayoutManager mLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private CircularProgressView progressView;
    private SearchView searchView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public ArticleListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.offers_list_main, container, false);

        sharePrefs = AppController.getPreference();

        //progressView = (CircularProgressView) rootView.findViewById(R.id.progress_view);
        //progressView.startAnimation();


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_offer);
        recyclerView.setHasFixedSize(true);

        offerList = new ArrayList<>();
        adapter = new ArticleAdapter(getActivity(), offerList, recyclerView);


        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ArticleListFragment.GridSpacingItemDecoration(2, VideoUtility.dpToPx(10,getActivity()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        // SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container_offers);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);



        retrieveData();
        //prepareOffers();

        ((MainActivity) getActivity()).requestForGps();

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                loadRecyclerViewData();
            }
        });

        recyclerView.addOnItemTouchListener(

                new RecycleItemClickListener(getActivity(), new RecycleItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Offer lic = adapter.getItem(position);
                        Log.d("YOLO", "Licence: " + lic.getTitle() + " clicked");

                        // Create new fragment and transaction
                        Toast.makeText(getActivity(),"Offer",Toast.LENGTH_SHORT);
                        Intent i = new Intent(getActivity(), ArticlePage.class);

                        i.putExtra("Offer",adapter.getItem(position));
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

                    if (loading && !searching)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount /*&& totalItemCount >= Integer.valueOf(sharePrefs.getMaxOfferView())*/) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            if (pages < maxPages) {
                                pages++;
                                requestAllArticles(pages);
                            }
                        }
                    }
                }
            }
        });

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(ARTICLE_TITLE);


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
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {

        // Fetching data from server
        loadRecyclerViewData();

        pages = 1;
        offerList.clear();
        prepareOffers();
    }

    private void loadRecyclerViewData() {
        // Showing refresh animation before making http call
        mSwipeRefreshLayout.setRefreshing(true);
        prepareOffers();

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


    /**
     * Adding few cities for testing -> Implement dynamic loading
     */
    private void prepareOffers() {
        if (Includes.staticContent) {
            adapter.notifyDataSetChanged();
        } else {
            requestAllArticles(pages);
            adapter.notifyDataSetChanged();
        }
    }


    // request for all notifications
    private void requestAllArticles(final int page) {
        Map<String, String> pairs = new HashMap<>();

        Boolean a = sharePrefs.getIsAnonymous();
        if (a != null && a) {
            pairs.put(METHOD_PARAM, GET_ARTICLES_METHOD_ANON);
        } else {
            pairs.put(METHOD_PARAM, GET_ARTICLES_METHOD);
        }

        pairs.put(USERID_PARAM, sharePrefs.getUserId());
        pairs.put(SESSION_KEY_PARAM, sharePrefs.getSessionKey());
        // If world not empty
        if (world != null && !world.equals("")) {
            //pairs.put(WORLD_PARAM, SIX_KM_BOUND);
        }

        pairs.put(WORLD_PARAM, TEN_KM_BOUND);
        pairs.put(PAGE_PARAM, Integer.toString(page));
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
                            //progressView.stopAnimation();
                            //progressView.setVisibility(View.INVISIBLE);

                            loading = true;

                            if (mSwipeRefreshLayout.isRefreshing())
                                mSwipeRefreshLayout.setRefreshing(false);
                        } else {
                            Utility.showAlertDialog(getActivity(), object.getString(MESSAGE_KEY));

                            if (mSwipeRefreshLayout.isRefreshing())
                                mSwipeRefreshLayout.setRefreshing(false);
                        }
                    } else {
                        Utility.showAlertDialog(getActivity(), object.getString(MESSAGE_KEY));

                        if (mSwipeRefreshLayout.isRefreshing())
                            mSwipeRefreshLayout.setRefreshing(false);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRestInteractionError(String message) {
                Utility.showAlertDialog(getActivity(), message);

                if (mSwipeRefreshLayout.isRefreshing())
                    mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        interaction.makeServiceRequest(AppUrls.COMMON_URL, pairs, TAG, "Dialog");
    }


    // get json data
    private void getJsonData(JSONObject object) {
        try {
            JSONObject jsonRootObject = new JSONObject(String.valueOf(object));

            JSONArray jsonArray = jsonRootObject.optJSONArray(DATA_RESP);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Offer ld = new Offer();


                if (jsonObject.has(CAT_ID_PARAM))
                    ld.setCategoryid(jsonObject.getString(CAT_ID_PARAM).toString());

                if (jsonObject.has(CAT_NAME))
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

                if (jsonObject.has(CAT_PHOTO_DEF))
                    ld.setCategoryphoto(jsonObject.getString(CAT_PHOTO_DEF).toString());

                if (jsonObject.has(CAT_PHOTO_ACTIVE))
                    ld.setPhotoactive(jsonObject.getString(CAT_PHOTO_ACTIVE).toString());

                if (jsonObject.has(OFF_ID_PARAM))
                    ld.setId_offer(jsonObject.getString(OFF_ID_PARAM).toString());

                if (jsonObject.has(BUSINESSNAME_PARAM))
                    ld.setBusinessname(jsonObject.getString(BUSINESSNAME_PARAM).toString());

                if (jsonObject.has(LAT_PARAM))
                    ld.setLatitude(jsonObject.getString(LAT_PARAM).toString());

                if (jsonObject.has(OFF_NAME_PARAM))
                    ld.setTitle(jsonObject.getString(OFF_NAME_PARAM).toString());

                if (jsonObject.has(LON_PARAM))
                    ld.setLongitude(jsonObject.getString(LON_PARAM).toString());

                if (jsonObject.has(BUSINESS_PHONE_PARAM))
                    ld.setBusinessphone(jsonObject.getString(BUSINESS_PHONE_PARAM).toString());

                if (jsonObject.has(BUSINESS_ADD_PARAM))
                    ld.setBusinessaddress(jsonObject.getString(BUSINESS_ADD_PARAM).toString());

                if (jsonObject.has(OFF_DESC_PARAM))
                    ld.setOfferdescription(jsonObject.getString(OFF_DESC_PARAM).toString());

                if (jsonObject.has(DISTANCE_PARAM))
                    ld.setDistance(jsonObject.getString(DISTANCE_PARAM).toString());

                if (jsonObject.has(TIMER_PARAM))
                    ld.setTimeout(jsonObject.getString(TIMER_PARAM).toString());

                if (jsonObject.has(PRICE_PARAM))
                    ld.setPrice(jsonObject.getString(PRICE_PARAM).toString());

                if (jsonObject.has(DISCOUNT_PARAM))
                    ld.setDiscount(jsonObject.getString(DISCOUNT_PARAM).toString());


                offerList.add(ld);
            }
            // Default order by distance
            if (offerList.size()>0) {
                //ListUtilities.sortArticlesDistanceAsc(offerList,adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_sort_distance_asc:
                Toast.makeText(this.getActivity(),"Distanza crescente",Toast.LENGTH_SHORT);
                Log.d("OffersList","Sort Distance Asc");
                ListUtilities.sortArticlesDistanceAsc(offerList,adapter);
                // Do Activity menu item stuff here
                return true;


            case R.id.action_sort_distance_desc:
                Toast.makeText(this.getActivity(),"Distanza decrescente",Toast.LENGTH_SHORT);
                Log.d("OffersList","Sort Distance Desc");
                ListUtilities.sortArticlesDistanceDesc(offerList,adapter);
                // Not implemented here
                return false;


            case R.id.action_sort_price_asc:
                Toast.makeText(this.getActivity(),"Prezzo crescente",Toast.LENGTH_SHORT);
                Log.d("OffersList","Sort Price Asc");
                ListUtilities.sortArticlesPriceAsc(offerList,adapter);
                // Do Activity menu item stuff here
                return true;
            case R.id.action_sort_price_desc:
                Toast.makeText(this.getActivity(),"Prezzo decrescente",Toast.LENGTH_SHORT);
                Log.d("OffersList","Sort Price Desc");
                ListUtilities.sortArticlesPriceDesc(offerList,adapter);
                // Not implemented here
                return false;
            default:
                break;
        }

        return false;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
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