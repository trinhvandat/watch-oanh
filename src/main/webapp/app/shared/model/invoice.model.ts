import { IOrder } from 'app/shared/model/order.model';

export interface IInvoice {
  id?: number;
  invoiceDate?: string | null;
  total?: number | null;
  order?: IOrder | null;
}

export const defaultValue: Readonly<IInvoice> = {};
