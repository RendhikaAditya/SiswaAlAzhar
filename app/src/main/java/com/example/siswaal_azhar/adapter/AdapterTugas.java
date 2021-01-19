package com.example.siswaal_azhar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siswaal_azhar.R;
import com.example.siswaal_azhar.activity.KirimTugasActivity;
import com.example.siswaal_azhar.model.ModelMateri;
import com.example.siswaal_azhar.model.ModelTugas;

import java.util.List;

public class AdapterTugas extends RecyclerView.Adapter<AdapterTugas.ListViewHolder> {
    Context context;
    List<ModelTugas>tugas;

    public AdapterTugas(List<ModelTugas> tugas) {
        this.tugas = tugas;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tugas, parent, false);
        AdapterTugas.ListViewHolder holder = new AdapterTugas.ListViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        final ModelTugas model = tugas.get(position);
        holder.judul.setText(model.getJudul_tugas());
        holder.deadline.setText("Jatuh Tempo : "+model.getDeadline_tugas());
        holder.isi.setText(model.getIsi_tugas());
        holder.pelajaran.setText(model.getNama_pelajaran());
        if (model.getFile_tugas()==null){
            holder.filePlace.setVisibility(View.GONE);
        }else {
            holder.fileTugas.setText(model.getFile_tugas());
        }
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, KirimTugasActivity.class);
                intent.putExtra("judul", model.getJudul_tugas());
                intent.putExtra("deadline", model.getDeadline_tugas());
                intent.putExtra("isi", model.getIsi_tugas());
                intent.putExtra("file", model.getFile_tugas());
                intent.putExtra("id", model.getId_tugas());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tugas.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView judul, isi, pelajaran, deadline, fileTugas;
        RelativeLayout filePlace, row;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            row = itemView.findViewById(R.id.rowTugas);
            judul = itemView.findViewById(R.id.judulTugas);
            isi = itemView.findViewById(R.id.isiTugas);
            pelajaran = itemView.findViewById(R.id.judulPelajaran);
            deadline = itemView.findViewById(R.id.deadLine);
            fileTugas = itemView.findViewById(R.id.txtNamaFile);
            filePlace = itemView.findViewById(R.id.filePlace);
        }
    }
}
