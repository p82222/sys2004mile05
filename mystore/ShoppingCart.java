package mystore;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<ProductStockPair> products = new ArrayList<>();
    private int numOfProducts = 0;
    private final int cartID;
    private double total = 0.0;

    public ShoppingCart(int cartID) {
        this.cartID = cartID;
    }

    public int getProductQuantity(Product p) {
        for (ProductStockPair pair : products) {
            if (pair.product.id == p.id) {
                return pair.stock;
            }
        }
        return -1;
    }

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

    public int getNumOfProducts() {
        return numOfProducts;
    }

    public List<ProductStockPair> getProducts() {
        return new ArrayList<>(products);
    }

    public double getTotal() {
        return total;
    }

    public int getCartID() { return cartID; }
}
