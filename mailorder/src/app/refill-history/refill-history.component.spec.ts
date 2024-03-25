import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RefillHistoryComponent } from './refill-history.component';

describe('RefillHistoryComponent', () => {
  let component: RefillHistoryComponent;
  let fixture: ComponentFixture<RefillHistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RefillHistoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RefillHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
