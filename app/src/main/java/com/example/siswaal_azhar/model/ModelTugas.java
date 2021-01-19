package com.example.siswaal_azhar.model;

public class ModelTugas {


        /**
         * id_tugas : 1
         * id_pelajaran : 1
         * id_kelas : 1
         * judul_tugas : Buat Makalah Persamaan
         * isi_tugas : buatkan makalah seperti di bawah
         * file_tugas : mtk1.pdf
         * deadline_tugas : 2021-01-20
         * nama_pelajaran : Matematika
         */

        private String id_tugas;
        private String id_pelajaran;
        private String id_kelas;
        private String judul_tugas;
        private String isi_tugas;
        private String file_tugas;
        private String deadline_tugas;
        private String nama_pelajaran;

    public ModelTugas(String id_tugas, String id_pelajaran, String id_kelas, String judul_tugas, String isi_tugas, String file_tugas, String deadline_tugas, String nama_pelajaran) {
        this.id_tugas = id_tugas;
        this.id_pelajaran = id_pelajaran;
        this.id_kelas = id_kelas;
        this.judul_tugas = judul_tugas;
        this.isi_tugas = isi_tugas;
        this.file_tugas = file_tugas;
        this.deadline_tugas = deadline_tugas;
        this.nama_pelajaran = nama_pelajaran;
    }

    public String getId_tugas() {
            return id_tugas;
        }

        public void setId_tugas(String id_tugas) {
            this.id_tugas = id_tugas;
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

        public String getJudul_tugas() {
            return judul_tugas;
        }

        public void setJudul_tugas(String judul_tugas) {
            this.judul_tugas = judul_tugas;
        }

        public String getIsi_tugas() {
            return isi_tugas;
        }

        public void setIsi_tugas(String isi_tugas) {
            this.isi_tugas = isi_tugas;
        }

        public String getFile_tugas() {
            return file_tugas;
        }

        public void setFile_tugas(String file_tugas) {
            this.file_tugas = file_tugas;
        }

        public String getDeadline_tugas() {
            return deadline_tugas;
        }

        public void setDeadline_tugas(String deadline_tugas) {
            this.deadline_tugas = deadline_tugas;
        }

        public String getNama_pelajaran() {
            return nama_pelajaran;
        }

        public void setNama_pelajaran(String nama_pelajaran) {
            this.nama_pelajaran = nama_pelajaran;
        }

}
