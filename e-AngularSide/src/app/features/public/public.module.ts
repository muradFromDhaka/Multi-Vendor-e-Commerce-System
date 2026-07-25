import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PublicRoutingModule } from './public-routing.module';
import { PublicComponent } from './public.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrandProductsComponent } from './brands/brand-products/brand-products.component';
import { CategoryProductsComponent } from './category-products/category-products.component';
import { HomeComponent } from './home/home.component';
import { PublicProductViewComponent } from './public-product-view/public-product-view.component';
import { PublicProductListComponent } from './public-product-list/public-product-list.component';
import { CategoryListComponent } from './category-list/category-list.component';
import { BrandListComponent } from './brands/brand-list/brand-list.component';
import { HeaderComponent } from './home/header/header.component';
import { HeroBannerComponent } from './home/hero-banner/hero-banner.component';
import { FlashSaleComponent } from './home/flash-sale/flash-sale.component';
import { CategorySectionComponent } from './home/category-section/category-section.component';
import { FeaturedProductsComponent } from './home/featured-products/featured-products.component';
import { NewArrivalsComponent } from './home/new-arrivals/new-arrivals.component';
import { BestSellingProductsComponent } from './home/best-selling-products/best-selling-products.component';
import { TopRatedProductsComponent } from './home/top-rated-products/top-rated-products.component';
import { TopVendorsComponent } from './home/top-vendors/top-vendors.component';
import { DealsSectionComponent } from './home/deals-section/deals-section.component';
import { WhyChooseUsComponent } from './home/why-choose-us/why-choose-us.component';
import { CustomerReviewsComponent } from './home/customer-reviews/customer-reviews.component';
import { NewsletterComponent } from './home/newsletter/newsletter.component';
import { FooterComponent } from './home/footer/footer.component';


@NgModule({
  declarations: [
    PublicComponent,
    BrandProductsComponent,
    CategoryProductsComponent,
    HomeComponent,
    PublicProductViewComponent,
    PublicProductListComponent,
    CategoryListComponent,
    BrandListComponent,
    HeaderComponent,
    HeroBannerComponent,
    FlashSaleComponent,
    CategorySectionComponent,
    FeaturedProductsComponent,
    NewArrivalsComponent,
    BestSellingProductsComponent,
    TopRatedProductsComponent,
    TopVendorsComponent,
    DealsSectionComponent,
    WhyChooseUsComponent,
    CustomerReviewsComponent,
    NewsletterComponent,
    FooterComponent,
  
  ],
  imports: [
    CommonModule,
    PublicRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class PublicModule { }
