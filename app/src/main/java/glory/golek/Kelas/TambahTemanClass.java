package glory.golek.Kelas;

/**
 * Created by Glory on 01-Mar-17.
 */

public class TambahTemanClass {
    String id;
    String nama;
    String key;

    public TambahTemanClass(String id, String nama, String key) {
        this.id = id;
        this.nama = nama;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
