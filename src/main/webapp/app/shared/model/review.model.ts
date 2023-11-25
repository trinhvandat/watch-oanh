import { ICustomer } from 'app/shared/model/customer.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IReview {
  id?: number;
  comment?: string | null;
  rate?: number | null;
  reviewDate?: string | null;
  customer?: ICustomer | null;
  product?: IProduct | null;
}

export const defaultValue: Readonly<IReview> = {};
