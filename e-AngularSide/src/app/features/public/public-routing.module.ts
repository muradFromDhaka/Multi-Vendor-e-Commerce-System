import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BrandProductsComponent } from './brands/brand-products/brand-products.component';
import { CategoryProductsComponent } from './category-products/category-products.component';
import { PublicProductViewComponent } from './public-product-view/public-product-view.component';
import { HomeComponent } from './home/home.component';
import { PublicProductListComponent } from './public-product-list/public-product-list.component';
import { CategoryListComponent } from './category-list/category-list.component';
import { BrandListComponent } from './brands/brand-list/brand-list.component';

const routes: Routes = [

  {
    path: '',
    children: [

  { path: '', pathMatch: 'full', redirectTo: 'home' },
 
  { path: 'home', component: HomeComponent },

  { path: 'publicProductList', component: PublicProductListComponent },
  
  { path: 'publicProductView/:id', component: PublicProductViewComponent },
   
  { path: 'categories', component: CategoryListComponent },

  { path: 'categoryProduct/:id/:name', component: CategoryProductsComponent },

  { path: 'brands', component: BrandListComponent },

  { path: 'brands/:id/:name', component: BrandProductsComponent },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PublicRoutingModule { }
