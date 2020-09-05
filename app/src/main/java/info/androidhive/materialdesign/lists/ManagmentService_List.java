package info.androidhive.materialdesign.lists;

/**
 * Created by hp on 2/16/2018.
 */

public class ManagmentService_List {

    private String servicename;
    private String amount;
    private String time;
    private String image;
    private String id;
    private String select;
    private String description;


    public ManagmentService_List(String id,String servicename,String amount,String time,String image, String select,String description) {

        this.  servicename =   servicename;
        this.  amount =   amount;
        this.  time   =   time;
        this.  image  =   image;
        this.  id     =   id;
        this.  select =   select;
        this.  description =   description;
    }


    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
