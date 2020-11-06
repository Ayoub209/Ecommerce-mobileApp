package Model;

public class Products {
    private String categorie,date,description,image,name,pid,price,time;

    public Products(){

    }

    public Products(String categorie, String date, String description, String image, String name, String pid, String price, String time) {
        this.categorie = categorie;
        this.date = date;
        this.description = description;
        this.image = image;
        this.name = name;
        this.pid = pid;
        this.price = price;
        this.time = time;
    }

    public String getCategorie() {
        return categorie;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPid() {
        return pid;
    }

    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
