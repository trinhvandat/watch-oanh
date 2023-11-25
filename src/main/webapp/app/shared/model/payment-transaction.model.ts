import { IOrder } from 'app/shared/model/order.model';
import { ICustomer } from 'app/shared/model/customer.model';

export interface IPaymentTransaction {
  id?: number;
  bankId?: string | null;
  amount?: number | null;
  order?: IOrder | null;
  customers?: ICustomer[] | null;
}

export const defaultValue: Readonly<IPaymentTransaction> = {};
