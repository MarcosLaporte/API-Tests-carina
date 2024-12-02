package web.automation.objects;

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
}
