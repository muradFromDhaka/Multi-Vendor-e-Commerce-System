import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VendorRoutingModule } from './vendor-routing.module';
import { VendorComponent } from './vendor.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { VendorDashboardComponent } from './vendor-dashboard/vendor-dashboard.component';
import { VendorDetailsComponent } from './vendor-details/vendor-details.component';
import { VendorFormComponent } from './vendor-form/vendor-form.component';
import { VendorProfileComponent } from './profile/vendor-profile/vendor-profile.component';
import { VendorProductFormComponent } from './products/vendor-product-form/vendor-product-form.component';
import { VendorProductListComponent } from './products/vendor-product-list/vendor-product-list.component';
import { VendorProductDetailsComponent } from './products/vendor-product-details/vendor-product-details.component';
import { VendorInventoryComponent } from './inventory/vendor-inventory/vendor-inventory.component';
import { VendorOrderListComponent } from './orders/vendor-order-list/vendor-order-list.component';
import { VendorOrderDetailsComponent } from './orders/vendor-order-details/vendor-order-details.component';
import { VendorReviewListComponent } from './reviews/vendor-review-list/vendor-review-list.component';
import { VendorSettingsComponent } from './settings/vendor-settings/vendor-settings.component';
import { ChangePasswordComponent } from './settings/change-password/change-password.component';


@NgModule({
  declarations: [
    VendorComponent,
    VendorDashboardComponent,
    VendorDetailsComponent,
    VendorFormComponent,
    VendorProfileComponent,
    VendorProductFormComponent,
    VendorProductListComponent,
    VendorProductDetailsComponent,
    VendorInventoryComponent,
    VendorOrderListComponent,
    VendorOrderDetailsComponent,
    VendorReviewListComponent,
    VendorSettingsComponent,
    ChangePasswordComponent
  ],
  imports: [
    CommonModule,
    VendorRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class VendorModule { }
