import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { VendorResponse, VendorSummary } from 'src/app/models/vendor.model';
import { AdminVendorService } from '../../services/admin-vendor.service';

@Component({
  selector: 'app-vendor-list',
  templateUrl: './vendor-list.component.html',
  styleUrls: ['./vendor-list.component.scss']
})
export class VendorListComponent implements OnInit {
  vendors: VendorResponse[] = [];
  selectedStatus = '';
  searchText = '';
  summary!: VendorSummary;

  constructor(
    private adminVendorService: AdminVendorService,
    private router: Router
  ) {}

statuses = [
  { label: 'All', value: '', count: 0 },
  { label: 'Pending', value: 'PENDING', count: 0 },
  { label: 'Approved', value: 'APPROVED', count: 0 },
  { label: 'Active', value: 'ACTIVE', count: 0 },
  { label: 'Suspended', value: 'SUSPENDED', count: 0 },
  { label: 'Rejected', value: 'REJECTED', count: 0 }
];


  ngOnInit(): void {
    this.loadVendors();
    this.loadSummary();
  }

 loadVendors(): void {

  this.adminVendorService
      .getVendors(this.selectedStatus, this.searchText)
      .subscribe({

        next: (res) => {
          this.vendors = res;
        },

        error: err => console.error(err)

      });

}

onSearch(): void {

  this.loadVendors();

}


loadSummary(): void {

  this.adminVendorService.getVendorSummary().subscribe({

    next: (summary) => {

      this.summary = summary;

      this.statuses = this.statuses.map(tab => ({
        ...tab,
        count: summary[
          (tab.label.toLowerCase() as keyof VendorSummary)
        ] ?? 0
      }));

    }

  });

}


changeStatus(status: string) {

  this.selectedStatus = status;

  this.loadVendors();

}



  viewVendor(id: number): void {
    this.router.navigate(['/admin/vendors', id]);
  }


 deleteVendor(id: number): void {
  if (!confirm('Are you sure you want to delete this vendor?')) {
    return;
  }

  this.adminVendorService.deleteVendor(id).subscribe({
    next: () => {
      this.loadVendors();
      this.loadSummary();
    },
    error: err => console.error(err)
  });
}


}
