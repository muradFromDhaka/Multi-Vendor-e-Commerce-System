import { Component } from '@angular/core';
import { InventoryListResponse } from '../../models/vendor-inventory.model';
import { VendorInventoryService } from '../../services/vendor-inventory.service';
import { environment } from 'src/app/services/environments';

@Component({
  selector: 'app-vendor-inventory',
  templateUrl: './vendor-inventory.component.html',
  styleUrls: ['./vendor-inventory.component.scss']
})
export class VendorInventoryComponent {

  baseImageUrl = environment.baseImageUrl;

    inventories: InventoryListResponse[] = [];
  
    loading = false;
  
    errorMessage = '';
  
    currentPage = 0;
  
    pageSize = 10;
  
    totalPages = 0;
  
    totalElements = 0;
  
    selectedFilter: 'ALL' | 'LOW_STOCK' | 'OUT_OF_STOCK' = 'ALL';
  
    constructor(
      private vendorInventoryService: VendorInventoryService
    ) { }
  
    ngOnInit(): void {
      this.loadInventory();
    }
  
    loadInventory(): void {
  
      this.loading = true;
  
      this.errorMessage = '';
  
      let request$;
  
      switch (this.selectedFilter) {
  
        case 'LOW_STOCK':
          request$ = this.vendorInventoryService.getLowStockInventory(
            this.currentPage,
            this.pageSize
          );
          break;
  
        case 'OUT_OF_STOCK':
          request$ = this.vendorInventoryService.getOutOfStockInventory(
            this.currentPage,
            this.pageSize
          );
          break;
  
        default:
          request$ = this.vendorInventoryService.getInventory(
            this.currentPage,
            this.pageSize
          );
      }
  
      request$.subscribe({
  
        next: (response) => {
  
          this.inventories = response.content;
  
          this.totalPages = response.totalPages;
  
          this.totalElements = response.totalElements;
  
          this.currentPage = response.number;
  
          this.loading = false;
  
        },
  
        error: (error) => {
  
          console.error(error);
  
          this.errorMessage = 'Failed to load inventory.';
  
          this.loading = false;
  
        }
  
      });
  
    }
  
    changeFilter(filter: 'ALL' | 'LOW_STOCK' | 'OUT_OF_STOCK'): void {
  
      this.selectedFilter = filter;
  
      this.currentPage = 0;
  
      this.loadInventory();
  
    }
  
    changePage(page: number): void {
  
      if (page < 0 || page >= this.totalPages) {
        return;
      }
  
      this.currentPage = page;
  
      this.loadInventory();
  
    }
    
}
