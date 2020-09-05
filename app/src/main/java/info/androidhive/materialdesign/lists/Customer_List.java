package info.androidhive.materialdesign.lists;

/**
 * Created by hp on 2/16/2018.
 */

public class Customer_List {

    private String id;
    private String user_id;
    private String business_id;
    private String booked_for;
    private String customer_image;
    private String name;
    private String email;
    private String number;
    private String customer_address;


    public Customer_List(String id  ,String user_id,String business_id,String booked_for,String customer_image,
                         String name, String email, String number,String customer_address) {

        this.id  = id;
        this.user_id  = user_id;
        this.business_id  = business_id;
        this.booked_for  = booked_for;
        this.customer_image  = customer_image;

        this.name  = name;
        this.email  = email;
        this.number = number;

        this.customer_address = customer_address;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getBooked_for() {
        return booked_for;
    }

    public void setBooked_for(String booked_for) {
        this.booked_for = booked_for;
    }

    public String getCustomer_image() {
        return customer_image;
    }

    public void setCustomer_image(String customer_image) {
        this.customer_image = customer_image;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }
}
