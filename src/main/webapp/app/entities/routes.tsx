import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Seller from './seller';
import Customer from './customer';
import Category from './category';
import Product from './product';
import Cart from './cart';
import CartItem from './cart-item';
import Order from './order';
import Invoice from './invoice';
import PaymentTransaction from './payment-transaction';
import Review from './review';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="seller/*" element={<Seller />} />
        <Route path="customer/*" element={<Customer />} />
        <Route path="category/*" element={<Category />} />
        <Route path="product/*" element={<Product />} />
        <Route path="cart/*" element={<Cart />} />
        <Route path="cart-item/*" element={<CartItem />} />
        <Route path="order/*" element={<Order />} />
        <Route path="invoice/*" element={<Invoice />} />
        <Route path="payment-transaction/*" element={<PaymentTransaction />} />
        <Route path="review/*" element={<Review />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
