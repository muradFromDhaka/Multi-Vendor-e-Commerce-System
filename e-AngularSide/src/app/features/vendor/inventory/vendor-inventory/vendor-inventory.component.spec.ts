import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VendorInventoryComponent } from './vendor-inventory.component';

describe('VendorInventoryComponent', () => {
  let component: VendorInventoryComponent;
  let fixture: ComponentFixture<VendorInventoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VendorInventoryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VendorInventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
