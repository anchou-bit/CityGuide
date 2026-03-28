package com.example.cityguide.ui.attractions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cityguide.R;
import com.example.cityguide.data.models.Attraction;

import java.io.File;
import java.util.List;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.AttractionViewHolder> {

    private List<Attraction> attractions;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Attraction attraction);
        void onEditClick(Attraction attraction);
        void onDeleteClick(Attraction attraction);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setAttractions(List<Attraction> attractions) {
        this.attractions = attractions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AttractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attraction, parent, false);
        return new AttractionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionViewHolder holder, int position) {
        Attraction attraction = attractions.get(position);
        holder.bind(attraction);
    }

    @Override
    public int getItemCount() {
        return attractions != null ? attractions.size() : 0;
    }

    class AttractionViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivAttractionImage;
        private final TextView tvName, tvCity, tvAddress;
        private final View btnEdit, btnDelete;

        public AttractionViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAttractionImage = itemView.findViewById(R.id.ivAttractionImage);
            tvName = itemView.findViewById(R.id.tvName);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void bind(Attraction attraction) {
            tvName.setText(attraction.getName());
            tvCity.setText(attraction.getCity());
            tvAddress.setText(attraction.getAddress());

            // Загрузка изображения
            if (attraction.getImagePath() != null && !attraction.getImagePath().isEmpty()) {
                File imageFile = new File(attraction.getImagePath());
                if (imageFile.exists()) {
                    Glide.with(itemView.getContext())
                            .load(imageFile)
                            .centerCrop()
                            .placeholder(R.drawable.ic_profile)
                            .into(ivAttractionImage);
                }
            } else {
                ivAttractionImage.setImageResource(R.drawable.ic_profile);
            }

            // Обработчики кликов
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(attraction);
                }
            });

            btnEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditClick(attraction);
                }
            });

            btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClick(attraction);
                }
            });
        }
    }
}