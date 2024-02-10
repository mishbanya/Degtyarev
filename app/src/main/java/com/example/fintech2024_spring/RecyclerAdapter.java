package com.example.fintech2024_spring;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private static OnItemClickListener clickListener;
    private static List<Film> dataList;

    public interface OnItemClickListener {
        void onItemClick(Film film);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.itemfilm, parent, false);
        return new ViewHolder(view);
    }
    public RecyclerAdapter(Context context, List<Film> dataList) {
        this.inflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Film data = dataList.get(position);

        holder.textViewName.setText(data.getName());
        holder.textViewYear.setText(String.valueOf((int)data.getYear()));
        Picasso.get().load(data.getPosterUrlPreview()).into(holder.imageCover);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void setData(List<Film> newData) {
        dataList.clear();
        dataList.addAll(newData);
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewYear;
        ImageView imageCover;

        Button button;
        ViewHolder(View itemView) {
            super(itemView);
            imageCover = itemView.findViewById(R.id.imageViewCover);
            textViewYear = itemView.findViewById(R.id.textViewYear);
            textViewName = itemView.findViewById(R.id.textViewName);
            button = itemView.findViewById(R.id.buttonFilm);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && clickListener != null) {
                        clickListener.onItemClick(dataList.get(position));
                    }
                }
            });
        }
    }
}
