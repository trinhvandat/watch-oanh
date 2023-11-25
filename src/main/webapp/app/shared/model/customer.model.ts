import { ICart } from 'app/shared/model/cart.model';
import { IOrder } from 'app/shared/model/order.model';
import { IPaymentTransaction } from 'app/shared/model/payment-transaction.model';
import { IReview } from 'app/shared/model/review.model';

export interface ICustomer {
  id?: number;
  userId?: number | null;
  cart?: ICart | null;
  order?: IOrder | null;
  paymentTransaction?: IPaymentTransaction | null;
  review?: IReview | null;
}

export const defaultValue: Readonly<ICustomer> = {};
