import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { OrderListComponent } from './order-list/order-list.component';
import { CartComponent } from './cart/cart.component';
import { BrandProductsComponent } from './brand-products/brand-products.component';
import { CategoryProductsComponent } from './category-products/category-products.component';
import { PublicProductViewComponent } from './public-product-view/public-product-view.component';
import { HomeComponent } from './home/home.component';
import { AdminProductlistComponent } from '../admin/products/admin-productlist/admin-productlist.component';
import { PublicProductListComponent } from './public-product-list/public-product-list.component';
import { CategoryListComponent } from './category-list/category-list.component';

const routes: Routes = [

  {
    path: '',
    children: [

  { path: '', pathMatch: 'full', redirectTo: 'home' },
 
  { path: 'home', component: HomeComponent },

  { path: 'publicProductList', component: PublicProductListComponent },
  
  { path: 'publicProductView/:id', component: PublicProductViewComponent },
  
  { path: 'publicProductView/:id/:name', component: PublicProductViewComponent },

  { path: 'view/:id', component: PublicProductViewComponent },
 
  { path: 'categories', component: CategoryListComponent },

  { path: 'categoryProduct/:id/:name', component: CategoryProductsComponent },

  { path: 'brand/:id', component: BrandProductsComponent },

  { path: 'cart', component: CartComponent },

  { path: 'orderList', component: OrderListComponent },
 
  { path: 'orderView/:id', component: OrderDetailsComponent },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PublicRoutingModule { }
