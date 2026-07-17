import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CartComponent } from './cart/cart.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { CustomerComponent } from './customer.component';
import { OrderListComponent } from './order-list/order-list.component';
import { OrderDetailsComponent } from './order-details/order-details.component';

const routes: Routes = [
  {
    path: '',
    component: CustomerComponent,
    children: [

      // { path: '', redirectTo: 'dashboard', pathMatch: 'full' },


      { path: 'cart', component: CartComponent },

      { path: 'checkout', component: CheckoutComponent },

      { path: 'orders', component: OrderListComponent },
 
      { path: 'orders/:id', component: OrderDetailsComponent },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
