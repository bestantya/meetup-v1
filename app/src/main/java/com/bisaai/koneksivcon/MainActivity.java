package com.bisaai.koneksivcon;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bisaai.koneksivcon.databinding.ActivityMainBinding;
import com.bisaai.vconconnect.*;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private String test;
    private  String urlRoom;
    private  String urlInv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Koneksi.CreateRoom("Hola Amigos");
//                Log.d("Create Room", Koneksi.GenerateUrlRoom("Anisa", "30002"));
//                Log.d("Join Room", Koneksi.StartMeeting("Anisa","30002"));
//                Log.d("Link Invitation", Koneksi.SendInvitation("Anisa", "Ilham", "30002"));
//                Log.d("End Room", Koneksi.EndMeeting("Anisa", "30002"));
//                Log.d("WKKKKKKKK ", Koneksi.status( Koneksi.GenerateUrlRoom("Anisa", "30002") ));
//                Koneksi.status( Koneksi.GenerateUrlRoom("Anisa", "30002") );
                    test = Koneksi.status( Koneksi.GenerateUrlRoom("Amika", "30002") );
                if (test == "SUCCESS"){
                    Log.d("Sukses Konek", "Siuuuuuu " );

                } else {
                    Log.e("Err", "onClick: "+test+"aaa" );

                }
                urlRoom = Koneksi.StartMeeting("Amika", "30002");
                Intent intent = new Intent(getApplicationContext(), Calling.class);
                intent.putExtra("URL", urlRoom);
                startActivity(intent);

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}