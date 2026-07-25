import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductCardComponent } from './components/product-card/product-card.component';
import { AdminRoutingModule } from "src/app/features/admin/admin-routing.module";



@NgModule({
  declarations: [
    ProductCardComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule
],

  exports: [
    ProductCardComponent
  ]

})
export class SharedModule { }
