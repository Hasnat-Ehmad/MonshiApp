package info.androidhive.materialdesign.lists;

/**
 * Created by hp on 2/16/2018.
 */

public class BillHistory_List {

    private boolean colorcheck;
    private String tag_name;
    private String job_number;
    private String job_detail;
    private String job_status;
    private String job_start;
    private String later_date_time;

    public BillHistory_List(String job_start, String tag_name, String job_number, String job_detail, String job_status, String later_date_time/*,boolean colorcheck*/) {

        this.  tag_name =   tag_name;
        this.job_number = job_number;
        this.colorcheck = colorcheck;
        this.job_detail = job_detail;
        this.job_status = job_status;
        this. job_start =  job_start;
       this.later_date_time =  later_date_time;
    }




    public String getJob_number() {
        return job_number;
    }

    public void setJob_number(String job_number) {
        this.job_number = job_number;
    }

    public boolean getColorcheck() {
        return colorcheck;
    }

    public void setColorcheck(boolean colorcheck) {
        this.colorcheck = colorcheck;
    }

    public String getJob_detail() {
        return job_detail;
    }

    public void setJob_detail(String job_detail) {
        this.job_detail = job_detail;
    }

    public String getJob_status() {
        return job_status;
    }

    public void setJob_status(String job_status) {
        this.job_status = job_status;
    }

    public String getJob_start() {
        return job_start;
    }

    public void setJob_start(String job_start) {
        this.job_start = job_start;
    }

    public String getLater_date_time() {
        return later_date_time;
    }

    public void setLater_date_time(String later_date_time) {
        this.later_date_time = later_date_time;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }
}
