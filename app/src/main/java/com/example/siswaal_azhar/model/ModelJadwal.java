package com.example.siswaal_azhar.model;

public class ModelJadwal {



        /**
         * id_jadwal_ujian : 1
         * id_jam_ajar : 1
         * id_kelas : 1
         * id_pelajaran : 1
         * id_guru : 4
         * hari_jadwal : Senin
         * id_jam : 1
         * jam_awal : 07:30:00
         * jam_akhir : 08:20:00
         * nama_kelas : 1
         * grup_kelas : A
         * nama_pelajaran : Matematika
         * id : 4
         * nip : 183192221923
         * nama_peg : Agus Sudirman
         * jabatan_id : 3
         * Email : INDRI_RAHMAYUNI@gmail.com
         * no_tlp : 08230129
         * alamat : Padang
         * tgl_masuk : 2020-10-05
         * tmp_lahir : Padang
         * agama : Islam
         * pendidikan : S2
         * foto : a.jpg
         */

        private String id_jadwal_ujian;
        private String id_jam_ajar;
        private String id_kelas;
        private String id_pelajaran;
        private String id_guru;
        private String hari_jadwal;
        private String id_jam;
        private String jam_awal;
        private String jam_akhir;
        private String nama_kelas;
        private String grup_kelas;
        private String nama_pelajaran;
        private String id;
        private String nip;
        private String nama_peg;
        private String jabatan_id;
        private String Email;
        private String no_tlp;
        private String alamat;
        private String tgl_masuk;
        private String tmp_lahir;
        private String agama;
        private String pendidikan;
        private String foto;

    public ModelJadwal(String id_jadwal_ujian, String id_jam_ajar, String id_kelas, String id_pelajaran, String id_guru, String hari_jadwal, String id_jam, String jam_awal, String jam_akhir, String nama_kelas, String grup_kelas, String nama_pelajaran, String id, String nip, String nama_peg, String jabatan_id, String email, String no_tlp, String alamat, String tgl_masuk, String tmp_lahir, String agama, String pendidikan, String foto) {
        this.id_jadwal_ujian = id_jadwal_ujian;
        this.id_jam_ajar = id_jam_ajar;
        this.id_kelas = id_kelas;
        this.id_pelajaran = id_pelajaran;
        this.id_guru = id_guru;
        this.hari_jadwal = hari_jadwal;
        this.id_jam = id_jam;
        this.jam_awal = jam_awal;
        this.jam_akhir = jam_akhir;
        this.nama_kelas = nama_kelas;
        this.grup_kelas = grup_kelas;
        this.nama_pelajaran = nama_pelajaran;
        this.id = id;
        this.nip = nip;
        this.nama_peg = nama_peg;
        this.jabatan_id = jabatan_id;
        Email = email;
        this.no_tlp = no_tlp;
        this.alamat = alamat;
        this.tgl_masuk = tgl_masuk;
        this.tmp_lahir = tmp_lahir;
        this.agama = agama;
        this.pendidikan = pendidikan;
        this.foto = foto;
    }

    public String getId_jadwal_ujian() {
            return id_jadwal_ujian;
        }

        public void setId_jadwal_ujian(String id_jadwal_ujian) {
            this.id_jadwal_ujian = id_jadwal_ujian;
        }

        public String getId_jam_ajar() {
            return id_jam_ajar;
        }

        public void setId_jam_ajar(String id_jam_ajar) {
            this.id_jam_ajar = id_jam_ajar;
        }

        public String getId_kelas() {
            return id_kelas;
        }

        public void setId_kelas(String id_kelas) {
            this.id_kelas = id_kelas;
        }

        public String getId_pelajaran() {
            return id_pelajaran;
        }

        public void setId_pelajaran(String id_pelajaran) {
            this.id_pelajaran = id_pelajaran;
        }

        public String getId_guru() {
            return id_guru;
        }

        public void setId_guru(String id_guru) {
            this.id_guru = id_guru;
        }

        public String getHari_jadwal() {
            return hari_jadwal;
        }

        public void setHari_jadwal(String hari_jadwal) {
            this.hari_jadwal = hari_jadwal;
        }

        public String getId_jam() {
            return id_jam;
        }

        public void setId_jam(String id_jam) {
            this.id_jam = id_jam;
        }

        public String getJam_awal() {
            return jam_awal;
        }

        public void setJam_awal(String jam_awal) {
            this.jam_awal = jam_awal;
        }

        public String getJam_akhir() {
            return jam_akhir;
        }

        public void setJam_akhir(String jam_akhir) {
            this.jam_akhir = jam_akhir;
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

        public String getNama_pelajaran() {
            return nama_pelajaran;
        }

        public void setNama_pelajaran(String nama_pelajaran) {
            this.nama_pelajaran = nama_pelajaran;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNip() {
            return nip;
        }

        public void setNip(String nip) {
            this.nip = nip;
        }

        public String getNama_peg() {
            return nama_peg;
        }

        public void setNama_peg(String nama_peg) {
            this.nama_peg = nama_peg;
        }

        public String getJabatan_id() {
            return jabatan_id;
        }

        public void setJabatan_id(String jabatan_id) {
            this.jabatan_id = jabatan_id;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public String getNo_tlp() {
            return no_tlp;
        }

        public void setNo_tlp(String no_tlp) {
            this.no_tlp = no_tlp;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getTgl_masuk() {
            return tgl_masuk;
        }

        public void setTgl_masuk(String tgl_masuk) {
            this.tgl_masuk = tgl_masuk;
        }

        public String getTmp_lahir() {
            return tmp_lahir;
        }

        public void setTmp_lahir(String tmp_lahir) {
            this.tmp_lahir = tmp_lahir;
        }

        public String getAgama() {
            return agama;
        }

        public void setAgama(String agama) {
            this.agama = agama;
        }

        public String getPendidikan() {
            return pendidikan;
        }

        public void setPendidikan(String pendidikan) {
            this.pendidikan = pendidikan;
        }

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }

}
