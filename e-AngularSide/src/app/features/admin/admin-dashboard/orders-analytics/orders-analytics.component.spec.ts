import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdersAnalyticsComponent } from './orders-analytics.component';

describe('OrdersAnalyticsComponent', () => {
  let component: OrdersAnalyticsComponent;
  let fixture: ComponentFixture<OrdersAnalyticsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrdersAnalyticsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrdersAnalyticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
