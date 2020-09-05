package info.androidhive.materialdesign.lists;

/**
 * Created by hp on 2/16/2018.
 */

public class ManagmentClass_List {

    private String classname;
    private String amount;
    private String time;
    private String image;
    private String id;
    private String desc;
    private String screen_check;


    public ManagmentClass_List(String id, String classname, String amount, String time, String image, String desc, String screen_check) {

        this.  classname =   classname;
        this.  amount    =   amount;
        this.  time      =   time;
        this.  image     =   image;
        this.  id        =   id;
        this.  desc      =   desc;
        this.screen_check=   screen_check;
    }


    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getScreen_check() {
        return screen_check;
    }

    public void setScreen_check(String screen_check) {
        this.screen_check = screen_check;
    }
}
