package com.example.belajarretrofit.Activity;

import androidx.appcompat.app.AppCompatActivity;

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

public class TambahActivity extends AppCompatActivity {

    private EditText edtnis, edtnama, edtkelas, edtketerangan;
    private Button btnsimpan;
    private String nis,nama,kelas,keterangan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        edtnis = findViewById(R.id.edt_nis);
        edtnama = findViewById(R.id.edt_nama);
        edtkelas = findViewById(R.id.edt_kelas);
        edtketerangan = findViewById(R.id.edt_keterangan);
        btnsimpan = findViewById(R.id.btn_simpan);

        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nis = edtnis.getText().toString();
                nama = edtnama.getText().toString();
                kelas = edtkelas.getText().toString();
                keterangan = edtketerangan.getText().toString();

                if (nis.trim().equals("")){
                    edtnis.setError("Nis tidak boleh kosong!");
                }else if(nama.trim().equals("")){
                    edtnama.setError("Nama tidak boleh kosong!");
                }else if ((kelas.trim().equals(""))){
                    edtkelas.setError("Kelas tidak boleh kosong!");
                }else if(keterangan.trim().equals("")){
                    edtketerangan.setError("Keterangan tidak boleh kosong");
                }else {
                    createData();
                }
            }
        });
    }

    private void createData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> simpandata = ardData.ardCreateData(nis,nama,kelas,keterangan);

        simpandata.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "Kode : " + kode + "| Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal Menghubungi Server" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}