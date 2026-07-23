import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TestComponent } from './test/test.component';


const routes: Routes = [

  {path:'test',component: TestComponent},

  {path: '',loadChildren: () => import('./features/public/public.module').then(m => m.PublicModule)},

  {path: 'customer',loadChildren: () =>import('./features/customer/customer.module').then(m => m.CustomerModule)},

  {path: 'admin',loadChildren: () =>import('./features/admin/admin.module').then(m => m.AdminModule)},

  {path: 'vendor',loadChildren: () =>import('./features/vendor/vendor.module').then(m => m.VendorModule)},

  {path: 'auth',loadChildren: () =>import('./auth/auth.module').then(m => m.AuthModule)}

];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
