package com.imibragimov.loftmoney;

//import java.util.Collections;
//import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<Item> items;

    ItemsAdapter(Context context, List<Item> items) {
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

   // public void setItems(List<Item> items) {
   //     this.items = items;
   //     notifyDataSetChanged();
   // }

    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    //    Context context = viewGroup.getContext();
    //    LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item, parent, false);
    //    final RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(view);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ItemsAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.nameView.setText(item.getName());
        holder.priceView.setText(item.getPrice());
    //    ViewHolder.bind(item);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView nameView, priceView;
        ViewHolder(View view){
            super(view);
            nameView = (TextView) view.findViewById(R.id.name);
            priceView = (TextView) view.findViewById(R.id.price);
        }
    }
}
