package info.androidhive.materialdesign.lists.customer_list;

/**
 * Created by hp on 2/16/2018.
 */

public class Customer_Activity_List {


    private String appt_id;
    private String bus_id;
    private String service_id;
    private String customer_id;
    private String staff_id;
    private String end_event;
    private String business_name;
    private String staff_name;
    private String service_name;
    private String customer_name;
    private String description;
    private String amount;
    private String rating;
    private String rated;

    public Customer_Activity_List
    (String appt_id, String bus_id, String service_id, String customer_id, String staff_id, String end_event,
     String business_name,String staff_name,String service_name,String customer_name, String description,String amount,
     String rating,String rated) {

        this.appt_id    = appt_id;
        this.bus_id     = bus_id;
        this.service_id = service_id;
        this.customer_id= customer_id;
        this.staff_id   = staff_id;
        this. end_event = end_event;
        this.business_name= business_name;
        this.staff_name   = staff_name;
        this.service_name = service_name;
        this.customer_name= customer_name;
        this.description  = description;
        this.amount = amount;
        this.rating = rating;
        this.rated  = rated;
    }

    public String getAppt_id() {
        return appt_id;
    }

    public void setAppt_id(String appt_id) {
        this.appt_id = appt_id;
    }

    public String getBus_id() {
        return bus_id;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getEnd_event() {
        return end_event;
    }

    public void setEnd_event(String end_event) {
        this.end_event = end_event;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRated() {
        return rated;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }
}
