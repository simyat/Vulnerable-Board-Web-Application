package model;

public class UserDTO {
    private String user_id;
    private String name;
    private String password;
    private String address;
    private String regidate;

    public UserDTO() {
    }

//    public UserVO(String user_id, String name, String password, String address) {
//        this.user_id = user_id;
//        this.name = name;
//        this.password = password;
//        this.address = address;
//    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegidate() {
        return regidate;
    }

    public void setRegidate(String regidate) {
        this.regidate = regidate;
    }
}
