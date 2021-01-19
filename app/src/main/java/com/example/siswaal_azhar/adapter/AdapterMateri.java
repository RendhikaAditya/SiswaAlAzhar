package com.example.siswaal_azhar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siswaal_azhar.R;
import com.example.siswaal_azhar.activity.PdfActivity;
import com.example.siswaal_azhar.model.ModelJadwal;
import com.example.siswaal_azhar.model.ModelMateri;

import java.util.List;

public class AdapterMateri extends RecyclerView.Adapter<AdapterMateri.ListViewHolder> {
    Context context;
    List<ModelMateri>materi;

    public AdapterMateri(List<ModelMateri> materi) {
        this.materi = materi;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_materi, parent, false);
        AdapterMateri.ListViewHolder holder = new AdapterMateri.ListViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final ModelMateri model = materi.get(position);
        holder.textView.setText(model.getNama_pelajaran());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PdfActivity.class);
                intent.putExtra("nama", model.getNama_pelajaran());
                intent.putExtra("PDF", model.getMateri_pelajaran());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return materi.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            cardView = itemView.findViewById(R.id.rowPelajaran);
            textView = itemView.findViewById(R.id.txtPelajaran);
        }
    }
}
