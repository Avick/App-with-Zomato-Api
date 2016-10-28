package kiwi.castle.avick.com.kiwiassignment.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import kiwi.castle.avick.com.kiwiassignment.Activity.BaseActivity;
import kiwi.castle.avick.com.kiwiassignment.Adapter.SearchListAdapter;
import kiwi.castle.avick.com.kiwiassignment.Models.BaseVO;
import kiwi.castle.avick.com.kiwiassignment.Models.CuisineList;
import kiwi.castle.avick.com.kiwiassignment.Models.CuisineModel;
import kiwi.castle.avick.com.kiwiassignment.Models.ErrorVO;
import kiwi.castle.avick.com.kiwiassignment.Models.RealmRestaurant;
import kiwi.castle.avick.com.kiwiassignment.Models.Restaurant;
import kiwi.castle.avick.com.kiwiassignment.Models.SearchResultModel;
import kiwi.castle.avick.com.kiwiassignment.Network.NetworkDAO;
import kiwi.castle.avick.com.kiwiassignment.R;
import kiwi.castle.avick.com.kiwiassignment.Utils.BasicUtils;
import kiwi.castle.avick.com.kiwiassignment.Utils.EndlessRecyclerOnScrollListener;

import static android.app.Activity.RESULT_OK;

/**
 * Created by avick on 10/24/16.
 */

public class SearchFragment extends BaseFragment implements View.OnClickListener, SearchListAdapter.ClickItemListener {


    int PLACE_PICKER_REQUEST = 1;

