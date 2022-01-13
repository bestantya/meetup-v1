package com.bisaai.koneksivcon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bisaai.koneksivcon.databinding.ActivityMainBinding;
import com.bisaai.vconconnect.Koneksi;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    private  String test;
    private  String urlRoom;
    private  String urlInv;
    private String nama = "Anisa";
    private String namaTujuan = "Arman";
    private String idCaller = "30002";
    private String ed1 = "";
    EditText nama_inv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView txtUrl = (TextView) findViewById(R.id.txturl);
        nama_inv = (EditText) findViewById(R.id.EditNama);

        Button btnShow = (Button) findViewById(R.id.btnShow);
        Button btnEnd = (Button) findViewById(R.id.btnEnd);
        Button btnInv = (Button) findViewById(R.id.btnInvite);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test = Koneksi.status( Koneksi.GenerateUrlRoom(nama, idCaller) );
                if (test == "SUCCESS"){
                    Log.d("Status", "Berhasil Konek " );

                } else {
                    Log.e("Err", "Gagal" );

                }

                urlRoom = Koneksi.StartMeeting(nama, idCaller);

                txtUrl.setText(urlRoom);
                Intent intent = new Intent(getApplicationContext(), Calling.class);
                intent.putExtra("URL", urlRoom);
                startActivity(intent);
            }
        });

        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test = Koneksi.status( Koneksi.EndMeeting( nama, idCaller ) );
                urlRoom = Koneksi.StartMeeting(nama, idCaller);
                if (test == "SUCCESS"){
                    Log.d("Sukses Konek", "Siuuuuuu " );

                } else {
                    Log.e("Err", "onClick: "+test+"aaa" );

                }

                txtUrl.setText(urlRoom);
                Toast.makeText(getApplicationContext(), "Berhasil Out", Toast.LENGTH_LONG).show();
            }
        });

        btnInv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ed1 = nama_inv.getText().toString();
                if (!ed1.isEmpty()){
                    namaTujuan = ed1;
                }
                Log.e("Nama Tujuan", namaTujuan);

                urlRoom = Koneksi.SendInvitation(namaTujuan, idCaller);
                txtUrl.setText(urlRoom);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlRoom));
                startActivity(browserIntent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}