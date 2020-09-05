package info.androidhive.materialdesign.lists;

/**
 * Created by hp on 2/16/2018.
 */

public class Monshi_Customers_List {


    private String id;
    private String name;
    private String appointment_id;
    private String service_name;
    private String staff_name;


    public Monshi_Customers_List
    (String id,String name, String appointment_id, String service_name, String staff_name) {
        this.  id =   id;
        this.  name =   name;
        this.appointment_id = appointment_id;
        this.service_name = service_name;
        this.staff_name = staff_name;

    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
