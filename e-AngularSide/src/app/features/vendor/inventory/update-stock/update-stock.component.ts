import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import {
  InventoryListResponse,
  UpdateInventoryRequest
} from '../../models/vendor-inventory.model';

import { VendorInventoryService } from '../../services/vendor-inventory.service';

@Component({
  selector: 'app-update-stock',
  templateUrl: './update-stock.component.html',
  styleUrls: ['./update-stock.component.scss']
})
export class UpdateStockComponent implements OnInit {

  inventory!: InventoryListResponse;

  variantId!: number;

  stock = 0;

  loading = false;

  errorMessage = '';

  constructor(
    private vendorInventoryService: VendorInventoryService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {

    this.variantId = Number(
      this.route.snapshot.paramMap.get('id')
    );

      console.log('Variant ID =', this.variantId);

    this.loadInventory();

  }

  loadInventory(): void {

    this.loading = true;

    this.vendorInventoryService
      .getInventoryDetails(this.variantId)
      .subscribe({

        next: (response) => {

          this.inventory = response;

          this.stock = response.stock;

          this.loading = false;

        },

        error: (error) => {

          console.error(error);

          this.errorMessage = 'Failed to load inventory details.';

          this.loading = false;

        }

      });

  }

  save(): void {

    if (this.stock < 0) {

      this.errorMessage = 'Stock cannot be negative.';

      return;

    }

    this.loading = true;

    const request: UpdateInventoryRequest = {

      stock: this.stock

    };

    this.vendorInventoryService
      .updateStock(this.variantId, request)
      .subscribe({

        next: () => {

          this.router.navigate(['/vendor/inventory']);

        },

        error: (error) => {

          console.error(error);

          this.loading = false;

          this.errorMessage = 'Failed to update stock.';

        }

      });

  }

  cancel(): void {

    this.router.navigate(['/vendor/inventory']);

  }

}