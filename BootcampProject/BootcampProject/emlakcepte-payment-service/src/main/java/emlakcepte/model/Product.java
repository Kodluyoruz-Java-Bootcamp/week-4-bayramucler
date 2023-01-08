package emlakcepte.model;

public class Product {

    private int id;
    private User user;
    private String name;
    private int realtyLimit;
    private int validityPeriod;
    private double price;
    private PaymentStatusType paymentStatusType;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public PaymentStatusType getPaymentStatusType() {
        return paymentStatusType;
    }

    public void setPaymentStatusType(PaymentStatusType paymentStatusType) {
        this.paymentStatusType = paymentStatusType;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", realtyLimit=" + realtyLimit +
                ", validityPeriod=" + validityPeriod +
                ", price=" + price +
                ", paymentStatusType=" + paymentStatusType +
                '}';
    }
}
