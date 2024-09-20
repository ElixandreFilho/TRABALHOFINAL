package br.ufc.quixada.usoroomdatabase;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import br.ufc.quixada.usoroomdatabase.database.AppDatabase;
import br.ufc.quixada.usoroomdatabase.models.Agendamento;

public class FragmentAgendamentos extends Fragment {

    private RecyclerView recyclerView;
    private AgendamentoAdapter agendamentoAdapter;
    private AppDatabase db;
    private Button criarNovoButton;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agendamentos, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewAgendamentos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        criarNovoButton = view.findViewById(R.id.criarNovoButton);
        criarNovoButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddItemActivity.class);
            startActivity(intent);
        });

        db = Room.databaseBuilder(getContext(), AppDatabase.class, "agendamento-database")
                .allowMainThreadQueries().build();

        atualizarListaAgendamentos();

        // Configuração para mover para agendamentos realizados ao deslizar para a direita
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Agendamento agendamento = agendamentoAdapter.getAgendamentoAt(position);

                if (direction == ItemTouchHelper.RIGHT) {
                    // Adicionar aos agendamentos realizados
                    FragmentAgendamentosRealizados.realizarAgendamento(agendamento);

                    // Remover o agendamento da lista principal
                    db.agendamentoDao().delete(agendamento);

                    // Atualizar o adapter
                    agendamentoAdapter.removeAgendamento(position);

                    Toast.makeText(getContext(), "Cliente atendido!", Toast.LENGTH_SHORT).show();

                } else if (direction == ItemTouchHelper.LEFT) {
                    // Remover agendamento
                    db.agendamentoDao().delete(agendamento);

                    // Atualizar o adapter
                    agendamentoAdapter.removeAgendamento(position);

                    Toast.makeText(getContext(), "Agendamento excluído com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }

    // Método para atualizar a lista de agendamentos
    public void atualizarListaAgendamentos() {
        List<Agendamento> agendamentos = db.agendamentoDao().getAllAgendamentos();
        agendamentoAdapter = new AgendamentoAdapter(agendamentos);
        recyclerView.setAdapter(agendamentoAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        atualizarListaAgendamentos();
    }
}
