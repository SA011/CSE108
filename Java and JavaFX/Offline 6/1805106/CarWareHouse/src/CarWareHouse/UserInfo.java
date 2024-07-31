package CarWareHouse;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class UserInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 7684197124985644443L;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, type);
    }

    public String getType() {
        return type;
    }

    private String password;
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(userName, userInfo.userName) &&
                Objects.equals(password, userInfo.password) &&
                Objects.equals(type, userInfo.type);
    }


    public UserInfo(String name, String pass, String t){
        if(name == null){
            name = "";
        }
        if(pass == null){
            pass = "";
        }
        if(type == null){
            type = "Viewer";
        }
        userName = name;
        password = pass;
        type = t;
    }
}
