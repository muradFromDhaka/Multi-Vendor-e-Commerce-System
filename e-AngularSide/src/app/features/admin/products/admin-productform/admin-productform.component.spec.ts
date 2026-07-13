import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminProductformComponent } from './admin-productform.component';

describe('AdminProductformComponent', () => {
  let component: AdminProductformComponent;
  let fixture: ComponentFixture<AdminProductformComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminProductformComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminProductformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
