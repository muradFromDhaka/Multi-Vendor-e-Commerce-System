import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BrandResponse } from 'src/app/models/brand.model';
import { environment } from 'src/app/services/environments';
import { BrandService } from 'src/app/services/brand.service';

@Component({
  selector: 'app-brand-list',
  templateUrl: './brand-list.component.html',
  styleUrls: ['./brand-list.component.scss']
})
export class BrandListComponent implements OnInit {

  brands: BrandResponse[] = [];

  loading = false;

  baseImageUrl = environment.baseImageUrl;

  constructor(
    private brandService: BrandService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadBrands();
  }

  loadBrands(): void {

    this.loading = true;

    this.brandService.getAll().subscribe({

      next: (res) => {
        this.brands = res;
        this.loading = false;
      },

      error: (err) => {
        console.error(err);
        this.loading = false;
      }

    });

  }

  openBrand(brand: BrandResponse): void {

    this.router.navigate([
      '/brands',
      brand.id,
      this.createSlug(brand.name)
    ]);

  }

  createSlug(text: string): string {

    return text
      .toLowerCase()
      .trim()
      .replace(/\s+/g, '-');

  }

}