package box.chronos.userk.brain.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import box.chronos.userk.brain.R;
import box.chronos.userk.brain.adapters.CategoryAdapter;
import box.chronos.userk.brain.callbacks.IAsyncResponse;
import box.chronos.userk.brain.objects.Category;
import box.chronos.userk.brain.serverRequest.AppUrls;
import box.chronos.userk.brain.serverRequest.RestInteraction;
import box.chronos.userk.brain.settings.Includes;
import box.chronos.userk.brain.utils.AppController;
import box.chronos.userk.brain.utils.BlurBuilder;
import box.chronos.userk.brain.utils.RecycleItemClickListener;
import box.chronos.userk.brain.utils.UserSharedPreference;
import box.chronos.userk.brain.utils.Utility;
import box.chronos.userk.brain.utils.VideoUtility;

import static box.chronos.userk.brain.settings.Includes.show_background_categories;
import static box.chronos.userk.brain.utils.AppConstant.CAT_ID;
import static box.chronos.userk.brain.utils.AppConstant.CAT_NAME;
import static box.chronos.userk.brain.utils.AppConstant.CAT_PHOTO_ACTIVE;
import static box.chronos.userk.brain.utils.AppConstant.CAT_PHOTO_DEF;
import static box.chronos.userk.brain.utils.AppConstant.CAT_SELECTED;
import static box.chronos.userk.brain.utils.AppConstant.GET_CAT_METHOD;
import static box.chronos.userk.brain.utils.AppConstant.GET_CAT_METHOD_ANON;
import static box.chronos.userk.brain.utils.AppConstant.MESSAGE_KEY;
import static box.chronos.userk.brain.utils.AppConstant.METHOD_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.ONE_RESP;
import static box.chronos.userk.brain.utils.AppConstant.SESSION_KEY_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.SUCCESS_PARAM;
import static box.chronos.userk.brain.utils.AppConstant.USERID_PARAM;

/**
 * Created by ChronosTeam on 27/02/2017.
 */
public class CategoriesGridFragment extends Fragment {
    private static final String TAG = CategoriesGridFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private UserSharedPreference sharePrefs;
    private CategoryAdapter adapter;
    private String commaValues;
    private ArrayList<Category> catList;


    public CategoriesGridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.category_view, container, false);

        sharePrefs = AppController.getPreference();


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_categories);
        //recyclerView.setHasFixedSize(false);

        catList = new ArrayList<>();
        adapter = new CategoryAdapter(getActivity(), catList, recyclerView);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, VideoUtility.dpToPx(10,getActivity()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareCategories();

        //triggerTutorial(rootView);

        recyclerView.addOnItemTouchListener(
                new RecycleItemClickListener(getActivity(), new RecycleItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                    /*
                     * Get id of licence handle item click
                     */
                        Category cat = (Category) catList.get(position);
                        Log.d("Category", "Category: " + cat.getCat_name() + " clicked");
                        String id = cat.getCat_id();
                        //String flag_color = cat.getFlag_dark();

                        Bundle data = new Bundle();
                        data.putString("cat", id);
                        data.putString("world", "1");

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        OffersListFragment fragment = new OffersListFragment();

                        fragment.setArguments(data);
                        fragmentTransaction.replace(R.id.fragment_container, fragment,"Categories");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        /*
                        Intent i = new Intent(getActivity(), SingleCategoryQuizzes.class);
                        i.putExtra(CAT_ID,id);
                        i.putExtra(CAT_COLOR_FLAG,flag_color);
                        startActivity(i);
                        */
                    }
                })
        );


        if (show_background_categories) {
            // Setup Background
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test9);
            Bitmap image = BlurBuilder.blur(getActivity().getApplicationContext(), bm);
            rootView.setBackground(new BitmapDrawable(getActivity().getResources(), image));
        }
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Categorie");


        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        //setFields();
        //prepareCategories();
    }


    private void setFields() {
        catList = new ArrayList<>();
        adapter = new CategoryAdapter(getActivity(),catList);
        recyclerView.setAdapter(adapter);
        //catList.clear();
        adapter.notifyDataSetChanged();
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
    private void prepareCategories() {
        if (Includes.staticContent) {
        } else {
            requestAllCategories();
            // Retrieve from activity
            // Get Licences

            /*
            String a = getActivity().getIntent().getStringExtra(TOT_LICENCES);
            for (int j = 0; j < Integer.valueOf(a);j++) {
                Licence licx = (Licence) getActivity().getIntent().getParcelableExtra(LICENCE_LIST + String.valueOf(j));
                licenceList.add(licx);
            }

            adapter.notifyDataSetChanged();
            */
        }
    }

    // request for all notifications
    private void requestAllCategories() {
        Map<String, String> pairs = new HashMap<>();

        Boolean b = sharePrefs.getIsAnonymous();
        if (b != null && b){
            pairs.put(METHOD_PARAM, GET_CAT_METHOD_ANON);
        } else {
            pairs.put(METHOD_PARAM, GET_CAT_METHOD);
        }
        pairs.put(USERID_PARAM, sharePrefs.getUserId());
        pairs.put(SESSION_KEY_PARAM, sharePrefs.getSessionKey());

        RestInteraction interaction = new RestInteraction(getActivity());
        interaction.setCallBack(new IAsyncResponse() {
            @Override
            public void onRestInteractionResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    catList.clear();
                    if (response != null) {
                        if (object.getString(SUCCESS_PARAM).equalsIgnoreCase(ONE_RESP)) {
                            getJsonData(object);
                        } else {
                            Utility.showAlertDialog(getActivity(), object.getString(MESSAGE_KEY));
                        }
                    } else {
                        Utility.showAlertDialog(getActivity(), object.getString("message"));
                    }
                    //dataAdapterNotification.setData(arrayListNotification);
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
        interaction.makeServiceRequest(AppUrls.COMMON_URL, pairs, TAG, "Categories");
    }

    // get json data
    private void getJsonData(JSONObject object) {
        try {
            JSONObject jsonRootObject = new JSONObject(String.valueOf(object));
            JSONArray jsonArray = jsonRootObject.optJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Category cat = new Category();
                if (jsonObject.has(CAT_ID))
                    cat.setCat_id(jsonObject.getString(CAT_ID).toString());
                if (jsonObject.has(CAT_NAME))
                    cat.setCat_name(jsonObject.getString(CAT_NAME).toString());
                if (jsonObject.has(CAT_PHOTO_DEF))
                    cat.setCat_photo(jsonObject.getString(CAT_PHOTO_DEF).toString());
                if (jsonObject.has(CAT_PHOTO_ACTIVE))
                    cat.setCat_photo_active(jsonObject.getString(CAT_PHOTO_ACTIVE).toString());
                if (jsonObject.has(CAT_SELECTED))
                    cat.setIs_selected(jsonObject.getString(CAT_SELECTED).toString());
                catList.add(cat);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}