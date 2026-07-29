import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DealManagementComponent } from './deal-management.component';
import { DealListComponent } from './deal-list/deal-list.component';
import { DealFormComponent } from './deal-form/deal-form.component';
import { DealDetailsComponent } from './deal-details/deal-details.component';

const routes: Routes = [

  {
    path: '',
    component: DealManagementComponent,

    children: [

      { path: '', redirectTo: 'deal', pathMatch: 'full' },

      { path: 'deal', component: DealListComponent },

      { path: 'dealForm', component: DealFormComponent },

      { path: 'dealForm/:id', component: DealFormComponent },

      { path: 'dealDetails/:id', component: DealDetailsComponent }

    ]
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DealManagementRoutingModule { }