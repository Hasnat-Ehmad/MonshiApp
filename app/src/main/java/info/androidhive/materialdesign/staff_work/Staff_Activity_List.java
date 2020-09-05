package info.androidhive.materialdesign.staff_work;

/**
 * Created by hp on 2/16/2018.
 */

public class Staff_Activity_List {


    private String jalali_date;
    private String service_name;
    private String customer_name;
    private String description;
    private String amount;
    private String isRated;
    private String rating;


    public Staff_Activity_List
    (String jalali_date, String service_name, String customer_name, String description, String amount, String isRated,
     String rating
    ) {

        this.jalali_date  = jalali_date;
        this.service_name = service_name;
        this.customer_name= customer_name;
        this.description  = description;
        this.amount       = amount;
        this.isRated      = isRated;
        this.rating       = rating;
    }


    public String getJalali_date() {
        return jalali_date;
    }

    public void setJalali_date(String jalali_date) {
        this.jalali_date = jalali_date;
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

    public String getIsRated() {
        return isRated;
    }

    public void setIsRated(String isRated) {
        this.isRated = isRated;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
