package kiwi.castle.avick.com.kiwiassignment.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import kiwi.castle.avick.com.kiwiassignment.Adapter.OfflineAdapter;
import kiwi.castle.avick.com.kiwiassignment.Adapter.SearchListAdapter;
import kiwi.castle.avick.com.kiwiassignment.Models.RealmRestaurant;
import kiwi.castle.avick.com.kiwiassignment.R;

/**
 * Created by avick on 10/28/16.
 */

public class OfflineFragment extends BaseFragment {

    Realm realm;
    RecyclerView mRecyclerView;
    OfflineAdapter mAdapter;
    ArrayList<RealmRestaurant> mDataset;
    TextView txtFavourite, txtemptyState;
    public static OfflineFragment newInstance() {
        OfflineFragment frag = new OfflineFragment();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_result, container, false);
        init(view);

        //view.setVisibility(View.GONE);
        return view;
    }




    public void init(View v) {
        txtFavourite = (TextView)v.findViewById(R.id.txt_favourites);
        txtFavourite.setVisibility(View.GONE);
        txtemptyState = (TextView)v.findViewById(R.id.empty_state);
        txtemptyState.setText("No Favourites");
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_search_result);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        realm = Realm.getDefaultInstance();
        updateRecylerView();


    }

    public void updateRecylerView() {
        mDataset = new ArrayList<>();
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.where(RealmRestaurant.class).findAll().deleteAllFromRealm();
//            }
//        });

        for (RealmRestaurant res : realm.where(RealmRestaurant.class).findAll()) {
            mDataset.add(res);
        }

        if(mDataset.size() == 0) {
            txtemptyState.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            txtemptyState.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter = new OfflineAdapter(getActivity(), mDataset);
            mRecyclerView.setAdapter(mAdapter);
        }
    }



}
