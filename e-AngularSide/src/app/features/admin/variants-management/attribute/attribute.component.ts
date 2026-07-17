import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AttributeService } from '../../services/variants/attribute.service';
import { AttributeRequest, AttributeResponse } from '../../../../models/variants/attribute.model';
import { CategoryResponse } from 'src/app/models/category.model';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-attribute',
  templateUrl: './attribute.component.html',
  styleUrls: ['./attribute.component.scss'],
})
export class AttributeComponent implements OnInit {
  form!: FormGroup;

  attributes: AttributeResponse[] = [];
  categories: CategoryResponse[] = [];

  searchText = '';
  allAttributes: AttributeResponse[] = [];

  loading = false;

  submitted = false;

  editing = false;

  selectedId = 0;

  page = 0;

  size = 10;

  totalPages = 0;

  totalElements = 0;

  constructor(
    private fb: FormBuilder,
    private attributeService: AttributeService,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      name: ['', Validators.required],
      categoryId: [null, Validators.required]

    });

    this.loadAttribute();
    this.loadCategories();
  }

  get f() {
    return this.form.controls;
  }

  loadAttribute() {
    this.loading = true;

    this.attributeService.getAll(this.page, this.size).subscribe({
      next: (res) => {
        this.allAttributes = res.content;
        this.attributes = res.content;
        this.totalPages = res.totalPages;
        this.totalElements = res.totalElements;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      },
    });
  }

  loadCategories() {

  this.categoryService
    .getAllCategories()
    .subscribe(res => {

      this.categories = res.content;

    });

}


search(): void {

  const keyword = this.searchText.toLowerCase();

  this.attributes = this.allAttributes.filter(item =>
      item.name.toLowerCase().includes(keyword) ||
      item.categoryName.toLowerCase().includes(keyword)
  );

}


 save() {

  this.submitted = true;

  if (this.form.invalid) return;

  const dto: AttributeRequest = this.form.value;

  if (this.editing) {

    this.attributeService
      .update(this.selectedId, dto)
      .subscribe({

        next: () => {

          alert('Attribute Updated Successfully');

          this.cancelEdit();

          this.loadAttribute();

        },

        error: (err) => {

          alert(
            err.error?.message || 'Something went wrong.'
          );

        }

      });

  } else {

    this.attributeService
      .create(dto)
      .subscribe({

        next: () => {

          alert('Attribute Saved Successfully');

          this.form.reset();

          this.submitted = false;

          this.loadAttribute();

        },

        error: (err) => {

          alert(
            err.error?.message || 'Something went wrong.'
          );

        }

      });

  }
}

  edit(item: AttributeResponse) {
    this.editing = true;
    this.selectedId = item.id;

    this.form.patchValue({
      name: item.name,
      categoryId: item.categoryId

    });
  }

  delete(id: number) {
    if (!confirm('Delete this attribute?')) return;

    this.attributeService.delete(id).subscribe(() => {
      this.loadAttribute();
    });
  }

  cancelEdit() {
    this.editing = false;
    this.selectedId = 0;
    this.submitted = false;
    this.form.reset();
  }

  previous() {
    if (this.page > 0) {
      this.page--;
      this.loadAttribute();
    }
  }

  next() {
    if (this.page + 1 < this.totalPages) {
      this.page++;
      this.loadAttribute();
    }
  }
}