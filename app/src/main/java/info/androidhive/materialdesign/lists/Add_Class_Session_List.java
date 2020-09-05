package info.androidhive.materialdesign.lists;

/**
 * Created by hp on 2/16/2018.
 */

public class Add_Class_Session_List {


    private String session_id;
    private String s_classid;
    private String s_staff_id;
    private String booking_total;
    private String s_slot;
    private String s_cost;
    private String date;


    public Add_Class_Session_List
    (String session_id, String s_classid,String s_staff_id, String booking_total, String s_slot, String s_cost, String date) {

        this.session_id    = session_id;
        this.s_classid     = s_classid;
        this.s_staff_id     = s_staff_id;
        this.booking_total = booking_total;
        this.s_slot = s_slot;
        this.s_cost = s_cost;
        this.date   = date;

    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getS_classid() {
        return s_classid;
    }

    public void setS_classid(String s_classid) {
        this.s_classid = s_classid;
    }

    public String getBooking_total() {
        return booking_total;
    }

    public void setBooking_total(String booking_total) {
        this.booking_total = booking_total;
    }

    public String getS_slot() {
        return s_slot;
    }

    public void setS_slot(String s_slot) {
        this.s_slot = s_slot;
    }

    public String getS_cost() {
        return s_cost;
    }

    public void setS_cost(String s_cost) {
        this.s_cost = s_cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getS_staff_id() {
        return s_staff_id;
    }

    public void setS_staff_id(String s_staff_id) {
        this.s_staff_id = s_staff_id;
    }
}
