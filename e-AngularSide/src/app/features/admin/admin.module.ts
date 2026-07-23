import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdminCategoryformComponent } from './categories/admin-categoryform/admin-categoryform.component';
import { AdminCategorylistComponent } from './categories/admin-categorylist/admin-categorylist.component';
import { BrandListComponent } from './brands/brand-list/brand-list.component';
import { AdminProductformComponent } from './products/admin-productform/admin-productform.component';
import { AdminProductDetailsComponent } from './products/admin-product-details/admin-product-details.component';
import { BrandFormComponent } from './brands/brand-form/brand-form.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { AdminProductlistComponent } from './products/admin-productlist/admin-productlist.component';
import { VendorDetailsComponent } from './vendor-management/vendor-details/vendor-details.component';
import { VendorProductsComponent } from './vendor-management/vendor-products/vendor-products.component';
import { VendorOrdersComponent } from './vendor-management/vendor-orders/vendor-orders.component';
import { VendorListComponent } from './vendor-management/vendor-list/vendor-list.component';
import { VendorEditComponent } from './vendor-management/vendor-edit/vendor-edit.component';
import { CustomerListComponent } from './customer-management/customer-list/customer-list.component';
import { CustomerDetailsComponent } from './customer-management/customer-details/customer-details.component';
import { RoleManagementComponent } from './role-management/role-management.component';
import { OrderListComponent } from './Order-management/order-list/order-list.component';
import { OrderDetailsComponent } from './Order-management/order-details/order-details.component';
import { AttributeComponent } from './variants-management/attribute/attribute.component';
import { AttributeValueComponent } from './variants-management/attribute-value/attribute-value.component';
import { ProductVariantListComponent } from './variants-management/product-variant/product-variant-list/product-variant-list.component';
import { ProductVariantDetailsComponent } from './variants-management/product-variant/product-variant-details/product-variant-details.component';
import { PaymentDetailsComponent } from './payment-management/payment-details/payment-details.component';
import { PaymentListComponent } from './payment-management/payment-list/payment-list.component';


@NgModule({
  declarations: [
  AdminComponent,
  AdminCategoryformComponent,
  AdminDashboardComponent,
  AdminProductDetailsComponent,
  AdminCategorylistComponent,
  AdminProductDetailsComponent,
  AdminProductformComponent,
  BrandFormComponent,
  BrandListComponent,
  AdminProductlistComponent,
    VendorListComponent,
  VendorDetailsComponent,
  VendorProductsComponent,
  VendorOrdersComponent,
  VendorEditComponent,
  CustomerListComponent,
  CustomerDetailsComponent,
  RoleManagementComponent,
  OrderListComponent,
  OrderDetailsComponent,
  AttributeComponent,
  AttributeValueComponent,
  ProductVariantListComponent,
  ProductVariantDetailsComponent,
  PaymentDetailsComponent,
  PaymentListComponent,
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class AdminModule { }
