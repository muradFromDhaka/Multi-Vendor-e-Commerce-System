import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FinancePaymentComponent } from './finance-payment.component';

describe('FinancePaymentComponent', () => {
  let component: FinancePaymentComponent;
  let fixture: ComponentFixture<FinancePaymentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FinancePaymentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FinancePaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
