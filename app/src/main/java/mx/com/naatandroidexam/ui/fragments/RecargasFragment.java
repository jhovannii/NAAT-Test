package mx.com.naatandroidexam.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mx.com.naatandroidexam.R;
import mx.com.naatandroidexam.adapters.RecargasAdapter;
import mx.com.naatandroidexam.model.Elemento;
import mx.com.naatandroidexam.persistence.realm.RealmDBHelper;
import mx.com.naatandroidexam.ui.activities.RecargaActivity;

public class RecargasFragment extends Fragment implements RecargasAdapter.OnItemClickListener {
    private ArrayList<Elemento> elementosClaro;
    private ArrayList<Elemento> elementosTuenti;
    private ArrayList<Elemento> elementosEntel;

    public RecargasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_recargas, container, false);

        RecyclerView rvClaro = vista.findViewById(R.id.rvClaro);
        RecyclerView rvTuenti = vista.findViewById(R.id.rvTuenti);
        RecyclerView rvEntel = vista.findViewById(R.id.rvEntel);

        TextView tvRv1 = vista.findViewById(R.id.tvNombreRecycler1);
        TextView tvRv2 = vista.findViewById(R.id.tvNombreRecycler2);
        TextView tvRv3 = vista.findViewById(R.id.tvNombreRecycler3);

        rvClaro.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvTuenti.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvEntel.setLayoutManager(new GridLayoutManager(getContext(), 3));

        elementosClaro = new ArrayList<>();
        elementosTuenti = new ArrayList<>();
        elementosEntel = new ArrayList<>();
        poblarListas();

        RecargasAdapter adapterClaro = new RecargasAdapter(elementosClaro, this);
        RecargasAdapter adapterTuenti = new RecargasAdapter(elementosTuenti, this);
        RecargasAdapter adapterEntel = new RecargasAdapter(elementosEntel, this);

        rvClaro.setAdapter(adapterClaro);
        tvRv1.setText(getString(R.string.hint_claro));
        rvTuenti.setAdapter(adapterTuenti);
        tvRv2.setText(getString(R.string.hint_tuenti));
        rvEntel.setAdapter(adapterEntel);
        tvRv3.setText(getString(R.string.hint_entel));

        return vista;
    }

    private void poblarListas() {
        RealmDBHelper dbHelper = new RealmDBHelper();
        elementosClaro.addAll(dbHelper.getElementosClaro());
        elementosTuenti.addAll(dbHelper.getElementosTuenti());
        elementosEntel.addAll(dbHelper.getElementosEntel());
    }

    @Override
    public void onItemClick(String nombre, String tipo) {
        Intent intent = new Intent(getActivity(), RecargaActivity.class);
        intent.putExtra("nombre", nombre);
        intent.putExtra("tipo", tipo);
        startActivity(intent);
    }
}