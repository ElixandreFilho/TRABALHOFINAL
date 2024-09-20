package br.ufc.quixada.usoroomdatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.ufc.quixada.usoroomdatabase.R;
import br.ufc.quixada.usoroomdatabase.models.Agendamento;

public class AgendamentoAdapter extends RecyclerView.Adapter<AgendamentoAdapter.PessoaViewHolder> {

    private List<Agendamento> agendamentos;

    public AgendamentoAdapter(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }

    @NonNull
    @Override
    public PessoaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.agendamento_item, parent, false);
        return new PessoaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PessoaViewHolder holder, int position) {
        Agendamento agendamento = agendamentos.get(position);
        holder.tituloTextView.setText(agendamento.cliente);
        holder.descricaoTextView.setText(agendamento.data);
    }

    @Override
    public int getItemCount() {
        return agendamentos.size();
    }

    public void removeAgendamento(int position) {
        agendamentos.remove(position);
        notifyItemRemoved(position);  // Notifica a remoção
    }

    public void addAgendamento(Agendamento agendamento) {
        agendamentos.add(agendamento);
        notifyItemInserted(agendamentos.size() - 1);  // Notifica a inserção do novo item
    }

    public Agendamento getAgendamentoAt(int position) {
        return agendamentos.get(position);
    }

    public class PessoaViewHolder extends RecyclerView.ViewHolder {
        TextView tituloTextView, descricaoTextView;

        public PessoaViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.clienteTextView);
            descricaoTextView = itemView.findViewById(R.id.dataTextView);
        }
    }
}
