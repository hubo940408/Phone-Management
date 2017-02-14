package personal.edu.phoneh.entity;


/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class Detection_User {
    private String topname;
    private String butname;
    private int photo;

    public String getTopname() {
        return topname;
    }

    public void setTopname(String topname) {
        this.topname = topname;
    }

    public String getButname() {
        return butname;
    }

    public void setButname(String butname) {
        this.butname = butname;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public Detection_User(String topname, String butname, int photo) {
        this.topname = topname;
        this.butname = butname;
        this.photo = photo;
    }
}
