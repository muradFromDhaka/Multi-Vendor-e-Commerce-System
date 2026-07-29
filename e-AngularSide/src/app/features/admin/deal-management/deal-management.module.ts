import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { DealManagementRoutingModule } from './deal-management-routing.module';

import { DealManagementComponent } from './deal-management.component';
import { DealListComponent } from './deal-list/deal-list.component';
import { DealFormComponent } from './deal-form/deal-form.component';
import { DealDetailsComponent } from './deal-details/deal-details.component';

@NgModule({

  declarations: [

    DealManagementComponent,
    DealListComponent,
    DealFormComponent,
    DealDetailsComponent

  ],

  imports: [

    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    DealManagementRoutingModule

  ]

})
export class DealManagementModule { }