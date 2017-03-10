package glory.golek.Kelas;

/**
 * Created by Glory on 07-Mar-17.
 */

public class IsiDataKomunitas {

    String nama;

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    String tipe;

    public IsiDataKomunitas(String nama, String admin, String id, String alamat, Double lat, Double lon, String nope, String email, String gambar, String tagline,String tipe) {
        this.nama = nama;
        this.admin = admin;
        this.id = id;
        this.alamat = alamat;
        this.lat = lat;
        this.lon = lon;
        this.nope = nope;
        this.email = email;
        this.gambar = gambar;
        this.tagline = tagline;
        this.tipe = tipe;
    }

    public String getNama() {

        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getNope() {
        return nope;
    }

    public void setNope(String nope) {
        this.nope = nope;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    String admin;
    String id;
    String alamat;
    Double lat;
    Double lon;
    String nope;
    String email;
    String gambar;
    String tagline;
}
