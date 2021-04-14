//STUDENT NAME: Chia-Yu Liu 
//STUDENT ID: 100698737     
//STUDENT NAME: Keefer Belanger
//STUDENT ID: St# 101152085

package store;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends ProductStockContainer {
    private final List<ProductStockPair> products = new ArrayList<>();
    private int numOfProducts = 0;
    private final int cartID;
    private double total = 0.0;
    
    /**
     * Constructor for ShoppingCart
     * @param cartID
     */
    public ShoppingCart(int cartID) {
        this.cartID = cartID;
    }
    
    /**
     * Returns an int value of the stock of a product
     * @param p
     */
    @Override
    public int getProductQuantity(Product p) {
        for (ProductStockPair pair : products) {
            if (pair.product.id == p.id) {
                return pair.stock;
            }
        }
        return -1;
    }
    
    /**
     * Adds a product and its quantity
     * @param p 
     * @param q 
     */
    @Override
    public void addProductQuantity(Product p, int q) {
        // no negative ids please
        if (p.id < 0) return;

        total += p.price * q;

        for (int i = 0; i < products.size(); i++) {
            ProductStockPair pair = products.get(i);
            if (pair.product.id == p.id) {
                products.set(i, new ProductStockPair(p, pair.stock + q));
                return;
            }
        }
        products.add(new ProductStockPair(p, q));
        numOfProducts++;
    }
    
    /**
     * Removes a product and its quantity
     * @param p
     * @param q 
     */
    @Override
    public boolean removeProductQuantity(Product p, int q) {
        for (int i = 0; i < products.size(); i++) {
            ProductStockPair pair = products.get(i);
            if (pair.product.id == p.id) {
                if (pair.stock >= q) {
                    products.set(i, new ProductStockPair(pair.product, pair.stock - q));
                    total -= p.price * q;
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    
    /**
     * Gets the number of products 
     */
    @Override
    public int getNumOfProducts() {
        return numOfProducts;
    }
    
    /**
     * Gets the list of products 
     */
    public List<ProductStockPair> getProducts() {
        return new ArrayList<>(products);
    }
    
    /**
     * Gets the total  
     */
    public double getTotal() {
        return total;
    }
    
    /**
     * Gets the cart id
     */
    public int getCartID() { return cartID; }
}
