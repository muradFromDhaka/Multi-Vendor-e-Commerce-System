import { TestBed } from '@angular/core/testing';

import { VendorAttributeValueService } from './vendor-attribute-value.service';

describe('VendorAttributeValueService', () => {
  let service: VendorAttributeValueService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VendorAttributeValueService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
