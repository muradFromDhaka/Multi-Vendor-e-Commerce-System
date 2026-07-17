import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ProductVariantService } from 'src/app/features/admin/services/product-variant.service';
import { ProductVariantResponse } from 'src/app/models/productVariant.model';
import { environment } from 'src/app/services/environments';
import { VendorProductVariantService } from '../../services/vendor-product-variant.service';

@Component({
  selector: 'app-vendor-product-variant-list',
  templateUrl: './vendor-product-variant-list.component.html',
  styleUrls: ['./vendor-product-variant-list.component.scss']
})
export class VendorProductVariantListComponent {

   variants: ProductVariantResponse[] = [];
  
    baseImageUrl = environment.baseImageUrl;
  
    loading = false;
  
    page = 0;
  
    size = 10;
  
    totalPages = 0;
  
    totalElements = 0;
  
    constructor(
      private vendorProductVariantService: VendorProductVariantService,
      public router: Router
    ) {}
  
    ngOnInit(): void {
  
      this.loadVariants();
    }
  
    // ==========================
    // Load All Variants
    // ==========================
  
    loadVariants(): void {
  
      this.loading = true;
  
      this.vendorProductVariantService
        .getAll(this.page, this.size)
        .subscribe({
  
          next: (res) => {
  
            this.variants = res.content;
  
            this.totalPages = res.totalPages;
  
            this.totalElements = res.totalElements;
  
            this.loading = false;
          },
  
          error: (err) => {
  
            console.error(err);
  
            this.loading = false;
          }
  
        });
    }
  
    // ==========================
    // Pagination
    // ==========================
  
    previousPage(): void {
  
      if (this.page > 0) {
  
        this.page--;
  
        this.loadVariants();
      }
    }
  
    nextPage(): void {
  
      if (this.page < this.totalPages - 1) {
  
        this.page++;
  
        this.loadVariants();
      }
    }
  
  
    viewVariant(id:number){
  
      // details page
      this.router.navigate([
          '/vendor/vendorProductVariantDetails',
          id
      ]);
  
  }
  
  
  
  editVariant(id:number){
  
      this.router.navigate([
          '/vendor/vendorProductVariantForm',
          id
      ]);
  
  }
  
  
  
  deleteVariant(id:number){
  
      if(confirm('Are you sure you want to delete this variant?')){
  
          this.vendorProductVariantService.delete(id)
          .subscribe({
  
              next:()=>{
  
                  this.loadVariants();
              },
  
              error:(err)=>{
  
                  console.log(err);
  
              }
  
          });
  
      }
  
  }
  
  trackByVariant(index: number, variant: ProductVariantResponse): number {
    return variant.id;
  }
  
}
