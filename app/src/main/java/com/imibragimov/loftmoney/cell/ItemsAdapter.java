package com.imibragimov.loftmoney.cell;

import android.content.Context;
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
    public ItemsAdapterClick itemsAdapterClick;

    public void clearItems() {
        moneyItemModelList.clear();
        notifyDataSetChanged();
    }

    public void setData(List<ItemModel> moneyItemModels) {
        moneyItemModelList.clear();
        moneyItemModelList.addAll(moneyItemModels);
        notifyDataSetChanged();
    }

    public List<ItemModel> getMoneyItemModelList() {
        return moneyItemModelList;
    }

    public void updateItem(ItemModel itemModel) {
        int itemPosition = moneyItemModelList.indexOf(itemModel);
        moneyItemModelList.set(itemPosition, itemModel);
        notifyItemChanged(itemPosition);
    }


    public void setItemsAdapterClick(ItemsAdapterClick itemsAdapterClick) {
        this.itemsAdapterClick = itemsAdapterClick;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.money_item;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutIdForListItem, parent, false);

        return new ItemViewHolder(view, itemsAdapterClick);
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
        private  ItemsAdapterClick itemsAdapterClick;

        public ItemViewHolder(@NonNull View itemView, ItemsAdapterClick itemsAdapterClick) {
            super(itemView);

            this.itemsAdapterClick = itemsAdapterClick;
            name = itemView.findViewById(R.id.tv_name_expenses);
            price = itemView.findViewById(R.id.tv_price_expenses);

        }

        public void bind(final ItemModel itemModel) {
            name.setText(itemModel.getName());
            //price.setText(new SpannableString(itemModel.getPrice() + " \u20BD"));
            price.setText(price.getContext().getResources().getString(R.string.price_with_currency, String.valueOf(itemModel.getPrice())));

            if (itemModel.getPosition() == 0) {
                price.setTextColor(ContextCompat.getColor(price.getContext(), R.color.priceColor));
            } else if (itemModel.getPosition() == 1) {
                price.setTextColor(ContextCompat.getColor(price.getContext(), R.color.priceColor_2));
            }

            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), itemModel.isSelected() ? R.color.itemSelectionColor : android.R.color.white));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemsAdapterClick != null){
                        itemsAdapterClick.onCellClick(itemModel);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (itemsAdapterClick != null) {
                        itemsAdapterClick.onLongCellClick(itemModel);
                    }
                    return true;
                }
            });
        }
    }
}
