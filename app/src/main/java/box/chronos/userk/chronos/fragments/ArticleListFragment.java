package box.chronos.userk.chronos.fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import box.chronos.userk.chronos.R;
import box.chronos.userk.chronos.activities.OfferPage;
import box.chronos.userk.chronos.adapters.ArticleAdapter;
import box.chronos.userk.chronos.objects.Offer;
import box.chronos.userk.chronos.settings.Includes;
import box.chronos.userk.chronos.utils.RecycleItemClickListener;
import box.chronos.userk.chronos.utils.VideoUtility;

/**
 * Created by ChronosTeam on 27/02/2017.
 */
public class ArticleListFragment extends Fragment {
    private ArticleAdapter adapter;
    private List<Offer> offerList = new ArrayList<Offer>();
    private ImageView glideHeader;

    public ArticleListFragment() {
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
        View rootView = inflater.inflate(R.layout.offers_list_main, container, false);


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_licences);
        recyclerView.setHasFixedSize(true);

        offerList = new ArrayList<>();
        adapter = new ArticleAdapter(getActivity(), offerList, recyclerView);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, VideoUtility.dpToPx(10,getActivity()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareOffers();

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
                        startActivity(i);

                        /*
                        OfferFragment newLoc = new OfferFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack
                        transaction.replace(R.id.fragment_container, newLoc,"location");
                        transaction.addToBackStack(null);

                        // Commit the transaction
                        transaction.commit();
                        */
                    }
                }

                )
        );


        return rootView;
    }



    /**
     * Adding few cities for testing -> Implement dynamic loading
     */
    private void prepareOffers() {
        if (Includes.staticContent) {
            int[] covers = new int[]{
                    R.drawable.giacca_u,
                    R.drawable.giacca_d,
                    R.drawable.drone,
                    R.drawable.scarpa_d};

            Offer a = new Offer("1","Vogstyle Uomo Cappotto Trench Giacca", covers[0], "Abbigliamento Uomo");
            a.setShopId("1");
            a.setDescription("Modello: Altezza:185cm,Peso:73 kg\n" +
                    "Cotone\n" +
                    "XL:Lunghezza:28.9\",Manica:25.6\",Busto:48\",Spalla:18.5\"\n" +
                    "giacca blouson\n" +
                    "Materiale:90% Cotone,10% Altri ");
            a.setPrice("240€");

            offerList.add(a);

            Offer a1 = new Offer("2","DELEY Autunno Giacca Slim Fit Elegante Ufficio ", covers[1], "Abbigliamento Donna");
            a1.setShopId("1");
            a1.setDescription("\n" +
                    "Stile unico, creare l'illusione di curve mozzafiato\n" +
                    "Elegante, capace e di esperienza\n" +
                    "Cotone E Poliestere\n" +
                    "Confortevole e morbido, materiale, moda, lo stile europeo\n" +
                    "Adatta per: Sping/Autunno/Inverno\n" +
                    "Dimensione: Formato asiatico è più piccolo di formato di UE. Si prega di controllare i dettagli dimensioni nella descrizione qui sotto\n");
            a1.setPrice("135€");
            a1.setDiscount("12%");
            offerList.add(a1);

            Offer a2 = new Offer("3","Drone Syma X5SW 4CH 2.4G 6-Asse Giroscopio RC Wifi FPV ", covers[2], "Tech");
            a2.setShopId("2");
            a2.setDescription("\n" +
                    "Il velivolo FPV può volare indoor o outdoor,\n"+
                    "3D rolling: 360 gradi di sostegno\n" +
                    "Versione Wi-Fi, foto e video, supporto agli utenti IOS/Android\n" +
                    "Con 0.3 MP fotocamera HD.\n" +
                    "Pronto a volare\n");
            a2.setPrice("455€");
            a2.setDiscount("21%");
            offerList.add(a2);

            Offer a3 = new Offer("4","Decollete in pelle bianca", covers[3], "Scarpe Donna");
            a3.setPrice("59,90€");
            a3.setShopId("1");
            a3.setDiscount("10%");
            a3.setDescription("Altri colori disponibili" +
                    "\n" +
                    "- sintetico soletta" +
                    "\n" +
                    "- sintetico suola" +
                    "\n" +
                    "- sintetico fodera" +
                    "\n" +
                    "- 9,5 centimetri altezza tacco" +
                    "\n" +
                    "- 9 centimetri larghezza suola" +
                    "\n" +
                    "* 5 cm altezza albero" +
                    "\n" +
                    "* misure del prodotto sono state scattate con dimensioni della uk 7. si prega di notare che le misure di cui sopra possono variare in base alle dimensioni. ");
            offerList.add(a3);

            adapter.notifyDataSetChanged();
        } else {
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




}