package com.christuniversity.atmosys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.widget.TextView;
import com.christuniversity.atmosys.databinding.ActivityMainBinding;
import com.christuniversity.atmosys.fetchData.ApiDataFetcher;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    TextView tempValue, humValue, pressValue, lightIntValue, altValue, rainValue, dewPointValue;
    private final int FETCH_INTERVAL_MS = 1500; //1.5 seconds
    private Handler fetchHandler;
    ApiDataFetcher fetchData = new ApiDataFetcher();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        tempValue = findViewById(R.id.temperatureValue);
        tempValue.setText(fetchData.fetchData("temperature"));
        humValue = findViewById(R.id.humidityValue);
        humValue.setText(fetchData.fetchData("humidity"));
        pressValue = findViewById(R.id.pressureValue);
        pressValue.setText(fetchData.fetchData("pressure"));
        lightIntValue = findViewById(R.id.lightIntensityValue);
        lightIntValue.setText(fetchData.fetchData("lightIntensity"));
        altValue = findViewById(R.id.altitudeValue);
        altValue.setText(fetchData.fetchData("altitude"));
        rainValue = findViewById(R.id.rainValue);
        rainValue.setText(fetchData.fetchData("rain"));
        dewPointValue = findViewById(R.id.dewPointValue);
        dewPointValue.setText(fetchData.fetchData("dewPoint"));

        fetchHandler = new Handler(Looper.getMainLooper());

        // Start the initial fetch
        fetchHandler.post(fetchRunnable);
    }
    private String t,h,p,l,a,r,d;
    private Runnable fetchRunnable = new Runnable() {
        @Override
        public void run() {
            // Perform the network request in the background
            new Thread(new Runnable() {
                @Override
                public void run() {
                    tempValue.setText(fetchData.fetchData("temperature"));
                    humValue.setText(fetchData.fetchData("humidity"));
                    pressValue.setText(fetchData.fetchData("pressure"));
                    lightIntValue.setText(fetchData.fetchData("lightIntensity"));
                    altValue.setText(fetchData.fetchData("altitude"));
                    rainValue.setText(fetchData.fetchData("rain"));
                    dewPointValue.setText(fetchData.fetchData("dewPoint"));
                    // Schedule the next fetch after the interval
                    fetchHandler.postDelayed(fetchRunnable, FETCH_INTERVAL_MS);
                }
            }).start();
        }
    };

    private void updateUIWithData(String data) {
        // Update your UI elements here with the fetched data
        // This method will run on the main UI thread
    }
            }