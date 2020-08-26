package mx.com.naatandroidexam.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mx.com.naatandroidexam.R;
import mx.com.naatandroidexam.model.Elemento;

public class RecargasAdapter extends RecyclerView.Adapter<RecargasAdapter.ViewHolder> {
    private ArrayList<Elemento> elementos;
    private OnItemClickListener itemClickListener;

    public RecargasAdapter(ArrayList<Elemento> elementos, OnItemClickListener itemClickListener) {
        this.elementos = elementos;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(elementos.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return elementos.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String nombre, String tipo);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreItem;
        ImageView ivImagenItem;
        LinearLayout llItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreItem = itemView.findViewById(R.id.tvNombreItem);
            ivImagenItem = itemView.findViewById(R.id.ivImagenItem);
            llItem = itemView.findViewById(R.id.llItem);
        }

        private void bind(Elemento elementoBind, final OnItemClickListener listener) {
            this.tvNombreItem.setText(elementoBind.getTipo());

            switch (elementoBind.getNombre()) {
                case "Claro":
                    ivImagenItem.setImageResource(R.drawable.ic_claro);
                    break;
                case "Tuenti":
                    ivImagenItem.setImageResource(R.drawable.ic_tuenti);
                    break;
                case "Entel":
                    ivImagenItem.setImageResource(R.drawable.ic_entel);
                    break;
                default:
                    ivImagenItem.setImageResource(R.drawable.ic_logo);
                    break;
            }

            llItem.setOnClickListener(v -> listener.onItemClick(elementoBind.getNombre(), elementoBind.getTipo()));
        }
    }
}
