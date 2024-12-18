import javax.swing.*;


abstract class Customer {


    private String name;


    private String email;


    private int phoneNum;




    public Customer(String name, String email, int phoneNum) {
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
    }


    public abstract void payCoffee();


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }
}


