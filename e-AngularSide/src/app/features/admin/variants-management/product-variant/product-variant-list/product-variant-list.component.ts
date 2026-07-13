import { Component, OnInit } from '@angular/core';
import { ProductVariantResponse } from '../../../models/variants/productVariant.model';
import { ProductVariantService } from '../../../services/variants/product-variant.service';
import { environment } from 'src/app/services/environments';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-variant-list',
  templateUrl: './product-variant-list.component.html',
  styleUrls: ['./product-variant-list.component.scss']
})
export class ProductVariantListComponent implements OnInit {

  variants: ProductVariantResponse[] = [];

  baseImageUrl = environment.baseImageUrl;

  loading = false;

  page = 0;

  size = 10;

  totalPages = 0;

  totalElements = 0;

  constructor(
    private productVariantService: ProductVariantService,
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

    this.productVariantService
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
        '/admin/productVariantDetails',
        id
    ]);

}



editVariant(id:number){

    this.router.navigate([
        '/admin/productVariantForm',
        id
    ]);

}



deleteVariant(id:number){

    if(confirm('Are you sure you want to delete this variant?')){

        this.productVariantService.delete(id)
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