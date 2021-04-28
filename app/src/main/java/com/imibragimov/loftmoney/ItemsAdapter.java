package com.imibragimov.loftmoney;

import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private final List<Item> itemList = new ArrayList<Item>();

    public void setData(List<Item> Item) {
        itemList.clear();
        itemList.addAll(Item);
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.item_view, null);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        holder.bindItem(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView price;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_view);
            price = itemView.findViewById(R.id.price_view);
        }

        public void bindItem(Item item) {
            name.setText(item.getName());
            price.setText(new SpannableString(item.getPrice() + " \u20BD"));

            //price.setText(price.getContext().getResources().getString(R.string.price_with_currency, String.valueOf(item.getPrice())));

            switch (item.getPosition()) {
                case 0:
                    price.setTextColor(ContextCompat.getColor(name.getContext(), R.color.colorPrimary));
                case 1:
                    price.setTextColor(ContextCompat.getColor(name.getContext(), R.color.AppleGreen));
            }
        }
    }
}
