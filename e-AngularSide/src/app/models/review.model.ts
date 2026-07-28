export interface ReviewRequest{

  productId:number;

  rating:number;

  comment:string;

}


export interface ReviewResponse {

  id:number;

  userName:string;

  productId:number;

  productName:string;

  rating:number;

  comment:string;

  createdAt: string;

}


export interface ProductReviewSummary{

  averageRating:number;

  totalReviews:number;

  fiveStar:number;

  fourStar:number;

  threeStar:number;

  twoStar:number;

  oneStar:number;

}