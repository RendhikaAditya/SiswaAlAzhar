package com.example.siswaal_azhar.model;

public class ModelRapor {


        private String id_lapor;
        private String id_siswa;
        private String id_kelas;
        private String file;
        private String id_semester;
        private String nama_kelas;
        private String grup_kelas;

    public ModelRapor(String id_lapor, String id_siswa, String id_kelas, String file, String id_semester, String nama_kelas, String grup_kelas) {
        this.id_lapor = id_lapor;
        this.id_siswa = id_siswa;
        this.id_kelas = id_kelas;
        this.file = file;
        this.id_semester = id_semester;
        this.nama_kelas = nama_kelas;
        this.grup_kelas = grup_kelas;
    }

    public String getId_lapor() {
        return id_lapor;
    }

    public void setId_lapor(String id_lapor) {
        this.id_lapor = id_lapor;
    }

    public String getId_siswa() {
        return id_siswa;
    }

    public void setId_siswa(String id_siswa) {
        this.id_siswa = id_siswa;
    }

    public String getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(String id_kelas) {
        this.id_kelas = id_kelas;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getId_semester() {
        return id_semester;
    }

    public void setId_semester(String id_semester) {
        this.id_semester = id_semester;
    }

    public String getNama_kelas() {
        return nama_kelas;
    }

    public void setNama_kelas(String nama_kelas) {
        this.nama_kelas = nama_kelas;
    }

    public String getGrup_kelas() {
        return grup_kelas;
    }

    public void setGrup_kelas(String grup_kelas) {
        this.grup_kelas = grup_kelas;
    }
}
