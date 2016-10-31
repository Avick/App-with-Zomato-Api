package kiwi.castle.avick.com.kiwiassignment.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import kiwi.castle.avick.com.kiwiassignment.Models.RealmRestaurant;
import kiwi.castle.avick.com.kiwiassignment.Models.Restaurant;
import kiwi.castle.avick.com.kiwiassignment.R;


/**
 * Created by avick on 10/28/16.
 */

public class OfflineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<RealmRestaurant> mDataset;
    Context mContext;
    LongClickItemListener itemListener;

    public OfflineAdapter(Context context, ArrayList<RealmRestaurant> dataset, LongClickItemListener listener) {
        mContext = context;
        mDataset = dataset;
        itemListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offline_restaurant_item, parent, false);
        return new ViewHolderRestaurant(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderRestaurant mholder = (ViewHolderRestaurant)holder;
        mholder.txtRestaurant.setText(mDataset.get(position).getName());
        mholder.txtRating.setText(mDataset.get(position).getRating());
        mholder.txtPrice.setText("Cost for two " + mDataset.get(position).getCurrency()+ " " +mDataset.get(position).getAverageCostForTwo());
        mholder.txtCuisines.setText("Cuisines: " + mDataset.get(position).getCuisines());
    }

    @Override
    public int getItemCount() {
        if (this.mDataset != null) {
            return mDataset.size();
            //return (hasNextCuisine ? (mDataset.size() + 1) : mDataset.size());
        } else {
            return 0;
        }
    }

    public class ViewHolderRestaurant extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        public TextView txtRestaurant;
        public TextView txtRating;
        public TextView txtPrice;
        public TextView txtCuisines;

        public ViewHolderRestaurant(View itemView) {
            super(itemView);
            txtRestaurant = (TextView) itemView.findViewById(R.id.txt_restaurant_name);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_restaurant_price);
            txtRating = (TextView) itemView.findViewById(R.id.txt_restaurant_rating);
            txtCuisines = (TextView) itemView.findViewById(R.id.txt_cuisines);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View view) {
            itemListener.onLongClickItem(mDataset.get(getAdapterPosition()), getAdapterPosition());
            return true;
        }
    }

    public interface LongClickItemListener{
        void onLongClickItem(RealmRestaurant restaurant, int position);
    }

}