import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AttributeService } from '../../services/variants/attribute.service';
import { AttributeValueService } from '../../services/variants/attribute-value.service';
import { AttributeResponse } from '../../models/variants/attribute.model';
import { AttributeValueRequest, AttributeValueResponse } from '../../models/variants/attributeValue.model';

@Component({
  selector: 'app-attribute-value',
  templateUrl: './attribute-value.component.html',
  styleUrls: ['./attribute-value.component.scss'],
})
export class AttributeValueComponent implements OnInit {
  form!: FormGroup;

  attributes: AttributeResponse[] = [];

  attributeValues: AttributeValueResponse[] = [];

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
    private attributeValueService: AttributeValueService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      value: ['', Validators.required],
      attributeId: [null, Validators.required],
    });

    this.loadAttributes();
    this.loadData();
  }

  get f() {
    return this.form.controls;
  }

  loadAttributes() {
    this.attributeService.getAll(0, 100).subscribe({
      next: (res) => {
        this.attributes = res.content;
      },
    });
  }

  loadData() {
    this.loading = true;

    this.attributeValueService.getAll(this.page, this.size).subscribe({
      next: (res) => {
        this.attributeValues = res.content;
        this.totalPages = res.totalPages;
        this.totalElements = res.totalElements;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      },
    });
  }

  save() {
    this.submitted = true;

    if (this.form.invalid) return;

    const dto: AttributeValueRequest = this.form.value;

    if (this.editing) {
      this.attributeValueService.update(this.selectedId, dto).subscribe(() => {
        this.cancelEdit();
        this.loadData();
      });
    } else {
      this.attributeValueService.create(dto).subscribe(() => {
        this.form.reset();
        this.submitted = false;
        this.loadData();
      });
    }
  }

  edit(item: AttributeValueResponse) {
    this.editing = true;
    this.selectedId = item.id;

    const attribute = this.attributes.find(
      (a) => a.name === item.attributeName
    );

    this.form.patchValue({
      value: item.value,
      attributeId: item.attributeId,
      attributeName: item.attributeName
    });
  }

  delete(id: number) {
    if (!confirm('Delete this Attribute Value?')) return;

    this.attributeValueService.delete(id).subscribe(() => {
      this.loadData();
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
      this.loadData();
    }
  }

  next() {
    if (this.page + 1 < this.totalPages) {
      this.page++;
      this.loadData();
    }
  }
}