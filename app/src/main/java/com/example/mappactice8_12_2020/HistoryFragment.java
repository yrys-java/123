package com.example.mappactice8_12_2020;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mappactice8_12_2020.R;
import com.example.mappactice8_12_2020.adapter.RecyclerViewAdapter;
import com.example.mappactice8_12_2020.data.model.Routes;
import com.example.mappactice8_12_2020.data.model.RoutesViewModel;

import java.util.List;

public class HistoryFragment extends Fragment {

    private static RoutesViewModel routesViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        routesViewModel = ViewModelProviders.of(this).get(RoutesViewModel.class);
        routesViewModel.getAllRun().observe(getActivity(), new Observer<List<Routes>>() {
            @Override
            public void onChanged(List<Routes> routes) {
                adapter.setRoutesArrayList(routes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                 ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Удалить?");
                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        routesViewModel.delete(adapter.routesPosition(viewHolder.getAdapterPosition()));
                        Toast.makeText(getContext(), "Забег удален", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        Toast.makeText(getContext(),
                                "Удаление отменено", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }


}