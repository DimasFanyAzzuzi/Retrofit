<?php
require("koneksi.php");

$response = array();

if($_SERVER['REQUEST_METHOD'] == 'POST'){
    $nim = $_POST["nim"];

    $query = "DELETE FROM tb_mahasiswa WHERE nim = '$nim'";
    $eksekusi = mysqli_query($konek,$query);
    $cek = mysqli_affected_rows($konek);

    if($cek > 0){
        $response["kode"] = 1;
        $response["pesan"] = "Berhasil";
    }else{
        $response["kode"] = 0;
        $response["pesan"] = "Gagal";
    }
}else{
    $response["kode"] = 0;
    $response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($konek);
?>