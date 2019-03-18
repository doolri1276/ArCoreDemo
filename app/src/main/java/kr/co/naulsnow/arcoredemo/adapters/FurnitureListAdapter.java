package kr.co.naulsnow.arcoredemo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kr.co.naulsnow.arcoredemo.R;
import kr.co.naulsnow.arcoredemo.models.FurnitureItem;
import kr.co.naulsnow.arcoredemo.singletons.FurnitureHelper;

public class FurnitureListAdapter extends RecyclerView.Adapter<FurnitureListAdapter.ViewHolder> {

    Context context;

    OnClickFurnitureInterface onClickFurnitureInterface;

    public FurnitureListAdapter(Context context, OnClickFurnitureInterface onClickFurnitureInterface) {
        this.context = context;
        this.onClickFurnitureInterface = onClickFurnitureInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_furniture_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        FurnitureItem item = FurnitureHelper.getInstance().getFurnitureList().get(i);

        //이미지
        Glide.with(context).load(item.getImageUrl()==null?item.getImageRid():item.getImageUrl()).into(viewHolder.ivImage);

        //이름
        viewHolder.tvName.setText(item.getName());
        viewHolder.tvPrice.setText(item.getPrice()+"원");

    }

    @Override
    public int getItemCount() {
        return FurnitureHelper.getInstance().getFurnitureList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout rootItemFurnitureList;

        ImageView ivImage;
        TextView tvName, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rootItemFurnitureList = itemView.findViewById(R.id.root_item_furniture_list);

            ivImage = itemView.findViewById(R.id.iv_iamge);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);

            rootItemFurnitureList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickFurnitureInterface.onClick(getLayoutPosition());
                }
            });
        }
    }

    public interface OnClickFurnitureInterface{
        void onClick(int num);
    }
}
