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
import com.example.siswaal_azhar.model.ModelRapor;

import java.util.List;

public class AdapterRapor extends RecyclerView.Adapter<AdapterRapor.ListViewHolder> {
    Context context;
    List<ModelRapor> rapor;

    public AdapterRapor(List<ModelRapor> rapor) {
        this.rapor = rapor;
    }

    @NonNull
    @Override
    public AdapterRapor.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rapor, parent, false);
        AdapterRapor.ListViewHolder holder = new AdapterRapor.ListViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final ModelRapor model = rapor.get(position);
        String smester = "";
        if (model.getId_semester().equalsIgnoreCase("1")){
            smester="Ganjil";
            /// a
        }else {
            smester="Genap";
        }
        final String text = model.getNama_kelas()+model.getGrup_kelas()+" "+smester;
        holder.textView.setText(text);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PdfActivity.class);
                intent.putExtra("nama", text);
                intent.putExtra("PDF", model.getFile());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rapor.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            cardView = itemView.findViewById(R.id.rowPelajaran);
            textView = itemView.findViewById(R.id.txtRapor);
        }
    }
}
