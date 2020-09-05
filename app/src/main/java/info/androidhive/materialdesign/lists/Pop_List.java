package info.androidhive.materialdesign.lists;

/**
 * Created by hp on 2/16/2018.
 */

public class Pop_List {


    private String operating_day_per;
    private String duration_per;
    private String from_hour_per;


    public Pop_List
    (String operating_day_per, String duration_per, String from_hour_per
    ) {

        this.  operating_day_per =   operating_day_per;
        this.duration_per = duration_per;
        this.from_hour_per = from_hour_per;

    }


    public String getOperating_day_per() {
        return operating_day_per;
    }

    public void setOperating_day_per(String operating_day_per) {
        this.operating_day_per = operating_day_per;
    }

    public String getDuration_per() {
        return duration_per;
    }

    public void setDuration_per(String duration_per) {
        this.duration_per = duration_per;
    }

    public String getFrom_hour_per() {
        return from_hour_per;
    }

    public void setFrom_hour_per(String from_hour_per) {
        this.from_hour_per = from_hour_per;
    }
}
