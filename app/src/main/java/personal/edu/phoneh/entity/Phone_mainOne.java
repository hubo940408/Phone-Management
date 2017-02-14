package personal.edu.phoneh.entity;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
public class Phone_mainOne {
    private String name;
    private int idx;

    public Phone_mainOne(String name, int idx) {
        this.name = name;
        this.idx = idx;
    }

    @Override
    public String toString() {
        return "Phone_mainOne{" +
                "name='" + name + '\'' +
                ", idx=" + idx +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }



}
