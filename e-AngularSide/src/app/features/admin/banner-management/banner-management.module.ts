import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BannerManagementRoutingModule } from './banner-management-routing.module';
import { BannerManagementComponent } from './banner-management.component';
import { BannerListComponent } from './banner-list/banner-list.component';
import { BannerFormComponent } from './banner-form/banner-form.component';
import { BannerDetailsComponent } from './banner-details/banner-details.component';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    BannerManagementComponent,
    BannerListComponent,
    BannerFormComponent,
    BannerDetailsComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    BannerManagementRoutingModule
  ]
})
export class BannerManagementModule { }
