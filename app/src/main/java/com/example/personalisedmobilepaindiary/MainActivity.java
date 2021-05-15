package com.example.personalisedmobilepaindiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personalisedmobilepaindiary.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText city;
    TextView tempo, humad,press;
    String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    String apikey = "266fcebd925baa958ad5e1095d286d70";
    LocationManager manager;
    LocationListener locationListener;
    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city = findViewById(R.id.city);
        tempo = findViewById(R.id.temp);
        press = findViewById(R.id.pressure);
        humad = findViewById(R.id.humadity);
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.appBar.toolbar);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_fragment,
                R.id.nav_add_fragment,
                R.id.nav_view_fragment)
//to display the Navigation button as a drawer symbol,not being shown as an Up

                .setOpenableLayout(binding.drawerLayout)
                .build();
        FragmentManager fragmentManager= getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment)
                fragmentManager.findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        //Sets up a NavigationView for use with a NavController.
        NavigationUI.setupWithNavController(binding.navView, navController);
        //Sets up a Toolbar for use with a NavController.
        NavigationUI.setupWithNavController(binding.appBar.toolbar,navController,
                mAppBarConfiguration);
    }
    public void getweather(View v){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherapi myapi=retrofit.create(weatherapi.class);
        Call<weatherfetch> weathercall=myapi.getweather(city.getText().toString().trim(),apikey);
        weathercall.enqueue(new Callback<weatherfetch>() {
            @Override
            public void onResponse(Call<weatherfetch> call, Response<weatherfetch> response) {
                if(response.code()==404){
                    Toast.makeText(MainActivity.this,"city not found.",Toast.LENGTH_LONG).show();
                }
                else if(!(response.isSuccessful())){
                    Toast.makeText(MainActivity.this,response.code()+" ",Toast.LENGTH_LONG).show();
                    return;
                }
                weatherfetch mydata=response.body();
                Main main=mydata.getMain();
                float temp=main.getTemp();
                float pres=main.getPressure();
                float hum=main.getHumidity();
                Integer temperature=(int)(temp-273.15);
                Integer pressure=(int)(pres);
                Integer humadity=(int)(hum);
                tempo.setText(String.valueOf(temperature)+"C");
                press.setText(String.valueOf(pressure)+"mb");
                humad.setText(String.valueOf(humadity)+ "%");
            }

            @Override
            public void onFailure(Call<weatherfetch> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }




    public void signout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}




