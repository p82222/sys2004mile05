//STUDENT NAME: Chia-Yu Liu 
//STUDENT ID: 100698737     
//STUDENT NAME: Keefer Belanger
//STUDENT ID: St# 101152085

package store;
import java.util.ArrayList;
import java.util.List;

public class Inventory extends ProductStockContainer{

    // they could've used something else here
    // a single ArrayList with Product-stock Pairs for example
    // could've used a HashMap or other
    private final List<ProductStockPair> products = new ArrayList<>();
    private int numOfProducts = 0;

    public Inventory() {
        initialize();
    }

    @Override
    public int getProductQuantity(Product p) {
        for (ProductStockPair pair : products) {
            if (pair.product.id == p.id) {
                return pair.stock;
            }
        }
        return -1;
    }

    @Override
    public void addProductQuantity(Product p, int q) {
        // no negative ids please
        if (p.id < 0) return;

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

    @Override
    public boolean removeProductQuantity(Product p, int q) {
        for (int i = 0; i < products.size(); i++) {
            ProductStockPair pair = products.get(i);
            if (pair.product.id == p.id) {
                if (pair.stock >= q) {
                    products.set(i, new ProductStockPair(pair.product, pair.stock - q));
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public int getNumOfProducts() {
        return numOfProducts;
    }

    public List<ProductStockPair> getProducts() {
        return new ArrayList<>(products);
    }

    private void initialize() {
        // can add some default inventory stock here
        this.addProductQuantity(new Product(100.0, "Apples", 0), 76);
        this.addProductQuantity(new Product(55.0, "Bananas", 1), 6);
        this.addProductQuantity(new Product(45.0, "Pineapples", 2), 32);
        this.addProductQuantity(new Product(45.0, "Papayas", 3), 32);
//        this.addProductQuantity(new Product(35.0, "MUSI1001", 3), 3);
//        this.addProductQuantity(new Product(0.01, "CRCJ1000", 4), 12);
//        this.addProductQuantity(new Product(25.0, "ELEC4705", 5), 132);
//        this.addProductQuantity(new Product(145.0, "SYSC4907", 6), 322);
//        this.addProductQuantity(new Product(1.0, "SYSC1005", 7), 7);
//        this.addProductQuantity(new Product(15.0, "ECOR1010", 8), 10);
//        this.addProductQuantity(new Product(99.0, "SYSC4805", 9), 332);
//        this.addProductQuantity(new Product(32.0, "STAT3501", 10), 4);
//        this.addProductQuantity(new Product(99.99, "MATH3501", 11), 1);
//        this.addProductQuantity(new Product(19.99, "ELEC3500", 12), 13);
//        this.addProductQuantity(new Product(145.99, "ELEC2501", 13), 22);
//        this.addProductQuantity(new Product(0.29, "SYSC3601", 14), 42);

        numOfProducts = 15;
    }


}
