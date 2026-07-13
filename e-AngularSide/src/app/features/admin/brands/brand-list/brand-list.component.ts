import { Component } from '@angular/core';
import { BrandResponse } from 'src/app/models/brand.model';
import { AuthService } from 'src/app/services/auth.service';
import { BrandService } from 'src/app/services/brand.service';

@Component({
  selector: 'app-brand-list',
  templateUrl: './brand-list.component.html',
  styleUrls: ['./brand-list.component.scss']
})
export class BrandListComponent {

   brands: BrandResponse[] = [];
  loading = false;
  editingBrand?: BrandResponse;

  constructor(
    private brandService: BrandService,
    public authService: AuthService) { }

  ngOnInit(): void {
    this.loadBrands();
  }

  loadBrands(): void {
    this.loading = true;
    this.brandService.getAll().subscribe({
      next: data => {
        this.brands = data;
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }



  deleteBrand(id: number): void {
    if (confirm('Are you sure to delete this brand?')) {
      this.brandService.delete(id).subscribe({
        next: () => this.loadBrands()
      });
    }
  }

  onFormSaved(): void {
    this.editingBrand = undefined;
    this.loadBrands();
  }

}
