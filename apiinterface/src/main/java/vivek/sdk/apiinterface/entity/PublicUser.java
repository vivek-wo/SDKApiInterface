package vivek.sdk.apiinterface.entity;

/**
 * Created by VIVEK-WO on 2018/3/1.
 */

public class PublicUser {
    private long id;//25632965
    private String name;//null

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PublicUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
