package org.ptit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @JsonIgnoreProperties(value = { "customer" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "customer")
    private Cart cart;

    @JsonIgnoreProperties(value = { "customer", "products", "invoice", "paymentTransaction" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "customer")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "order", "customers" }, allowSetters = true)
    private PaymentTransaction paymentTransaction;

    @JsonIgnoreProperties(value = { "customer", "product" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "customer")
    private Review review;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Customer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Customer userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Cart getCart() {
        return this.cart;
    }

    public void setCart(Cart cart) {
        if (this.cart != null) {
            this.cart.setCustomer(null);
        }
        if (cart != null) {
            cart.setCustomer(this);
        }
        this.cart = cart;
    }

    public Customer cart(Cart cart) {
        this.setCart(cart);
        return this;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        if (this.order != null) {
            this.order.setCustomer(null);
        }
        if (order != null) {
            order.setCustomer(this);
        }
        this.order = order;
    }

    public Customer order(Order order) {
        this.setOrder(order);
        return this;
    }

    public PaymentTransaction getPaymentTransaction() {
        return this.paymentTransaction;
    }

    public void setPaymentTransaction(PaymentTransaction paymentTransaction) {
        this.paymentTransaction = paymentTransaction;
    }

    public Customer paymentTransaction(PaymentTransaction paymentTransaction) {
        this.setPaymentTransaction(paymentTransaction);
        return this;
    }

    public Review getReview() {
        return this.review;
    }

    public void setReview(Review review) {
        if (this.review != null) {
            this.review.setCustomer(null);
        }
        if (review != null) {
            review.setCustomer(this);
        }
        this.review = review;
    }

    public Customer review(Review review) {
        this.setReview(review);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return getId() != null && getId().equals(((Customer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            "}";
    }
}
