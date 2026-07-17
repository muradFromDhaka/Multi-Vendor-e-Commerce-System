import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { OrderResponse } from 'src/app/models/order.model';
import { VendorResponse } from 'src/app/models/vendor.model';
import { OrderService } from 'src/app/services/order.service';
import { ProductService } from 'src/app/services/product.service';
import { VendorService } from '../services/vendor.service';
import { ProductListResponse } from 'src/app/models/product.model';
import { VendorOrderService } from '../services/vendor-order.service';
import { VendorOrderListResponse } from '../services/vendorOrder.model';
import { VendorProductService } from '../services/vendor-product.service';

@Component({
  selector: 'app-vendor-dashboard',
  templateUrl: './vendor-dashboard.component.html',
  styleUrls: ['./vendor-dashboard.component.scss']
})
export class VendorDashboardComponent implements OnInit {

  activeTab: string = 'profile';
  modalOpen: boolean = false;
  editingProduct: any = null;

   vendor?: VendorResponse;

  orders: VendorOrderListResponse[] = [];
   

  products: ProductListResponse[] = [];


  productForm!: FormGroup; 

  constructor(
    private router: Router,
    private vendorProducService: VendorProductService,
    private vendorService: VendorService,
    private vendorOrderService: VendorOrderService,
  ){}
  ngOnInit(): void {
    this.loadVendor();
    this.loadProducts();
    this.loadOrders();
  }

   loadVendor() {
    this.vendorService.getMyVendor().subscribe((res) => this.vendor = res);
  }

    loadProducts() {
    this.vendorProducService.getMyProducts()
      .subscribe(res => this.products = res.content);
  }


  loadOrders() {
    this.vendorOrderService.getVendorOrders()
      .subscribe(res => this.orders = res.content);
  }


  selectTab(tab: string) {
    this.activeTab = tab;
    // this.router.navigate(['/vendor/vendorProfile'])
  }


  openModal() {
    this.modalOpen = true;
    this.editingProduct = null;
  }

  closeModal() {
    this.modalOpen = false;
  }

  editProduct(product: any) {
    this.editingProduct = product;
    this.modalOpen = true;
  }

  saveProduct() {
    if(this.editingProduct) {
      Object.assign(this.editingProduct, this.productForm);
    } else {
      // this.products.push({ ...this.productForm });
    }
    this.closeModal();
  }

  deleteProduct(product: ProductListResponse) {
    if (!confirm('Delete Product?')) {
        return;
    }
    this.vendorProducService
        .deleteProduct(product.id)
        .subscribe(() => {
            this.loadProducts();
        });
}

}
