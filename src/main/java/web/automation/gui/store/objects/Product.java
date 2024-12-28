package web.automation.gui.store.objects;

public class Product {
    private final String name;
    private final String priceTag;

    public Product(String name, String priceTag) {
        this.name = name;
        this.priceTag = priceTag;
    }

    public String getName() {
        return this.name;
    }

    public String getPriceTag() {
        return this.priceTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return this.name.equals(product.name) && this.priceTag.equals(product.priceTag);
    }
}
