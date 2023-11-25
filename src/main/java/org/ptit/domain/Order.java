package org.ptit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "receiver_address")
    private String receiverAddress;

    @JsonIgnoreProperties(value = { "cart", "order", "paymentTransaction", "review" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    @JsonIgnoreProperties(value = { "reviews", "category", "cartItem", "order" }, allowSetters = true)
    private Set<Product> products = new HashSet<>();

    @JsonIgnoreProperties(value = { "order" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    private Invoice invoice;

    @JsonIgnoreProperties(value = { "order", "customers" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    private PaymentTransaction paymentTransaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public Order orderDate(LocalDate orderDate) {
        this.setOrderDate(orderDate);
        return this;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getReceiverAddress() {
        return this.receiverAddress;
    }

    public Order receiverAddress(String receiverAddress) {
        this.setReceiverAddress(receiverAddress);
        return this;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order customer(Customer customer) {
        this.setCustomer(customer);
        return this;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setOrder(null));
        }
        if (products != null) {
            products.forEach(i -> i.setOrder(this));
        }
        this.products = products;
    }

    public Order products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public Order addProduct(Product product) {
        this.products.add(product);
        product.setOrder(this);
        return this;
    }

    public Order removeProduct(Product product) {
        this.products.remove(product);
        product.setOrder(null);
        return this;
    }

    public Invoice getInvoice() {
        return this.invoice;
    }

    public void setInvoice(Invoice invoice) {
        if (this.invoice != null) {
            this.invoice.setOrder(null);
        }
        if (invoice != null) {
            invoice.setOrder(this);
        }
        this.invoice = invoice;
    }

    public Order invoice(Invoice invoice) {
        this.setInvoice(invoice);
        return this;
    }

    public PaymentTransaction getPaymentTransaction() {
        return this.paymentTransaction;
    }

    public void setPaymentTransaction(PaymentTransaction paymentTransaction) {
        if (this.paymentTransaction != null) {
            this.paymentTransaction.setOrder(null);
        }
        if (paymentTransaction != null) {
            paymentTransaction.setOrder(this);
        }
        this.paymentTransaction = paymentTransaction;
    }

    public Order paymentTransaction(PaymentTransaction paymentTransaction) {
        this.setPaymentTransaction(paymentTransaction);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return getId() != null && getId().equals(((Order) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", orderDate='" + getOrderDate() + "'" +
            ", receiverAddress='" + getReceiverAddress() + "'" +
            "}";
    }
}
