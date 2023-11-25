import seller from 'app/entities/seller/seller.reducer';
import customer from 'app/entities/customer/customer.reducer';
import category from 'app/entities/category/category.reducer';
import product from 'app/entities/product/product.reducer';
import cart from 'app/entities/cart/cart.reducer';
import cartItem from 'app/entities/cart-item/cart-item.reducer';
import order from 'app/entities/order/order.reducer';
import invoice from 'app/entities/invoice/invoice.reducer';
import paymentTransaction from 'app/entities/payment-transaction/payment-transaction.reducer';
import review from 'app/entities/review/review.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  seller,
  customer,
  category,
  product,
  cart,
  cartItem,
  order,
  invoice,
  paymentTransaction,
  review,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
