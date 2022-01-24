package mmv.devlops.datakelas.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mmv.devlops.datakelas.R;
import mmv.devlops.datakelas.adapter.KelasAdapter;
import mmv.devlops.datakelas.database.SiswaDatabase;
import mmv.devlops.datakelas.model.KelasModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvKelas)
    RecyclerView rvKelas;

    private SiswaDatabase siswaDatabase;
    private List<KelasModel> kelasModelList;
    private TextView batteryLevel;
    private ProgressBar mBatteryLevelProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        batteryLevel = (TextView) findViewById(R.id.batteryLevel);
        mBatteryLevelProgress = (ProgressBar) findViewById(R.id.progressBar);

        this.registerReceiver(this.myBatteryReceivers, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        // Membuat object database
        siswaDatabase = SiswaDatabase.createDatabase(this);

        // Membuat membuat object List
        kelasModelList = new ArrayList<>();

        ExtendedFloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TambahKelasActivity.class));
            }
        });
            }

    @Override
    protected void onResume() {
        super.onResume();

        // Mengosongkan List agar dipastikan list dapat disi dengan data yg paling baru
        kelasModelList.clear();

        getData();

        // Mensetting dan proses menampilkan data ke RecyclerView
        rvKelas.setLayoutManager(new GridLayoutManager(this, 2));
        rvKelas.setAdapter(new KelasAdapter(this, kelasModelList));
    }

    private void getData() {

        // Operasi mengambil data dari database Sqlite
        kelasModelList = siswaDatabase.kelasDao().select();
    }
    private BroadcastReceiver myBatteryReceivers = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int lvl = intent.getIntExtra("level", 0);
            batteryLevel.setText("Battery anda saat ini : " + String.valueOf(lvl) + "%");
            mBatteryLevelProgress.setProgress(lvl);
            if(lvl == 100){
                Toast.makeText(context, "Battery Full.", Toast.LENGTH_SHORT).show();
            }
        }
    };
}

