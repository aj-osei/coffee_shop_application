public class Espresso extends Coffee{
    private int numShots;
    private String milkType;
    private String addIngredients;

    public Espresso(String name, int price, String description, int calories, int numShots, String milkType, String addIngredients) {
        super(name, price, description, calories);
        this.numShots = numShots;
        this.milkType = milkType;
        this.addIngredients = addIngredients;
    }

    public int getNumShots() {
        return numShots;
    }
    public void setNumShots(int numShots) {
        this.numShots = numShots;
    }
    public String getMilkType() {
        return milkType;
    }
    public void setMilkType(String milkType) {
        this.milkType = milkType;
    }
    public String getAddIngredients() {
        return addIngredients;
    }
    public void setAddIngredients(String addIngredients) {
        this.addIngredients = addIngredients;
    }

    @Override
    public void prepare(){
        super.prepare();
        System.out.println("Force hot water through finely-ground coffee.");
    }
}

