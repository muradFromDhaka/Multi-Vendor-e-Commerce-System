import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VendorFormComponent } from './vendor-form/vendor-form.component';
import { VendorListComponent } from '../admin/vendor-management/vendor-list/vendor-list.component';
import { VendorProfileComponent } from './profile/vendor-profile/vendor-profile.component';
import { VendorDashboardComponent } from './vendor-dashboard/vendor-dashboard.component';
import { VendorComponent } from './vendor.component';
import { VendorProductListComponent } from './products/vendor-product-list/vendor-product-list.component';
import { VendorProductDetailsComponent } from './products/vendor-product-details/vendor-product-details.component';
import { VendorProductFormComponent } from './products/vendor-product-form/vendor-product-form.component';
import { VendorProductVariantListComponent } from './product-variant-management/vendor-product-variant-list/vendor-product-variant-list.component';
import { VendorProductVariantDetailsComponent } from './product-variant-management/vendor-product-variant-details/vendor-product-variant-details.component';
import { VendorProductVariantFormComponent } from './product-variant-management/vendor-product-variant-form/vendor-product-variant-form.component';
import { VendorCustomerListComponent } from './customers/vendor-customer-list/vendor-customer-list.component';
import { VendorCustomerDetailsComponent } from './customers/vendor-customer-details/vendor-customer-details.component';

const routes: Routes = [

{

    path:'',
        component: VendorComponent,
    children:[

        {path:'',redirectTo:'dashboard',pathMatch:'full'},
        {path:'dashboard',component:VendorDashboardComponent},
        {path:'profile',component:VendorProfileComponent},
        {path:'vendors',component:VendorListComponent},
        {path:'vendorForm',component:VendorFormComponent},

        {path:'productList',component:VendorProductListComponent},
        {path:'productDetails/:id',component:VendorProductDetailsComponent},
        {path:'productForm',component:VendorProductFormComponent},
        {path:'productForm/:id',component:VendorProductFormComponent},

        {path:'vendorProductVariantList',component:VendorProductVariantListComponent},
        {path:'vendorProductVariantDetails/:id',component:VendorProductVariantDetailsComponent},
        {path:'vendorProductVariantForm',component:VendorProductVariantFormComponent},
        {path:'vendorProductVariantForm/:id',component:VendorProductVariantFormComponent},
        

        {path:'vendorCustomer',component:VendorCustomerListComponent},
        {path:'vendorCustomer/:userName',component:VendorCustomerDetailsComponent}
    ]

}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VendorRoutingModule { }
