jpackage com.example.siswaal_azhar.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siswaal_azhar.R;
import com.example.siswaal_azhar.activity.SoalQuisActivity;
import com.example.siswaal_azhar.model.ModelQuis;

import java.util.List;

public class AdapterQuis extends RecyclerView.Adapter<AdapterQuis.ViewHolder> {
    Context context;
    List<ModelQuis>dataQuis;


    public AdapterQuis(List<ModelQuis> dataQuis) {
        this.dataQuis = dataQuis;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View a = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_materi, parent, false);
        ViewHolder holder = new ViewHolder(a);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelQuis data = dataQuis.get(position);
        final String id_pelajaran =  data.getId_pelajaran();
        holder.txt_judul.setText(data.getNama_pelajaran());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SoalQuisActivity.class);
                i.putExtra("id_pelajaran",id_pelajaran);
                Log.e("id",id_pelajaran);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataQuis.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_judul;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            txt_judul = itemView.findViewById(R.id.txtPelajaran);
        }
    }
}
