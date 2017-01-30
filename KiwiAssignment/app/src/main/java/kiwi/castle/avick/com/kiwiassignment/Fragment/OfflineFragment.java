package kiwi.castle.avick.com.kiwiassignment.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import io.realm.RealmQuery;
import io.realm.RealmResults;
import kiwi.castle.avick.com.kiwiassignment.Activity.BaseActivity;
import kiwi.castle.avick.com.kiwiassignment.Adapter.OfflineAdapter;
import kiwi.castle.avick.com.kiwiassignment.Adapter.SearchListAdapter;
import kiwi.castle.avick.com.kiwiassignment.Models.RealmRestaurant;
import kiwi.castle.avick.com.kiwiassignment.R;

/**
 * Created by avick on 10/28/16.
 */

public class OfflineFragment extends BaseFragment implements OfflineAdapter.LongClickItemListener {

    Realm realm;
    RecyclerView mRecyclerView;
    OfflineAdapter mAdapter;
    ArrayList<RealmRestaurant> mDataset;
    TextView txtFavourite, txtemptyState, txtInstruction;
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
        customizeToolbar();
        init(view);
        return view;
    }




    public void init(View v) {
        txtFavourite = (TextView)v.findViewById(R.id.txt_favourites);
        txtFavourite.setVisibility(View.GONE);
        txtemptyState = (TextView)v.findViewById(R.id.empty_state);
        txtInstruction = (TextView)v.findViewById(R.id.txt_instruction);
        txtInstruction.setVisibility(View.VISIBLE);
        txtemptyState.setText(R.string.no_favourites);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.rv_search_result);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        updateRecylerView();


    }

    public void updateRecylerView() {
        realm = Realm.getDefaultInstance();
        mDataset = new ArrayList<>();

        for (RealmRestaurant res : realm.where(RealmRestaurant.class).findAll()) {
            mDataset.add(res);
        }

        if(mDataset.size() == 0) {
            txtemptyState.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            txtemptyState.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter = new OfflineAdapter(getActivity(), mDataset, this);
            mRecyclerView.setAdapter(mAdapter);
        }

        realm.close();
    }


    @Override
    public void onLongClickItem(final RealmRestaurant restaurant , final int position) {
        ((BaseActivity)getActivity()).showInstructionAlert("DELETE!!!", "Are you sure ?", getActivity(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        removeItem(restaurant, position);
                        dialog.dismiss();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        },null);
                    //.setNegativeButton("No", null).show();


    }

    public void removeItem(final RealmRestaurant restaurant ,int position) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmRestaurant> result = realm.where(RealmRestaurant.class).equalTo("id", restaurant.getId()).findAll();
                result.deleteAllFromRealm();
            }
        });

        mDataset.remove(position);
        mAdapter.notifyDataSetChanged();
        if(mDataset.size() == 0) {
            txtemptyState.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    public void customizeToolbar(){
        ((BaseActivity)getActivity()).getImgMap().setVisibility(View.GONE);
        ((BaseActivity)getActivity()).getImgSearch().setVisibility(View.GONE);
        ((BaseActivity)getActivity()).getTxtHeaderView().setText(getString(R.string.favourite_text));
        ((BaseActivity)getActivity()).getTxtSubHeader().setVisibility(View.GONE);
    }


}
