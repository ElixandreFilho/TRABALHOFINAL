package br.ufc.quixada.usoroomdatabase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.usoroomdatabase.database.AppDatabase;
import br.ufc.quixada.usoroomdatabase.models.Agendamento;

public class FragmentAgendamentosRealizados extends Fragment {

    private RecyclerView recyclerView;
    private AgendamentoAdapter agendamentoAdapter;
    private AppDatabase db;
    private static List<Agendamento> realizados = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agendamentos_realizados, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewRealizados);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = Room.databaseBuilder(getContext(), AppDatabase.class, "agendamento-database")
                .allowMainThreadQueries().build();

        // Inicializar o adapter com a lista de agendamentos realizados
        agendamentoAdapter = new AgendamentoAdapter(realizados);
        recyclerView.setAdapter(agendamentoAdapter);

        // Configuração para remover ao deslizar para a esquerda
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Agendamento agendamento = agendamentoAdapter.getAgendamentoAt(position);

                // Remove o agendamento do banco de dados
                db.agendamentoDao().delete(agendamento);

                // Remove o item da lista no Adapter
                agendamentoAdapter.removeAgendamento(position);
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }

    // Método para adicionar agendamentos realizados
    public static void realizarAgendamento(Agendamento agendamento) {
        if (!realizados.contains(agendamento)) {
            realizados.add(agendamento);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Atualizar a lista de agendamentos realizados
        agendamentoAdapter.notifyDataSetChanged();
    }
}
