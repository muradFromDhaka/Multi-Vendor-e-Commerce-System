import { TestBed } from '@angular/core/testing';

import { VendorAttributeService } from './vendor-attribute.service';

describe('VendorAttributeService', () => {
  let service: VendorAttributeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VendorAttributeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
