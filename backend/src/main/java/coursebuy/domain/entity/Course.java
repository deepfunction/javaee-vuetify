package coursebuy.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "courses")
public class Course extends Union implements Serializable {

    @Column(name = "name", length = 30, nullable = false, unique = true)
    private String name;

    @Column(name = "price", length = 30, nullable = false)
    private int price;

    @Column(name = "discount", length = 30, nullable = false)
    private int discount;

    @Column(name = "subtotal", length = 30, nullable = false)
    private int subtotal;

    @Column(name = "\"isBusy\"", length = 5, nullable = false)
    private boolean isBusy;

    @Column(name = "\"userEmail\"", nullable = false)
    private String userEmail;

    @Column(name = "\"isAddedToCart\"", length = 5, nullable = false)
    private boolean isAddedToCart;

    @Column(name = "\"isAvailable\"", length = 5, nullable = false)
    private boolean isAvailable;

    @Column(name = "\"isBought\"", length = 5, nullable = false)
    private boolean isBought;

    public Course() {
    }

    public Course(String name, int price, int discount, int subtotal, boolean isBusy, String userEmail, boolean isAddedToCart, boolean isAvailable, boolean isBought) {
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.subtotal = subtotal;
        this.isBusy = isBusy;
        this.userEmail = userEmail;
        this.isAddedToCart = isAddedToCart;
        this.isAvailable = isAvailable;
        this.isBought = isBought;
    }

    public Course(long id, String name, int price, int discount, int subtotal, boolean isBusy, String userEmail, boolean isAddedToCart, boolean isAvailable, boolean isBought) {
        super(id);
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.subtotal = subtotal;
        this.isBusy = isBusy;
        this.userEmail = userEmail;
        this.isAddedToCart = isAddedToCart;
        this.isAvailable = isAvailable;
        this.isBought = isBought;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public boolean getIsBusy() {
        return isBusy;
    }

    public void setIsBusy(boolean busy) {
        isBusy = busy;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean getIsAddedToCart() {
        return isAddedToCart;
    }

    public void setIsAddedToCart(boolean addedToCart) {
        isAddedToCart = addedToCart;
    }

    public boolean getIsBought() {
        return isBought;
    }

    public void setIsBought(boolean bought) {
        isBought = bought;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return name.equals(course.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", subtotal=" + subtotal +
                ", isBusy=" + isBusy +
                ", userEmail='" + userEmail + '\'' +
                ", isAddedToCart=" + isAddedToCart +
                ", isAvailable=" + isAvailable +
                ", isBought=" + isBought +
                ", id=" + id +
                '}';
    }
}
