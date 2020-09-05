package info.androidhive.materialdesign.lists;

/**
 * Created by hp on 2/16/2018.
 */

public class Fav_List {

    private String id;
    private String fav_id;
    private String type;
    private String business_id;
    private String staff_id;
    private String contact;
    private String name;
    private String description;
    private String image;


    public Fav_List(String id  ,String  fav_id, String type, String business_id, String staff_id, String contact,
                    String name, String description, String image) {

        this.id  = id;
        this.fav_id  = fav_id;
        this.type  = type;
        this.business_id  = business_id;
        this.staff_id  = staff_id;
        this.contact  = contact;
        this.name  = name;
        this.description  = description;
        this.image = image;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFav_id() {
        return fav_id;
    }

    public void setFav_id(String fav_id) {
        this.fav_id = fav_id;
    }
}
