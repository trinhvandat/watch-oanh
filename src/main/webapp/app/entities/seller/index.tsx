import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Seller from './seller';
import SellerDetail from './seller-detail';
import SellerUpdate from './seller-update';
import SellerDeleteDialog from './seller-delete-dialog';

const SellerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Seller />} />
    <Route path="new" element={<SellerUpdate />} />
    <Route path=":id">
      <Route index element={<SellerDetail />} />
      <Route path="edit" element={<SellerUpdate />} />
      <Route path="delete" element={<SellerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SellerRoutes;
