package Model;

public class Utilisateur {
    private String phone,name,password;

    public Utilisateur(){

    }

    public Utilisateur(String phone, String name, String password) {
        this.phone = phone;
        this.name = name;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public  String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
