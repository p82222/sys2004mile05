//STUDENT NAME: Chia-Yu Liu 
//STUDENT ID: 100698737     
//STUDENT NAME: Keefer Belanger
//STUDENT ID: St# 101152085

package store;

import java.util.ArrayList;
import java.util.List;

public class StoreManager {
    private final Inventory storeInventory = new Inventory();
    private final List<ShoppingCart> shoppingCarts = new ArrayList<>();
    private int shoppingCartCounter = 0;
    
    /**
     * Gets the product stock
     * @param p
     */
    public int getProductStock(Product p) {
        return storeInventory.getProductQuantity(p);
    }
    
    /**
     * Makes a new cart and returns the cartid 
     */
    public int assignNewCartID() {
        int id = shoppingCartCounter++;
        shoppingCarts.add(new ShoppingCart(id));
        return id;
    }
    
    /**
     * Adds a product and amount of that product to the cart
     * @param cartID
     * @param p
     * @param amount
     */
    public boolean addToCart(int cartID, Product p, int amount) {
        if (storeInventory.removeProductQuantity(p, amount)) {
            for (ShoppingCart cart : shoppingCarts) {
                if (cart.getCartID() == cartID) {
                    cart.addProductQuantity(p, amount);
                    return true;
                }
            }
            // put items back
            storeInventory.addProductQuantity(p, amount);
        }
        return false;
    }
    
    /**
     * Removes a product and amount of that product to the cart
     * @param cartID
     * @param p
     * @param amount
     */
    public boolean removeFromCart(int cartID, Product p, int amount) {
        for (ShoppingCart cart : shoppingCarts) {
            if (cart.getCartID() == cartID) {
                if (cart.removeProductQuantity(p, amount)) {
                    storeInventory.addProductQuantity(p, amount);
                    return true;
                }
                return false;
            }
        }
        return false;
    }
    
    /**
     * Gets the available products from the Inventory
     */
    public List<ProductStockPair> getAvailableProducts() {
        return storeInventory.getProducts();
    }
    
    /**
     * Gets the contents that were added to the cart
     * @param cartID
     */
    public List<ProductStockPair> getCartContents(int cartID) {
        for (ShoppingCart cart : shoppingCarts) {
            if (cart.getCartID() == cartID) {
                List<ProductStockPair> contents = new ArrayList<>(cart.getProducts());
                // filtering out 0 stocks; unique to viewing cart contents
                contents.removeIf(productStockPair -> productStockPair.stock < 1);
                return contents;
            }
        }
        return null; // just give an empty ArrayList if not found
    }

     /**
     * Clears the cart on exit
     * @param cartID
     */
    public void clearCartContents(int cartID) {
        for (ShoppingCart cart : shoppingCarts) {
            if (cart.getCartID() == cartID) {
                List<ProductStockPair> stuffToPutBack = cart.getProducts();
                for (ProductStockPair pair : stuffToPutBack) {
                    storeInventory.addProductQuantity(pair.product, pair.stock);
                }
                // delete cart from shoppingCarts
                shoppingCarts.remove(cart);
                return;
            }
        }
    }
    
     /**
     * Gets the total for the items in the cart
     * @param cartID
     */
    public double getCartTotal(int cartID) {
        for (ShoppingCart cart : shoppingCarts) {
            if (cart.getCartID() == cartID) {
                return cart.getTotal();
            }
        }
        return -1.0;
    }
    
     /**
     * Gets the number of products
     */
    public int getNumOfProducts() {
        return storeInventory.getNumOfProducts();
    }
    
     /**
     * Checks if product is in the cart
     * @param cartID
     * @param p
     */
    public boolean isInCart(int cartID, Product p) {
        for (ShoppingCart sC : shoppingCarts) {
            if (sC.getCartID() == cartID) {
                return sC.getProductQuantity(p) > 0;
            }
        }
        return false;
    }
}
