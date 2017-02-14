package personal.edu.phoneh.entity;


/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class Manage_Message {
    private int imageView1;
    private int imageView2;
    private String name;
    private String message;
    private String versions;

    public Manage_Message(int imageView1, int imageView2, String name, String message, String versions) {
        this.imageView1 = imageView1;
        this.imageView2 = imageView2;
        this.name = name;
        this.message = message;
        this.versions = versions;
    }

    public int getImageView1() {
        return imageView1;
    }

    public void setImageView1(int imageView1) {
        this.imageView1 = imageView1;
    }

    public int getImageView2() {
        return imageView2;
    }

    public void setImageView2(int imageView2) {
        this.imageView2 = imageView2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }
}
