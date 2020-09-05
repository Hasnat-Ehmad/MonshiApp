package info.androidhive.materialdesign.lists;

public class Message_List {

    private String image;
    private String name;
    private String date_time;
    private String message;
    private String sender;

    public Message_List(String image  , String  name, String date_time, String message, String sender) {

        this.image  = image;
        this.name  = name;
        this.date_time  = date_time;
        this.message  = message;
        this.sender  = sender;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
