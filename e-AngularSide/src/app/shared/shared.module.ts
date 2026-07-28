import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductCardComponent } from './components/product-card/product-card.component';
import { AdminRoutingModule } from "src/app/features/admin/admin-routing.module";
import { ReviewSummaryComponent } from './review/review-summary/review-summary.component';
import { ReviewListComponent } from './review/review-list/review-list.component';
import { ReviewFormComponent } from './review/review-form/review-form.component';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    ProductCardComponent,
    ReviewFormComponent,
    ReviewListComponent,
    ReviewSummaryComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule
],

  exports: [
    ProductCardComponent,
    ReviewFormComponent,
    ReviewListComponent,
    ReviewSummaryComponent
  ]

})
export class SharedModule { }