    private boolean hasNextCuisine = false;
    private boolean isCuisine = true;
    RecyclerView mRecyclerView;
    SearchListAdapter mAdapter;
    EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
    ArrayList<CuisineModel> cuisines;
    ArrayList<BaseVO> mDataSet;
    int counter = 0;
    boolean isFirsttime = true;
    ImageView searchIcon;
    private Realm realm;
    TextView txtFavourite, txtemptyState;
    String q = null;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_result, container, false);
        init(view);
        realm = Realm.getDefaultInstance();
        //view.setVisibility(View.GONE);
        //return addToBaseFragment(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((BaseActivity)getActivity()).setSubHeaderText("in " + BasicUtils.getLastLocationName(getActivity()));
        if(cuisines == null) {
            mDataSet = null;
            counter =0;
            showFragmentProgressBar();
            NetworkDAO.getInstance(getActivity().getString(R.string.zomato_api_endpoints), getActivity()).getCuisines(BasicUtils.getLastLocationCoordinate(getActivity())[0], BasicUtils.getLastLocationCoordinate(getActivity())[1]);
        } else if( mDataSet != null) {
            mAdapter = new SearchListAdapter(mDataSet, getActivity(),this);
            mRecyclerView.setAdapter(mAdapter);
        } else if(mDataSet == null){
            counter = 0;
            getResult(counter);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_favourites:
                getFragmentManager().beginTransaction()
                        //.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .setCustomAnimations(R.animator.enter_right, R.animator.exit_left, R.animator.enter_left, R.animator.exit_right)
                        .replace(R.id.lnr_data_container, OfflineFragment.newInstance(), OfflineFragment.class.getSimpleName()).addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public void onClickItem(final Restaurant restaurant) {
        if(realm.isEmpty() || !checkIfExists(restaurant.getId())) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmRestaurant rest = realm.createObject(RealmRestaurant.class);
                    rest.setId(restaurant.getId());
                    rest.setName(restaurant.getName());
                    rest.setCurrency(restaurant.getCurrency());
                    rest.setRating(restaurant.getUserRating().getAggregateRating());
                    rest.setAverageCostForTwo(restaurant.getAverageCostForTwo());
                    rest.setCuisines(restaurant.getCuisines());
                }
            });
        }
    }

    public void init(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_search_result);
        txtFavourite = (TextView)view.findViewById(R.id.txt_favourites);
        txtemptyState = (TextView)view.findViewById(R.id.empty_state);
        txtFavourite.setOnClickListener(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int count) {
                counter++;
                if(isHasNextCuisine()){
                    mAdapter.setHasNextCuisine(true);
                    getResult(counter);
                }
            }
        };
        mRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);

        searchIcon = ((BaseActivity)getActivity()).getImgSearch();
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity)getActivity()).getTxtHeaderView().setVisibility(View.GONE);
                ((BaseActivity)getActivity()).getImgSearch().setVisibility(View.GONE);
                ((BaseActivity)getActivity()).getSearchLayout().setVisibility(View.GONE);
                ((BaseActivity)getActivity()).getImgMap().setVisibility(View.GONE);
                ((BaseActivity)getActivity()).getTxtSubHeader().setVisibility(View.GONE);
                ((BaseActivity)getActivity()).getSearchText().setVisibility(View.VISIBLE);
                ((BaseActivity)getActivity()).getSearchText().setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                //((BaseActivity)getActivity()).getSearchText().clearFocus();
                ((BaseActivity)getActivity()).getSearchText().setFocusable(true);
                ((BaseActivity)getActivity()).getSearchText().requestFocus();
                ((BaseActivity)getActivity()).getSearchText().requestFocusFromTouch();

            }
        });

        ((BaseActivity)getActivity()).getImgMap().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLocationpicker();
            }
        });

        ((BaseActivity)getActivity()).getSearchText().setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // Identifier of the action. This will be either the identifier you supplied,
                        // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                            ((BaseActivity)getActivity()).setHeaderText("Search result for " + v.getText().toString());
                            ((BaseActivity)getActivity()).getTxtHeaderView().setVisibility(View.VISIBLE);
                            ((BaseActivity)getActivity()).getImgSearch().setVisibility(View.VISIBLE);
                            ((BaseActivity)getActivity()).getImgMap().setVisibility(View.VISIBLE);
                            ((BaseActivity)getActivity()).getTxtSubHeader().setVisibility(View.VISIBLE);
                            //((BaseActivity)getActivity()).getSearchLayout().setVisibility(View.);
                            ((BaseActivity)getActivity()).getSearchText().setVisibility(View.GONE);
                            q = v.getText().toString();
                            startSearch();
                            return true;
                        }
                        // Return true if you have consumed the action, else false.
                        return false;
                    }
                });

    }

    public void getResult(int count) {
        NetworkDAO.getInstance(getActivity().getString(R.string.zomato_api_endpoints),
                getActivity()).getSearchResult(BasicUtils.getLastLocationCoordinate(getActivity())[0],
                BasicUtils.getLastLocationCoordinate(getActivity())[1], cuisines.get(count).getCuisine().getId(), q);
    }


    public void onEvent(CuisineList result) {
        //hideFragmentProgressBar();
        isCuisine = false;
        if(result != null) {
            cuisines = result.getCuisines();
            if (cuisines.size() > 0) {
                txtemptyState.setVisibility(View.GONE);
                getResult(counter);
            } else {
                txtemptyState.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        }

    }

    public void onEvent(SearchResultModel result) {
        hideFragmentProgressBar();
//        if(result.getRestaurants().get(0).getRestaurant() == )
        //((BaseActivity)getActivity()).dismissProgressDialog();
        mRecyclerView.setVisibility(View.VISIBLE);
        endlessRecyclerOnScrollListener.setLoading(false);
        updateRecylerView(result);
    }

    public void onEvent(ErrorVO errorVO) {
        //hideFragmentProgressBar();
        ((BaseActivity)getActivity()).dismissProgressDialog();
        if(!isCuisine) {
            counter++;
            getResult(counter);
        } else {
            txtemptyState.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    public boolean isHasNextCuisine() {
        if(counter < cuisines.size()) {
            return true;
        }

        return false;
    }


    public void updateRecylerView(SearchResultModel result) {
        if(result != null && result.getRestaurants() != null && result.getRestaurants().size() > 0) {

            if(mDataSet == null) {
                mDataSet = new ArrayList<>();
            }

            CuisineModel cuisine = cuisines.get(counter);
            mDataSet.add(cuisine);
            for(int i = 0; i <  result.getRestaurants().size(); i++) {
                if(checkIfExists(result.getRestaurants().get(i).getRestaurant().getId())) {
                    result.getRestaurants().get(i).getRestaurant().setSaved(true);
                }
            }
            mDataSet.addAll(result.getRestaurants());
            if(isFirsttime) {
                mAdapter = new SearchListAdapter(mDataSet, getActivity(),this);
                mRecyclerView.setAdapter(mAdapter);
                isFirsttime = false;
            }else  {
                //mAdapter.addAdapter(mDataSet);
                //mAdapter.notifyDataSetChanged();
            }
            if(mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }

        } else if(result != null && result.getRestaurants() != null && result.getRestaurants().size() == 0) {
            counter++;
            //((BaseActivity)getActivity()).showProgressDialog(null, false);
            getResult(counter);
        }
    }



    public void startSearch() {
        mDataSet = null;
        mAdapter = null;
        counter = 0;
        showFragmentProgressBar();
        NetworkDAO.getInstance(getActivity().getString(R.string.zomato_api_endpoints),
                getActivity()).getSearchResult(BasicUtils.getLastLocationCoordinate(getActivity())[0],
                BasicUtils.getLastLocationCoordinate(getActivity())[1], cuisines.get(counter).getCuisine().getId(), q);

        mRecyclerView.setVisibility(View.GONE);
        isFirsttime = true;

    }

    public boolean checkIfExists(String id){

        RealmQuery<RealmRestaurant> query = realm.where(RealmRestaurant.class)
                .equalTo("id", id);

        return query.count() != 0;
    }

    void startLocationpicker(){
        try {

            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
//                String toastMsg = String.format("Place: %s", place.getName());
//                Toast.makeText(get, toastMsg, Toast.LENGTH_LONG).show();
                BasicUtils.setLastLocationName(getActivity(),place.getName().toString());
                BasicUtils.setLastLocationCoordinate(getActivity(), place.getLatLng().latitude + "," + place.getLatLng().longitude);
                cuisines = null;
                mDataSet = null;
                mAdapter = null;
                counter = 0;
                ((BaseActivity)getActivity()).setSubHeaderText("in " + BasicUtils.getLastLocationName(getActivity()));
                mRecyclerView.setVisibility(View.GONE);
                isFirsttime = true;
                showFragmentProgressBar();
                NetworkDAO.getInstance(getActivity().getString(R.string.zomato_api_endpoints), getActivity()).getCuisines(BasicUtils.getLastLocationCoordinate(getActivity())[0], BasicUtils.getLastLocationCoordinate(getActivity())[1]);

                //Fragment fragment = SearchFragment.newInstance();
                //getFragmentManager().beginTransaction().add(R.id.lnr_data_container, fragment, SearchFragment.class.getSimpleName()).addToBackStack(null).commit();
            }
        }
    }
}
