package com.example.belajarretrofit.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.belajarretrofit.API.APIRequestData;
import com.example.belajarretrofit.API.RetroServer;
import com.example.belajarretrofit.Model.ResponseModel;
import com.example.belajarretrofit.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {

    private String xnis, xnama, xkelas, xketerangan;
    private EditText edtnis, edtnama, edtkelas, edtketerangan;
    private Button btnUbah;
    private String nis, nama, kelas, keterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        Intent terima = getIntent();
        xnis = terima.getStringExtra("xnis");
        xnama = terima.getStringExtra("xnama");
        xkelas = terima.getStringExtra("xkelas");
        xketerangan = terima.getStringExtra("xketerangan");

        edtnis = findViewById(R.id.edt_nis);
        edtnama = findViewById(R.id.edt_nama);
        edtkelas = findViewById(R.id.edt_kelas);
        edtketerangan = findViewById(R.id.edt_keterangan);
        btnUbah = findViewById(R.id.btn_ubah);

        edtnis.setText(xnis);
        edtnama.setText(xnama);
        edtkelas.setText(xkelas);
        edtketerangan.setText(xketerangan);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nis = edtnis.getText().toString();
                nama = edtnama.getText().toString();
                kelas = edtkelas.getText().toString();
                keterangan = edtketerangan.getText().toString();
                updateData();
            }
        });

    }

    private void updateData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> ubahdata = ardData.ardUpdateData(nis,nama,kelas,keterangan);

        ubahdata.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "Kode : " + kode + "| Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Gagal Menghubungi Server" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}