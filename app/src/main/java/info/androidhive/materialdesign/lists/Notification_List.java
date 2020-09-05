package info.androidhive.materialdesign.lists;

/**
 * Created by hp on 2/16/2018.
 */

public class Notification_List {


    private String id;
    private String notification;


    public Notification_List(String id, String notification) {

        this.id      = id;
        this.notification = notification;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
