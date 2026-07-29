import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BannerManagementComponent } from './banner-management.component';
import { BannerListComponent } from './banner-list/banner-list.component';
import { BannerFormComponent } from './banner-form/banner-form.component';
import { BannerDetailsComponent } from './banner-details/banner-details.component';


const routes: Routes = [

  {
    path: '',
    component: BannerManagementComponent,

    children: [

      {path: 'banner',component: BannerListComponent},

      {path: 'bannerForm',component: BannerFormComponent},

      {path: 'bannerForm/:id',component: BannerFormComponent},

      {path: 'bannerDetails/:id',component: BannerDetailsComponent}

    ]

  }

];



@NgModule({

  imports: [
    RouterModule.forChild(routes)
  ],

  exports: [
    RouterModule
  ]

})
export class BannerManagementRoutingModule {}