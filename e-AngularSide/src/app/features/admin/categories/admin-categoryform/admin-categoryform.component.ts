import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/app/services/environments';
import { CategoryRequest, CategoryResponse } from 'src/app/models/category.model';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-admin-categoryform',
  templateUrl: './admin-categoryform.component.html',
  styleUrls: ['./admin-categoryform.component.scss']
})
export class AdminCategoryformComponent implements OnInit {

  categoryForm!: FormGroup;
  baseImageUrl = environment.baseImageUrl;
  editingCategory = false;
  categoryId?: number;

  parents: CategoryResponse[] = [];

  selectedImage?: File;
  imagePreview?: string;

  loading = false;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  // ---------------- LIFECYCLE ----------------
  ngOnInit(): void {
    this.initForm();
    this.loadRouteData();
    this.loadParentCategories();
  }

  // ---------------- FORM ----------------
  private initForm(): void {
    this.categoryForm = this.fb.group({
      name: ['', [Validators.required, Validators.maxLength(150)]],
      parentId: [null]
    });
  }

  // ---------------- ROUTE ----------------
  private loadRouteData(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) return;

    this.editingCategory = true;
    this.categoryId = Number(id);

    this.categoryService.getCategoryById(this.categoryId).subscribe({
      next: category => {
        this.categoryForm.patchValue({
          name: category.name,
          parentId: category.parentId
        });
        this.imagePreview = category.imageUrl || undefined;
      }
    });
  }

  // ---------------- API ----------------
  private loadParentCategories(): void {
    this.categoryService.getAllRootCategories().subscribe({
      next: page => this.parents = page.content
    });
  }

  // ---------------- IMAGE ----------------
  onImageSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return;

    const file = input.files[0];

    // ✅ size validation (2MB)
    if (file.size > 2 * 1024 * 1024) {
      alert('Image must be under 2MB');
      input.value = '';
      return;
    }

    this.selectedImage = file;

    const reader = new FileReader();
    reader.onload = () => this.imagePreview = reader.result as string;
    reader.readAsDataURL(file);
  }

  removeImage(): void {
    this.selectedImage = undefined;
    this.imagePreview = undefined;
  }

  // ---------------- SUBMIT ----------------
  onSubmit(): void {
    if (this.categoryForm.invalid || this.loading) return;

    this.loading = true;

    const payload: CategoryRequest = {
      name: this.categoryForm.value.name,
      parentId: this.categoryForm.value.parentId
    };

    const request$ = this.editingCategory
      ? this.categoryService.updateCategory(this.categoryId!, payload, this.selectedImage)
      : this.categoryService.createCategory(payload, this.selectedImage);

    request$.subscribe({
      next: () => {
        alert(`Category ${this.editingCategory ? 'updated' : 'created'} successfully`);
        this.router.navigate(['/admin/adminCategoryList']);
      },
      error: () => this.loading = false
    });
  }
}
