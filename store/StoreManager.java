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

    public int getProductStock(Product p) {
        return storeInventory.getProductQuantity(p);
    }

    // makes a new cart and returns the cartid
    public int assignNewCartID() {
        int id = shoppingCartCounter++;
        shoppingCarts.add(new ShoppingCart(id));
        return id;
    }

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

    public List<ProductStockPair> getAvailableProducts() {
        return storeInventory.getProducts();
    }

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

    // on quit
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

    public double getCartTotal(int cartID) {
        for (ShoppingCart cart : shoppingCarts) {
            if (cart.getCartID() == cartID) {
                return cart.getTotal();
            }
        }
        return -1.0;
    }

    public int getNumOfProducts() {
        return storeInventory.getNumOfProducts();
    }

    public boolean isInCart(int cartID, Product p) {
        for (ShoppingCart sC : shoppingCarts) {
            if (sC.getCartID() == cartID) {
                return sC.getProductQuantity(p) > 0;
            }
        }
        return false;
    }
}
