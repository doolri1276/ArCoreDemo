package kr.co.naulsnow.arcoredemo.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import kr.co.naulsnow.arcoredemo.R;
import kr.co.naulsnow.arcoredemo.adapters.CartAdapter;
import kr.co.naulsnow.arcoredemo.models.FurnitureItem;
import kr.co.naulsnow.arcoredemo.singletons.FurnitureHelper;

public class CartDialog extends DialogFragment {

    OnResult onResult;

    //툴바
    ImageView ivCancel;
    TextView tvTopSum;

    //전체 선택
    CheckBox cbCheckAll;

    //장바구니 리스트
    RecyclerView rvFurnitures;
    CartAdapter adapter;

    //구매
    TextView tvPurchaseSelected, tvPurchaseAll;

    //결과
    TextView tvFurnitureAllSum, tvFurnitureSum, tvDiscountSum, tvCouponSum, tvShippingSum, tvSum;


    //데이터들
    int shippingSum;
    boolean isSetting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_cart, container, false);
        setViews(view);
        setListeners();
        setAppBar();
        setAdapter();
        setData();
        return view;
    }

    private void setViews(View view) {
        //툴바
        ivCancel = view.findViewById(R.id.iv_cancel);
        tvTopSum = view.findViewById(R.id.tv_top_sum);

        //전체선택
        cbCheckAll = view.findViewById(R.id.cb_check_all);

        //장바구니 리스트
        rvFurnitures = view.findViewById(R.id.rv_furnitures);

        //구매
        tvPurchaseSelected = view.findViewById(R.id.tv_purchase_selected);
        tvPurchaseAll = view.findViewById(R.id.tv_purchase_all);

        //결과
        tvFurnitureSum = view.findViewById(R.id.tv_furniture_sum);
        tvFurnitureAllSum = view.findViewById(R.id.tv_furniture_all_sum);
        tvDiscountSum = view.findViewById(R.id.tv_discount_sum);
        tvCouponSum = view.findViewById(R.id.tv_coupon_sum);
        tvShippingSum = view.findViewById(R.id.tv_shipping_sum);
        tvSum = view.findViewById(R.id.tv_sum);

    }

    private void setListeners() {
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResult.finish();
                getDialog().dismiss();
            }
        });

        cbCheckAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isSetting) return;
                isSetting=true;

                for(int i=0;i<FurnitureHelper.getInstance().getCartFurnitureList().size();i++){
                    FurnitureHelper.getInstance().getCartFurnitureList().get(i).setSelected(isChecked);
                }

                isSetting=false;
                adapter.notifyDataSetChanged();
                setData();
            }
        });

    }

    private void setAppBar() {

    }

    private void setAdapter(){
        adapter = new CartAdapter(getContext(), new CartAdapter.OnClickItemInterface() {
            @Override
            public void onClick(int num, boolean selected) {
                setData();
            }
        });
        rvFurnitures.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setData() {

        isSetting=true;

        //툴바
        tvTopSum.setText(FurnitureHelper.getInstance().getSelectedSum()+"원");

        //전체선택
        boolean isCheckedAll = true;
        for(int i=0;i<FurnitureHelper.getInstance().getCartFurnitureList().size();i++){
            FurnitureItem item = FurnitureHelper.getInstance().getCartFurnitureList().get(i);

            if(!item.isSelected()){
                isCheckedAll=false;
                break;
            }
        }
        cbCheckAll.setChecked(isCheckedAll);

        //결과
        tvFurnitureSum.setText(FurnitureHelper.getInstance().getSelectedSum()+"원");
        tvFurnitureAllSum.setText(FurnitureHelper.getInstance().getAllSum()+"원");
        shippingSum = FurnitureHelper.getInstance().getCartFurnitureList().size()>0 && FurnitureHelper.getInstance().getSelectedSum()>100000?0:3000;
        tvShippingSum.setText(shippingSum+"원");
        tvSum.setText((FurnitureHelper.getInstance().getSelectedSum()+shippingSum)+"원");

        isSetting=false;

    }

    public interface OnResult{
        void finish();
    }

    public void setOnResult(OnResult onResult) {
        this.onResult = onResult;
    }
}
