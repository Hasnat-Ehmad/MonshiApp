package info.androidhive.materialdesign.lists;

/**
 * Created by hp on 2/16/2018.
 */

public class ManagmentStaff_List {



    private String id;
    private String username;
    private String email;
    private String mobile;
    private String rating;
    private String image;

    public ManagmentStaff_List(String id,String username,String email,String mobile,String rating,String image) {

        this.id =   id;
        this.username =   username;
        this.email    =   email;
        this.mobile   =   mobile;
        this.rating   =   rating;
        this.image    =   image;

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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
}
