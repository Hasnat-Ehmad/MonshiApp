package info.androidhive.materialdesign.lists;

/**
 * Created by hp on 2/16/2018.
 */

public class Dashboard_List {


    private String end_event;
    private String appointment_id;
    private String service_name;
    private String staff_name;
    private String booked_by;
    private String is_rated;
    private String rating;
    private String status;

    public Dashboard_List
    (String end_event, String appointment_id, String service_name, String staff_name, String booked_by, String is_rated,
     String rating, String status
    ) {

        this.  end_event =   end_event;
        this.appointment_id = appointment_id;
        this.service_name = service_name;
        this.staff_name = staff_name;
        this.booked_by = booked_by;
        this. is_rated =  is_rated;
        this.rating =  rating;
        this.status =  status;
    }

    public String getEnd_event() {
        return end_event;
    }

    public void setEnd_event(String end_event) {
        this.end_event = end_event;
    }

    public String getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(String appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getBooked_by() {
        return booked_by;
    }

    public void setBooked_by(String booked_by) {
        this.booked_by = booked_by;
    }

    public String getIs_rated() {
        return is_rated;
    }

    public void setIs_rated(String is_rated) {
        this.is_rated = is_rated;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
