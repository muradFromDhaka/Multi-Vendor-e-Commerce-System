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
import { DashboardHeaderComponent } from './admin-dashboard/dashboard-header/dashboard-header.component';
import { DashboardOverviewComponent } from './admin-dashboard/dashboard-overview/dashboard-overview.component';
import { RevenueAnalyticsComponent } from './admin-dashboard/revenue-analytics/revenue-analytics.component';
import { FinancePaymentComponent } from './admin-dashboard/finance-payment/finance-payment.component';
import { OrdersAnalyticsComponent } from './admin-dashboard/orders-analytics/orders-analytics.component';
import { CustomerVendorComponent } from './admin-dashboard/customer-vendor/customer-vendor.component';
import { ProductInventoryComponent } from './admin-dashboard/product-inventory/product-inventory.component';
import { EngagementAnalyticsComponent } from './admin-dashboard/engagement-analytics/engagement-analytics.component';
import { DashboardChartsComponent } from './admin-dashboard/dashboard-charts/dashboard-charts.component';
import { RecentOrdersComponent } from './admin-dashboard/recent-orders/recent-orders.component';
import { RecentActivitiesComponent } from './admin-dashboard/recent-activities/recent-activities.component';
import { ProductAnalyticsComponent } from './admin-dashboard/product-analytics/product-analytics.component';
import { AdminSidebarComponent } from './admin-sidebar/admin-sidebar.component';


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
  DashboardHeaderComponent,
  DashboardOverviewComponent,
  RevenueAnalyticsComponent,
  FinancePaymentComponent,
  OrdersAnalyticsComponent,
  CustomerVendorComponent,
  ProductInventoryComponent,
  EngagementAnalyticsComponent,
  DashboardChartsComponent,
  RecentOrdersComponent,
  RecentActivitiesComponent,
  DashboardHeaderComponent,
  ProductAnalyticsComponent,
  AdminSidebarComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class AdminModule { }
