package com.example.belajarretrofit.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.belajarretrofit.API.APIRequestData;
import com.example.belajarretrofit.API.RetroServer;
import com.example.belajarretrofit.Activity.MainActivity;
import com.example.belajarretrofit.Activity.UbahActivity;
import com.example.belajarretrofit.Model.DataModel;
import com.example.belajarretrofit.Model.ResponseModel;
import com.example.belajarretrofit.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{
    private Context ctx;
    private List<DataModel> listSiswa;
    private List<DataModel> listData;
    private String nis;

    public AdapterData(Context ctx, List<DataModel> listSiswa){
        this.ctx = ctx;
        this.listSiswa = listSiswa;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dm = listSiswa.get(position);
        holder.tvNis.setText(dm.getNis());
        holder.tvNama.setText(dm.getNama());
        holder.tvKelas.setText(dm.getKelas());
        holder.tvKeterangan.setText(dm.getKeterangan());
    }

    @Override
    public int getItemCount() {
        return listSiswa.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvNis, tvNama, tvKelas, tvKeterangan;
        public HolderData(@NonNull View itemView){
            super(itemView);
            tvNis = itemView.findViewById(R.id.tv_nis);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvKelas = itemView.findViewById(R.id.tv_kelas);
            tvKeterangan = itemView.findViewById(R.id.tv_keterangan);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Pilih operasi yang akan dilakukan");
                    dialogPesan.setCancelable(true);
                    nis = tvNis.getText().toString();
                    dialogPesan.setTitle("Perhatian");
                    dialogPesan.setIcon(R.mipmap.ic_launcher);

                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            deleteData();
                            dialog.dismiss();
                            Handler handler = new android.os.Handler();
                            handler.postDelayed(new Runnable(){
                                @Override
                                public void run() {
                                    ((MainActivity) ctx).retrieveData();
                                }
                            },250);

                        }
                    });

                    dialogPesan.setNegativeButton("ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getData();
                            dialog.dismiss();
                        }
                    });

                    dialogPesan.show();
                    return false;
                }
            });
        }

        private void deleteData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> hapusData = ardData.ardDeleteData(nis);

            hapusData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    Toast.makeText(ctx, "Kode : " + kode+"| Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void getData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> ambilData = ardData.ardgetData(nis);

            ambilData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    listData = response.body().getData();

                    String varNis = listData.get(0).getNis();
                    String varNama = listData.get(0).getNama();
                    String varKelas = listData.get(0).getKelas();
                    String varKeterangan = listData.get(0).getKeterangan();

                    Intent intent = new Intent(ctx, UbahActivity.class);
                    intent.putExtra("xnim", varNis);
                    intent.putExtra("xnama",varNama);
                    intent.putExtra("xkelas", varKelas);
                    intent.putExtra("xketerangan", varKeterangan);
                    ctx.startActivity(intent);
//                    Toast.makeText(ctx, "Kode : " + kode+"| Pesan : " +pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
