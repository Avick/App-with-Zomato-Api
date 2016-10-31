package kiwi.castle.avick.com.kiwiassignment.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import kiwi.castle.avick.com.kiwiassignment.Models.BaseVO;
import kiwi.castle.avick.com.kiwiassignment.Models.CuisineModel;
import kiwi.castle.avick.com.kiwiassignment.Models.Restaurant;
import kiwi.castle.avick.com.kiwiassignment.Models.RestaurantModel;
import kiwi.castle.avick.com.kiwiassignment.R;

/**
 * Created by avick on 10/26/16.
 */


public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<BaseVO> mDataset;
    Context mContext;
    boolean hasNextCuisine;
    private final int VIEW_PROGRESS = -1;
    ClickItemListener mClickItemListener;

    public SearchListAdapter(ArrayList<BaseVO> mDataset, Context mContext, ClickItemListener clickItemListener) {
        this.mDataset = mDataset;
        this.mContext = mContext;
        this.mClickItemListener = clickItemListener;
    }

    public void setHasNextCuisine(boolean hasNextCuisine) {
        this.hasNextCuisine = hasNextCuisine;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == R.layout.restaurant_item) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
            return new ViewHolderRestaurant(v);
        } else if (viewType == R.layout.cuisine_header_item) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuisine_header_item, parent, false);
            return new ViewHolderCuisine(v);
        } else if(viewType == VIEW_PROGRESS) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_progress_bar, parent, false);
            return new ProgressViewHolder(v);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderRestaurant) {
            RestaurantModel restaurant = (RestaurantModel)mDataset.get(position);
            ((ViewHolderRestaurant) holder).txtRestaurant.setText(restaurant.getRestaurant().getName());
            ((ViewHolderRestaurant) holder).txtRating.setText(restaurant.getRestaurant().getUserRating().getAggregateRating());
            ((ViewHolderRestaurant) holder).txtPrice.setText("Cost for two " + restaurant.getRestaurant().getCurrency()+ " " +restaurant.getRestaurant().getAverageCostForTwo());
            if(((RestaurantModel)mDataset.get(position)).getRestaurant().isSaved()) {
                ((ViewHolderRestaurant) holder).txtFavorite.setText("Saved");
            } else {
                ((ViewHolderRestaurant) holder).txtFavorite.setText("Save it for later!");
            }
        } else if(holder instanceof ViewHolderCuisine) {
            CuisineModel cuisine = (CuisineModel)mDataset.get(position);
            ((ViewHolderCuisine) holder).txtCuisineName.setText(cuisine.getCuisine().getName());
        } else if (holder instanceof ProgressViewHolder) {
            ProgressViewHolder mHolder = (ProgressViewHolder) holder;
            mHolder.progressBar.setVisibility(View.VISIBLE);
        }
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

    @Override
    public int getItemViewType(int position) {

        if( mDataset.get(position) == null) {
            return VIEW_PROGRESS;
        } else {
            if(mDataset.get(position) instanceof RestaurantModel) {
                return R.layout.restaurant_item;
            } else if (mDataset.get(position) instanceof CuisineModel) {
                return R.layout.cuisine_header_item;
            }
            return 0;
        }

        //return R.layout.photo_item;
    }


    public void addAdapter(ArrayList<BaseVO> data) {
        if(mDataset == null) {
            mDataset = new ArrayList();
        }

        mDataset.addAll(data);

    }




    public class ViewHolderRestaurant extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtRestaurant;
        public TextView txtRating;
        public TextView txtPrice;
        public TextView txtFavorite;

        public ViewHolderRestaurant(View itemView) {
            super(itemView);
            txtRestaurant = (TextView) itemView.findViewById(R.id.txt_restaurant_name);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_restaurant_price);
            txtRating = (TextView) itemView.findViewById(R.id.txt_restaurant_rating);
            txtFavorite = (TextView) itemView.findViewById(R.id.txt_favourite);

            txtFavorite.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.txt_favourite) {
                if(!((RestaurantModel)mDataset.get(getAdapterPosition())).getRestaurant().isSaved()) {
                    ((RestaurantModel) mDataset.get(getAdapterPosition())).getRestaurant().setSaved(true);
                    notifyItemChanged(getAdapterPosition());
                    mClickItemListener.onClickItem(((RestaurantModel) mDataset.get(getAdapterPosition())).getRestaurant());
                }
            }
        }
    }

    public class ViewHolderCuisine extends RecyclerView.ViewHolder {

        public TextView txtCuisineName;

        public ViewHolderCuisine(View itemView){
            super(itemView);
            txtCuisineName = (TextView) itemView.findViewById(R.id.txt_cuisine_name);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.recycler_progressbar);
        }
    }

    public interface ClickItemListener{
        void onClickItem(Restaurant restaurant);
    }

}
