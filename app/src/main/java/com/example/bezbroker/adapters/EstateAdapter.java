package com.example.bezbroker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bezbroker.R;
import com.example.bezbroker.model.Estate;

import java.util.ArrayList;
import java.util.List;

public class EstateAdapter extends RecyclerView.Adapter<EstateAdapter.VH>{

    public interface OnClick {
        void onEstateClick(Estate estate);
    }

    private List<Estate> items = new ArrayList<>();
    private OnClick onClick;

    public EstateAdapter(OnClick onClick){
        this.onClick = onClick;
    }

    public void setItems(List<Estate> list){
        items.clear();
        items.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estate, parent, false);

        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Estate e = items.get(position);

        holder.title.setText(e.title);
        holder.price.setText(e.price + "€");
        holder.meta.setText(e.categoryName + ";" + e.city + ";"  + e.area);

        String photo = e.firstPhoto();

        if(photo == null){
            holder.image.setImageDrawable(null);
        }
        //TODO: add photo

        holder.itemView.setOnClickListener(v -> onClick.onEstateClick(e));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        TextView price;
        TextView meta;

        public VH(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.itemEstateImage);
            title = itemView.findViewById(R.id.itemEstateTitle);
            price = itemView.findViewById(R.id.itemEstatePrice);
            meta = itemView.findViewById(R.id.itemEstateMeta);
        }
    }
}
