package web.automation.gui.store.objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Cart {
    public final Map<Product, Integer> products;

    public Cart(Map<Product, Integer> products) {
        this.products = products;
    }

    public Cart() {
        this(new HashMap<>());
    }

    /**
     * Adds product and quantity entry to the map.
     * If the map already contained a mapping for the product (key), the new and old quantities (value) are added together.
     */
    public void addProduct(Product product, int quantity) {
        int existingQty = this.products.getOrDefault(product, 0);
        this.products.put(product, existingQty + quantity);
    }

    public Optional<Product> getProductByName(String prodName) {
        return this.products.keySet()
                .stream()
                .filter(p -> p.getName().equalsIgnoreCase(prodName))
                .findFirst();
    }

    public String getFormattedTotalAmount() {
        double totalValue = this.products.entrySet()
                .stream()
                .mapToDouble(entry -> {
                    double price = Double.parseDouble(
                            entry.getKey().getPriceTag().substring(1));
                    return entry.getValue() * price;
                })
                .sum();

        return String.format("$%.2f", totalValue);
    }
}
