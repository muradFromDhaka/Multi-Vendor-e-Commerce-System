import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BrandRequest } from 'src/app/models/brand.model';
import { BrandService } from 'src/app/services/brand.service';

@Component({
  selector: 'app-brand-form',
  templateUrl: './brand-form.component.html',
  styleUrls: ['./brand-form.component.scss']
})
export class BrandFormComponent {

  brandForm!: FormGroup;
  editingBrand = false;
  brandId!: number;
  selectedLogo?: File;
  logoPreview?: string;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private brandService: BrandService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.brandForm = this.fb.group({
      name: ['', [Validators.required, Validators.maxLength(100)]],
      description: ['', Validators.maxLength(500)],
    });

    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      this.editingBrand = true;
      this.brandId = +idParam;

      this.brandService.getById(this.brandId).subscribe({
        next: (brand) => {
          this.brandForm.patchValue({
            name: brand.name,
            description: brand.description || ''
          });

          if (brand.logoUrl) {
            this.logoPreview = 'http://localhost:8080/' + brand.logoUrl;
          }
        },
        error: () => {
          alert('Failed to load brand');
          this.router.navigate(['/adminLayout/brand']);
        }
      });
    }
  }

  onLogoSelected(event: any): void {
    const file: File = event.target.files[0];
    if (file) {
      this.selectedLogo = file;
      const reader = new FileReader();
      reader.onload = () => this.logoPreview = reader.result as string;
      reader.readAsDataURL(file);
    }
  }

  removeLogo(): void {
    this.selectedLogo = undefined;
    this.logoPreview = undefined;
  }

  onSubmit(): void {
    if (this.brandForm.invalid) return;

    this.loading = true;
    const brandData: BrandRequest = this.brandForm.value;

    if (this.editingBrand) {
      // ✅ FIXED
      this.brandService.update(this.brandId, brandData, this.selectedLogo).subscribe({
        next: () => {
          alert('Brand updated successfully!');
          this.router.navigate(['/admin/brand']);
        },
        error: () => this.loading = false
      });
    } else {
      this.brandService.create(brandData, this.selectedLogo).subscribe({
        next: () => {
          alert('Brand created successfully!');
          this.brandForm.reset();
          this.logoPreview = undefined;
          this.router.navigate(['/admin/brand']);
        },
        error: () => this.loading = false
      });
    }
  }
}
