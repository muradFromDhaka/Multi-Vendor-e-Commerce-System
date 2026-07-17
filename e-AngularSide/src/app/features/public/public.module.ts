import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PublicRoutingModule } from './public-routing.module';
import { PublicComponent } from './public.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrandProductsComponent } from './brands/brand-products/brand-products.component';
import { CategoryProductsComponent } from './category-products/category-products.component';
import { HomeComponent } from './home/home.component';
import { PublicProductViewComponent } from './public-product-view/public-product-view.component';
import { PublicProductListComponent } from './public-product-list/public-product-list.component';
import { CategoryListComponent } from './category-list/category-list.component';
import { BrandListComponent } from './brands/brand-list/brand-list.component';


@NgModule({
  declarations: [
    PublicComponent,
    BrandProductsComponent,
    CategoryProductsComponent,
    HomeComponent,
    PublicProductViewComponent,
    PublicProductListComponent,
    CategoryListComponent,
    BrandListComponent,
  
  ],
  imports: [
    CommonModule,
    PublicRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class PublicModule { }
