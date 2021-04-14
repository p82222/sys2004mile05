package store;

public abstract class ProductStockContainer {

    public abstract int getProductQuantity(Product p);

    public abstract void addProductQuantity(Product p, int quantity);

    public abstract boolean removeProductQuantity(Product p, int quantity);

    public abstract int getNumOfProducts();


}
