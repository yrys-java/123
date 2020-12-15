package com.example.mappactice8_12_2020.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mappactice8_12_2020.R;
import com.example.mappactice8_12_2020.data.model.Routes;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RoutesHolder> {

    private List<Routes> routesArrayList = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Routes run);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public RoutesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new RoutesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutesHolder holder, int position) {
        Routes run = routesArrayList.get(position);
        holder.routeLength.setText(run.getRoutesLength());
        holder.averageSpeed.setText(run.getAverageSpeed());
        holder.timeSpent.setText(run.getTimeSpent());
    }

    @Override
    public int getItemCount() {
        return routesArrayList.size();

    }

    public void setRoutesArrayList(List<Routes> run) {
        this.routesArrayList = run;
        notifyDataSetChanged();
    }

    class RoutesHolder extends RecyclerView.ViewHolder {

        TextView routeLength;
        TextView averageSpeed;
        TextView timeSpent;

        public RoutesHolder(@NonNull View itemView) {
            super(itemView);

            routeLength = itemView.findViewById(R.id.run);
            timeSpent = itemView.findViewById(R.id.time);
            averageSpeed = itemView.findViewById(R.id.speed);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(routesArrayList.get(position));
                    }
                }
            });

        }
    }
}
