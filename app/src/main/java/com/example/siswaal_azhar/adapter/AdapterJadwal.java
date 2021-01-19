package com.example.siswaal_azhar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siswaal_azhar.R;
import com.example.siswaal_azhar.model.ModelJadwal;

import java.util.List;

public class AdapterJadwal extends RecyclerView.Adapter<AdapterJadwal.ListViewHolder> {
    Context context;
    List<ModelJadwal>jadwals;

    public AdapterJadwal(List<ModelJadwal> jadwals) {
        this.jadwals = jadwals;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_jadwal, parent, false);
        AdapterJadwal.ListViewHolder holder = new AdapterJadwal.ListViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final ModelJadwal model = jadwals.get(position);
        holder.pelajaran.setText(model.getNama_pelajaran());
        holder.jam.setText(model.getJam_awal().substring(0,5)+" - "+model.getJam_akhir().substring(0,5));
    }

    @Override
    public int getItemCount() {
        return jadwals.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView pelajaran, jam;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            pelajaran = itemView.findViewById(R.id.namaPelajaran);
            jam = itemView.findViewById(R.id.jam);
        }
    }
}
