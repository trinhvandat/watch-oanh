import { IProduct } from 'app/shared/model/product.model';
import { ICart } from 'app/shared/model/cart.model';

export interface ICartItem {
  id?: number;
  product?: IProduct | null;
  cart?: ICart | null;
}

export const defaultValue: Readonly<ICartItem> = {};
