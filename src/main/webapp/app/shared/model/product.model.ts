import { IReview } from 'app/shared/model/review.model';
import { ICategory } from 'app/shared/model/category.model';
import { ICartItem } from 'app/shared/model/cart-item.model';
import { IOrder } from 'app/shared/model/order.model';

export interface IProduct {
  id?: number;
  name?: string | null;
  description?: string | null;
  price?: number | null;
  image?: string | null;
  inventory?: number | null;
  sold?: number | null;
  reviews?: IReview[] | null;
  category?: ICategory | null;
  cartItem?: ICartItem | null;
  order?: IOrder | null;
}

export const defaultValue: Readonly<IProduct> = {};
