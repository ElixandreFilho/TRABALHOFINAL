package br.ufc.quixada.usoroomdatabase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import br.ufc.quixada.usoroomdatabase.database.AppDatabase;
import br.ufc.quixada.usoroomdatabase.models.Agendamento;
import br.ufc.quixada.usoroomdatabase.FragmentAgendamentos;
import br.ufc.quixada.usoroomdatabase.FragmentAgendamentosRealizados;
import br.ufc.quixada.usoroomdatabase.AgendamentoAdapter;
import android.widget.Button;
import android.view.View;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private AppDatabase db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "agendamento-database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new AgendamentoPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
    }

    private class AgendamentoPagerAdapter extends FragmentStateAdapter {
        public AgendamentoPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return new FragmentAgendamentos();
            } else {
                return new FragmentAgendamentosRealizados();
            }
        }

        @Override
        public int getItemCount() {
            return 2; // Dois fragments
        }
    }
}
