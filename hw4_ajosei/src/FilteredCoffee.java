public class FilteredCoffee extends Coffee{

    private String brew_type;

    public FilteredCoffee(String name, int price, String description, int calories, String brew_type){
        super(name, price, description, calories);
        this.brew_type = brew_type;
    }

    public void setBrew_type (String brew_type){
        this.brew_type = brew_type;
    }

    public String getBrew_type(){
        return brew_type;
    }

    @Override
    public void prepare(){
        super.prepare();
        System.out.println("Pour hot water through the filter with coffee.");
    }
}
