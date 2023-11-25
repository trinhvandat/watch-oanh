import { ICustomer } from 'app/shared/model/customer.model';
import { IProduct } from 'app/shared/model/product.model';
import { IInvoice } from 'app/shared/model/invoice.model';
import { IPaymentTransaction } from 'app/shared/model/payment-transaction.model';

export interface IOrder {
  id?: number;
  orderDate?: string | null;
  receiverAddress?: string | null;
  customer?: ICustomer | null;
  products?: IProduct[] | null;
  invoice?: IInvoice | null;
  paymentTransaction?: IPaymentTransaction | null;
}

export const defaultValue: Readonly<IOrder> = {};
