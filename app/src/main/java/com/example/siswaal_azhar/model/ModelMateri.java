package com.example.siswaal_azhar.model;

public class ModelMateri {


    private String id_materi;
    private String id_pelajaran;
    private String id_kelas;
    private String materi_pelajaran;
    private String nama_pelajaran;

    public ModelMateri(String id_materi, String id_pelajaran, String id_kelas, String materi_pelajaran, String nama_pelajaran) {
        this.id_materi = id_materi;
        this.id_pelajaran = id_pelajaran;
        this.id_kelas = id_kelas;
        this.materi_pelajaran = materi_pelajaran;
        this.nama_pelajaran = nama_pelajaran;
    }

    public String getId_materi() {
        return id_materi;
    }

    public void setId_materi(String id_materi) {
        this.id_materi = id_materi;
    }

    public String getId_pelajaran() {
        return id_pelajaran;
    }

    public void setId_pelajaran(String id_pelajaran) {
        this.id_pelajaran = id_pelajaran;
    }

    public String getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(String id_kelas) {
        this.id_kelas = id_kelas;
    }

    public String getMateri_pelajaran() {
        return materi_pelajaran;
    }

    public void setMateri_pelajaran(String materi_pelajaran) {
        this.materi_pelajaran = materi_pelajaran;
    }

    public String getNama_pelajaran() {
        return nama_pelajaran;
    }

    public void setNama_pelajaran(String nama_pelajaran) {
        this.nama_pelajaran = nama_pelajaran;
    }

}
