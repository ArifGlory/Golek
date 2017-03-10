package glory.golek.Kelas;

/**
 * Created by Glory on 28-Feb-17.
 */

public class IsiDataUser {

    String nama;
    String password;
    String id;
    String alamat;
    Double lat;
    Double lon;
    String nope;
    String email;
    String gambar;
    String pm;

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }


    public IsiDataUser(String nama, String password, String id, String alamat, Double lat, Double lon, String nope, String email,String gambar,String pm) {
        this.nama = nama;
        this.password = password;
        this.id = id;
        this.alamat = alamat;
        this.lat = lat;
        this.lon = lon;
        this.nope = nope;
        this.email = email;
        this.gambar = gambar;
        this.pm = pm;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
