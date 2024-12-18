public class Coffee {

    private String name;

    private int price;

    private String description;

    private int calories;


    public Coffee(String name, int price, String description, int calories) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.calories = calories;

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getCalories() {
        return calories;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }


    public void prepare() {
        System.out.println("Preparing " + getName());
    }

}