import { ICustomer } from 'app/shared/model/customer.model';

export interface ICart {
  id?: number;
  customer?: ICustomer | null;
}

export const defaultValue: Readonly<ICart> = {};
