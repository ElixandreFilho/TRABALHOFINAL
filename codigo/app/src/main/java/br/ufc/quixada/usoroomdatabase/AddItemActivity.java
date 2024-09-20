package br.ufc.quixada.usoroomdatabase;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import br.ufc.quixada.usoroomdatabase.database.AppDatabase;
import br.ufc.quixada.usoroomdatabase.models.Agendamento;

public class AddItemActivity extends AppCompatActivity {

    private EditText clienteEditText, dataHoraEditText;
    private Button saveButton;
    private Calendar calendar = Calendar.getInstance();
    private AppDatabase db;
    private List<String> horariosPermitidos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        clienteEditText = findViewById(R.id.clienteEditText);
        dataHoraEditText = findViewById(R.id.dataHoraEditText);
        saveButton = findViewById(R.id.saveButton);
        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "agendamento-database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        // Configura a lista de horários permitidos
        setHorariosPermitidos();

        dataHoraEditText.setOnClickListener(v -> showDateTimePicker());

        saveButton.setOnClickListener(v -> {
            String cliente = clienteEditText.getText().toString();
            String dataHora = dataHoraEditText.getText().toString();

            if (cliente.isEmpty() || dataHora.isEmpty()) {
                Toast.makeText(AddItemActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            Agendamento novoAgendamento = new Agendamento(cliente, dataHora);
            db.agendamentoDao().insertAll(novoAgendamento);
            Toast.makeText(AddItemActivity.this, "Agendamento salvo!", Toast.LENGTH_SHORT).show();

            // Envia um resultado para a MainActivity
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    // Configura os horários permitidos
    private void setHorariosPermitidos() {
        // Manhã (8h-12h)
        for (int i = 8; i <= 12; i++) {
            horariosPermitidos.add(String.format("%02d:00", i));
        }
        // Tarde (14h-17h)
        for (int i = 14; i <= 17; i++) {
            horariosPermitidos.add(String.format("%02d:00", i));
        }
    }

    // Exibe o DatePicker e o TimePicker - funcionalidade calendario e hora
    @SuppressLint("DefaultLocale")
    private void showDateTimePicker() {
        final Calendar currentDate = Calendar.getInstance();
        new DatePickerDialog(AddItemActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(year, monthOfYear, dayOfMonth);

            new TimePickerDialog(AddItemActivity.this, (timeView, hourOfDay, minute) -> {
                dataHoraEditText.setText(String.format("%02d/%02d/%04d %02d:00",
                        dayOfMonth, (monthOfYear + 1), year, hourOfDay));

            }, currentDate.get(Calendar.HOUR_OF_DAY), 0, true).show();

        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH)).show();
    }
}
