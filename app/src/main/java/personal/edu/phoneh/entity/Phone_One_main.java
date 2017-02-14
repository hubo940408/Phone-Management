package personal.edu.phoneh.entity;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
public class Phone_One_main {
    private String name;
    private String number;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Phone_One_main{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Phone_One_main(String name, String number) {
        this.name = name;
        this.number = number;
    }
}
