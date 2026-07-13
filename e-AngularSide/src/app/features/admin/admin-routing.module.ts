import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BrandListComponent } from './brands/brand-list/brand-list.component';
import { AdminCategorylistComponent } from './categories/admin-categorylist/admin-categorylist.component';
import { AdminProductformComponent } from './products/admin-productform/admin-productform.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { BrandFormComponent } from './brands/brand-form/brand-form.component';
import { AdminCategoryformComponent } from './categories/admin-categoryform/admin-categoryform.component';
import { AdminProductDetailsComponent } from './products/admin-product-details/admin-product-details.component';
import { AdminProductlistComponent } from './products/admin-productlist/admin-productlist.component';
import { VendorListComponent } from './vendor-management/vendor-list/vendor-list.component';
import { VendorProductsComponent } from './vendor-management/vendor-products/vendor-products.component';
import { VendorDetailsComponent } from './vendor-management/vendor-details/vendor-details.component';
import { VendorOrdersComponent } from './vendor-management/vendor-orders/vendor-orders.component';
import { VendorEditComponent } from './vendor-management/vendor-edit/vendor-edit.component';
import { CustomerListComponent } from './customer-management/customer-list/customer-list.component';
import { CustomerDetailsComponent } from './customer-management/customer-details/customer-details.component';
import { RoleManagementComponent } from './role-management/role-management.component';
import { OrderListComponent } from './Order-management/order-list/order-list.component';
import { OrderDetailsComponent } from './Order-management/order-details/order-details.component';
import { AdminComponent } from './admin.component';
import { AttributeComponent } from './variants-management/attribute/attribute.component';
import { AttributeValueComponent } from './variants-management/attribute-value/attribute-value.component';
import { ProductVariantFormComponent } from './variants-management/product-variant/product-variant-form/product-variant-form.component';
import { ProductVariantDetailsComponent } from './variants-management/product-variant/product-variant-details/product-variant-details.component';
import { ProductVariantListComponent } from './variants-management/product-variant/product-variant-list/product-variant-list.component';

const routes: Routes = [
{
    path:'',
    component: AdminComponent,
      children: [
      { path: '', pathMatch: 'full', redirectTo: 'adminDashboard' },
      { path: 'adminDashboard', component: AdminDashboardComponent },
      { path: 'adminProductList', component: AdminProductlistComponent },
      { path: 'adminProductForm', component: AdminProductformComponent },
      { path: 'adminEditProductForm/:id', component: AdminProductformComponent },
      { path: 'adminProductView/:id', component: AdminProductDetailsComponent },
      { path: 'adminCategoryList', component: AdminCategorylistComponent },
      { path: 'adminCategoryForm', component: AdminCategoryformComponent },
      { path: 'adminEditCategoryForm/:id', component: AdminCategoryformComponent },
      { path: 'brand', component: BrandListComponent },
      { path: 'brandForm', component: BrandFormComponent },
      { path: 'editBrand/:id', component: BrandFormComponent },

      {path: 'vendors',component: VendorListComponent},
      { path: 'vendors/:id',component: VendorDetailsComponent},
      {path: 'vendors/:id/products',component: VendorProductsComponent},
      {path: 'vendors/:id/orders',component: VendorOrdersComponent},
      {path: 'vendors/edit/:id',component: VendorEditComponent},

      {path: 'customers',component: CustomerListComponent},
      {path: 'customer/:username',component: CustomerDetailsComponent},
      
      {path: 'roles',component: RoleManagementComponent},

      {path: 'orders',component: OrderListComponent},
      {path: 'orders/:id',component: OrderDetailsComponent},

      {path: 'attribute',component: AttributeComponent},
      {path: 'attributeValue',component: AttributeValueComponent},
      {path: 'productVariantForm',component: ProductVariantFormComponent},
      {path: 'productVariantForm/:id',component: ProductVariantFormComponent},
      {path: 'productVariantList',component: ProductVariantListComponent},
      {path: 'productVariantDetails/:id',component: ProductVariantDetailsComponent},
      
    ],
}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
