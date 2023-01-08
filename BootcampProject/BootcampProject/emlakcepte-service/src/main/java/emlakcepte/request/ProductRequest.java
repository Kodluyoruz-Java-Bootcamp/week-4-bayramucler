package emlakcepte.request;

import emlakcepte.model.User;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ProductRequest {
    private int userId;
    private String name;
    private int realtyLimit;
    private int validityPeriod;
    private double price;

    public ProductRequest() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRealtyLimit() {
        return realtyLimit;
    }

    public void setRealtyLimit(int realtyLimit) {
        this.realtyLimit = realtyLimit;
    }

    public int getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(int validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductRequest{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", realtyLimit=" + realtyLimit +
                ", validityPeriod=" + validityPeriod +
                ", price=" + price +
                '}';
    }
}
