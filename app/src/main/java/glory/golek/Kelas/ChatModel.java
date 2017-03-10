package glory.golek.Kelas;

/**
 * Created by Glory on 06-Mar-17.
 */

public class ChatModel {

    String from_id;
    String to_id;
    String pesan;
    String time;

    public ChatModel(String from_id, String to_id, String pesan, String time) {
        this.from_id = from_id;
        this.to_id = to_id;
        this.pesan = pesan;
        this.time = time;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
