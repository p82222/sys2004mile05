//STUDENT NAME: Chia-Yu Liu 
//STUDENT ID: 100698737     
//STUDENT NAME: Keefer Belanger
//STUDENT ID: St# 101152085

package store;

public abstract class ProductStockContainer {
    
    /**
     * Abstract method for use in another class
     * @param p 
     */
    public abstract int getProductQuantity(Product p);
    
    /**
     * Abstract method for use in another class
     * @param p 
     * @param quantity 
     */
    public abstract void addProductQuantity(Product p, int quantity);
    
    /**
     * Abstract method for use in another class
     * @param p 
     * @param quantity
     */
    public abstract boolean removeProductQuantity(Product p, int quantity);
    
    /**
     * Abstract method for use in another class
     */
    public abstract int getNumOfProducts();
}
