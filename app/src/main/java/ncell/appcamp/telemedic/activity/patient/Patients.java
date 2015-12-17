package ncell.appcamp.telemedic.activity.patient;

import org.json.JSONObject;

import java.io.Serializable;

public class Patients implements Serializable {
    String id, name, username, cell_no, home_no, email, address, sex, status;

    public Patients(String id, String name, String username, String cell_no,
                    String home_no, String email, String address, String sex, String status) {
        super();
        this.id = id;
        this.name = name;
        this.username = username;
        this.cell_no = cell_no;
        this.home_no = home_no;
        this.email = email;
        this.address = address;
        this.sex = sex;
        this.status = status;
    }

    public Patients(String id, String name, String cell_no) {
        super();
        this.name = name;
        this.cell_no = cell_no;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCell_no() {
        return cell_no;
    }

    public void setCell_no(String cell_no) {
        this.cell_no = cell_no;
    }

    public String getHome_no() {
        return home_no;
    }

    public void setHome_no(String home_no) {
        this.home_no = home_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Patients(JSONObject jObj) {
        this.id = jObj.optString("id");
        this.name = jObj.optString("name");
        this.username = jObj.optString("username");
        this.address = jObj.optString("address");
        this.email = jObj.optString("email");
        this.cell_no = jObj.optString("cell_no");
        this.home_no = jObj.optString("home_no");
        this.status = jObj.optString("status");
    }


}
