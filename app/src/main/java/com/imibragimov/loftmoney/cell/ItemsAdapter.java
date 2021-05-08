package com.imibragimov.loftmoney.cell;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.imibragimov.loftmoney.R;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<ItemModel> moneyItemModelList = new ArrayList<>();

    public void clearItems() {
        moneyItemModelList.clear();
        notifyDataSetChanged();
    }

    public void setData(List<ItemModel> moneyItemModels) {
        moneyItemModelList.clear();
        moneyItemModelList.addAll(moneyItemModels);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.money_item;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutIdForListItem, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        holder.bind(moneyItemModelList.get(position));
    }

    @Override
    public int getItemCount() {

        return moneyItemModelList.size();
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView price;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name_expenses);
            price = itemView.findViewById(R.id.tv_price_expenses);
        }

        public void bind(ItemModel itemModel) {
            name.setText(itemModel.getName());
            price.setText(new SpannableString(itemModel.getPrice() + " \u20BD"));
            if (itemModel.getPosition() == 0) {
                price.setTextColor(ContextCompat.getColor(price.getContext(), R.color.priceColor));
            } else if (itemModel.getPosition() == 1) {
                price.setTextColor(ContextCompat.getColor(price.getContext(), R.color.priceColor_2));
            }
        }
    }
}
