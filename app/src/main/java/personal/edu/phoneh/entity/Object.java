package personal.edu.phoneh.entity;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class Object {
    private String name;
    private int imageView;

    public Object(String name, int imageView) {
        this.name = name;
        this.imageView = imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }
}
