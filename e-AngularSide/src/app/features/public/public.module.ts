import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PublicRoutingModule } from './public-routing.module';
import { PublicComponent } from './public.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrandProductsComponent } from './brand-products/brand-products.component';
import { CartComponent } from './cart/cart.component';
import { CategoryProductsComponent } from './category-products/category-products.component';
import { HomeComponent } from './home/home.component';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { OrderListComponent } from './order-list/order-list.component';
import { PublicProductViewComponent } from './public-product-view/public-product-view.component';
import { PublicProductListComponent } from './public-product-list/public-product-list.component';
import { CategoryListComponent } from './category-list/category-list.component';


@NgModule({
  declarations: [
    PublicComponent,
    BrandProductsComponent,
    CartComponent,
    CategoryProductsComponent,
    HomeComponent,
    OrderDetailsComponent,
    OrderListComponent,
    PublicProductViewComponent,
    PublicProductListComponent,
    CategoryListComponent
  ],
  imports: [
    CommonModule,
    PublicRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class PublicModule { }
