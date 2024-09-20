package br.ufc.quixada.usoroomdatabase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.ufc.quixada.usoroomdatabase.models.Agendamento;

@Dao
public interface AgendamentoDao {
    @Insert
    void insertAll(Agendamento... agendamentos);

    @Delete
    void delete(Agendamento agendamento);

    @Query("SELECT * FROM agendamento")
    List<Agendamento> getAllAgendamentos();

}
