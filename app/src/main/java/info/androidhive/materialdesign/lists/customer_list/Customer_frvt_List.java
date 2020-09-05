package info.androidhive.materialdesign.lists.customer_list;

/**
 * Created by hp on 2/16/2018.
 */

public class Customer_frvt_List {


    private String business_id;
    private String name;
    private String number;
    private String image;
    private String type;


    public Customer_frvt_List
    (String business_id, String name, String number, String image, String type){

        this.business_id = business_id;
        this.name   = name;
        this.number = number;
        this.image  = image;
        this.type   = type;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }
}
