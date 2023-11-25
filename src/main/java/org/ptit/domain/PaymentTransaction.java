package org.ptit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A PaymentTransaction.
 */
@Entity
@Table(name = "payment_transaction")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bank_id")
    private String bankId;

    @Column(name = "amount")
    private Double amount;

    @JsonIgnoreProperties(value = { "customer", "products", "invoice", "paymentTransaction" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Order order;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentTransaction")
    @JsonIgnoreProperties(value = { "cart", "order", "paymentTransaction", "review" }, allowSetters = true)
    private Set<Customer> customers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentTransaction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankId() {
        return this.bankId;
    }

    public PaymentTransaction bankId(String bankId) {
        this.setBankId(bankId);
        return this;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public Double getAmount() {
        return this.amount;
    }

    public PaymentTransaction amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PaymentTransaction order(Order order) {
        this.setOrder(order);
        return this;
    }

    public Set<Customer> getCustomers() {
        return this.customers;
    }

    public void setCustomers(Set<Customer> customers) {
        if (this.customers != null) {
            this.customers.forEach(i -> i.setPaymentTransaction(null));
        }
        if (customers != null) {
            customers.forEach(i -> i.setPaymentTransaction(this));
        }
        this.customers = customers;
    }

    public PaymentTransaction customers(Set<Customer> customers) {
        this.setCustomers(customers);
        return this;
    }

    public PaymentTransaction addCustomer(Customer customer) {
        this.customers.add(customer);
        customer.setPaymentTransaction(this);
        return this;
    }

    public PaymentTransaction removeCustomer(Customer customer) {
        this.customers.remove(customer);
        customer.setPaymentTransaction(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentTransaction)) {
            return false;
        }
        return getId() != null && getId().equals(((PaymentTransaction) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentTransaction{" +
            "id=" + getId() +
            ", bankId='" + getBankId() + "'" +
            ", amount=" + getAmount() +
            "}";
    }
}
