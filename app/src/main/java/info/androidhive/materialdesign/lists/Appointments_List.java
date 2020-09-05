package info.androidhive.materialdesign.lists;

/**
 * Created by hp on 2/16/2018.
 */

public class Appointments_List {


    private String id;
    private String title;
    private String start;
    private String end;
    private String business_id;
    private String customer_id;
    private String staff_id;
    private String staff_color;
    private String user_id;
    private String eStart;
    private String eEnd;
    private String staff;
    private String customer;
    private String business;
    private String business_image;
    private String service;
    private String service_id;
    private String editable;
    private String service_time;
    private String check_multiple_staff;

    public Appointments_List
    (String id, String title, String start, String end, String business_id, String customer_id,
     String staff_id, String staff_color,String user_id, String eStart, String eEnd, String staff,
     String customer, String business,String service,String business_image, String service_id, String editable, String service_time,String check_multiple_staff
    ) {

        this.  id  =   id;
        this.title = title;
        this.start = start;
        this.end   = end;
        this.business_id = business_id;
        this. customer_id =  customer_id;
        this.staff_id =  staff_id;
        this.staff_color =  staff_color;
        this.  user_id =   user_id;
        this.eStart = eStart;
        this.eEnd = eEnd;
        this.staff = staff;
        this.customer = customer;
        this. business =  business;
        this.service =  service;
        this.business_image =  business_image;
        this.service_id =  service_id;
        this.editable =  editable;
        this.service_time =  service_time;
        this.check_multiple_staff =  check_multiple_staff;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
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

    public String getStaff_color() {
        return staff_color;
    }

    public void setStaff_color(String staff_color) {
        this.staff_color = staff_color;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String geteStart() {
        return eStart;
    }

    public void seteStart(String eStart) {
        this.eStart = eStart;
    }

    public String geteEnd() {
        return eEnd;
    }

    public void seteEnd(String eEnd) {
        this.eEnd = eEnd;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getCheck_multiple_staff() {
        return check_multiple_staff;
    }

    public void setCheck_multiple_staff(String check_multiple_staff) {
        this.check_multiple_staff = check_multiple_staff;
    }

    public String getBusiness_image() {
        return business_image;
    }

    public void setBusiness_image(String business_image) {
        this.business_image = business_image;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }
}
