package glory.golek.Kelas;

/**
 * Created by Glory on 04-Mar-17.
 */

public class IsiDataTimeline {

    String id;
    String key;
    String pm;
    String gambar;
    String status;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    String nama;

    public IsiDataTimeline(String id, String key, String pm, String gambar, String status,String nama) {
        this.id = id;
        this.key = key;
        this.pm = pm;
        this.gambar = gambar;
        this.status = status;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
