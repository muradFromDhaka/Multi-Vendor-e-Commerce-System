export interface WishlistResponse {

  wishlistId: number;

  totalProducts: number;

  products: WishlistProduct[];

}


export interface WishlistProduct {

  productId: number;

  productName: string;

  imageUrl: string;

  price: number;

  discountPrice: number;

  averageRating: number;

  totalReviews: number;

}