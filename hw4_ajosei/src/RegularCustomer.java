public class RegularCustomer extends Customer{


    public RegularCustomer(String name, String address, int phoneNum) {
        super(name, address, phoneNum);
    }


    @Override
    public void payCoffee() {
        System.out.println("Regular customer can pay for the coffee using credit card or cash.");
    }


}
