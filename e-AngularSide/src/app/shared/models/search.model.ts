import { BrandResponse } from "src/app/models/brand.model";
import { CategoryResponse } from "src/app/models/category.model";
import { ProductListResponse } from "src/app/models/product.model";


export interface SearchResponse {

    products: ProductListResponse[];

    categories: CategoryResponse[];

    brands: BrandResponse[];

}