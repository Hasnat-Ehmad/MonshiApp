package info.androidhive.materialdesign.lists;

import org.json.JSONArray;

/**
 * Created by hp on 2/16/2018.
 */

public class Class_Session_List {

    private String session_id;
    private String classname;
    private String to_date_per;
    private String from_date_per;
    private String teacher_name;
    private JSONArray session_day_list;
    private String classname_temp;
    private String classid_temp;


    public Class_Session_List
    (String session_id,String classname,String classname_temp, String classid_temp, String to_date_per, String from_date_per,String teacher_name,JSONArray session_day_list){

        this.session_id   =   session_id;
        this.classname    =   classname;
        this.classname_temp = classname_temp;
        this.to_date_per  = to_date_per;
        this.from_date_per= from_date_per;
        this.teacher_name = teacher_name;
        this.classid_temp = classid_temp;
        this.session_day_list  = session_day_list;


    }


    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getTo_date_per() {
        return to_date_per;
    }

    public void setTo_date_per(String to_date_per) {
        this.to_date_per = to_date_per;
    }

    public String getFrom_date_per() {
        return from_date_per;
    }

    public void setFrom_date_per(String from_date_per) {
        this.from_date_per = from_date_per;
    }

    public JSONArray getSession_day_list() {
        return session_day_list;
    }

    public void setSession_day_list(JSONArray session_day_list) {
        this.session_day_list = session_day_list;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getClassname_temp() {
        return classname_temp;
    }

    public void setClassname_temp(String classname_temp) {
        this.classname_temp = classname_temp;
    }

    public String getClassid_temp() {
        return classid_temp;
    }

    public void setClassid_temp(String classid_temp) {
        this.classid_temp = classid_temp;
    }
}
