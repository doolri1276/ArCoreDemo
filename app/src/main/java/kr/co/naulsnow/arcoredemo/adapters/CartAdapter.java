package kr.co.naulsnow.arcoredemo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kr.co.naulsnow.arcoredemo.R;
import kr.co.naulsnow.arcoredemo.models.FurnitureItem;
import kr.co.naulsnow.arcoredemo.singletons.FurnitureHelper;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;

    OnClickItemInterface onClickItemInterface;

    public CartAdapter(Context context, OnClickItemInterface onClickItemInterface) {
        this.context = context;
        this.onClickItemInterface = onClickItemInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        FurnitureItem item = FurnitureHelper.getInstance().getCartFurnitureList().get(i);

        viewHolder.isSetting=true;
        viewHolder.cbSelected.setChecked(item.isSelected());
        Glide.with(context).load(item.getImageUrl()).into(viewHolder.ivPreview);
        viewHolder.tvName.setText(item.getName());
        viewHolder.tvPrice.setText(item.getPrice()+"원");
        viewHolder.isSetting=false;

    }

    @Override
    public int getItemCount() {
        return FurnitureHelper.getInstance().getCartFurnitureList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox cbSelected;
        ImageView ivPreview;
        TextView tvName, tvPrice;
        ImageView ivRemove;


        //데이터들
        boolean isSetting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cbSelected = itemView.findViewById(R.id.cb_selected);
            ivPreview = itemView.findViewById(R.id.iv_preview);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            ivRemove = itemView.findViewById(R.id.iv_remove);

            cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isSetting) return;
                    isSetting=true;
                    FurnitureItem item = FurnitureHelper.getInstance().getCartFurnitureList().get(getLayoutPosition());
                    item.setSelected(isChecked);
                    onClickItemInterface.onClick(getLayoutPosition(), isChecked);
                    isSetting=false;
                }
            });

            ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FurnitureItem item = FurnitureHelper.getInstance().getCartFurnitureList().get(getLayoutPosition());
                    item.getOnDetachNode().onDetach(getLayoutPosition());
                    onClickItemInterface.onClick(getLayoutPosition(), false);
//                    FurnitureHelper.getInstance().getCartFurnitureList().remove(getLayoutPosition());
                    notifyItemRemoved(getLayoutPosition());
                }
            });
        }
    }

    public interface OnClickItemInterface{
        void onClick(int num, boolean selected);
    }
}
