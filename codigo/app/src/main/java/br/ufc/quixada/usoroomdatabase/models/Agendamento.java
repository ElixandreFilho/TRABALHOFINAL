package br.ufc.quixada.usoroomdatabase.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "agendamento")
public class Agendamento {
    // : Id, Nome, Curso e Idade
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "cliente")
    public String cliente;
    @ColumnInfo(name = "data")
    public String data;



    public Agendamento(String cliente, String data){
        this.cliente = cliente;
        this.data = data;
    }

    @NonNull
    @Override
    public String toString() {
        String nomeRetorno = this.uid + " | " + this.cliente + " | " + this.data;
        return nomeRetorno ;
    }
}
