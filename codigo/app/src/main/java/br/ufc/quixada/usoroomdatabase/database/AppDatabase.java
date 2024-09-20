package br.ufc.quixada.usoroomdatabase.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import br.ufc.quixada.usoroomdatabase.dao.AgendamentoDao;
import br.ufc.quixada.usoroomdatabase.dao.PessoaDao;
import br.ufc.quixada.usoroomdatabase.models.Agendamento;
import br.ufc.quixada.usoroomdatabase.models.Pessoa;

@Database(entities = {Agendamento.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AgendamentoDao agendamentoDao();
}

