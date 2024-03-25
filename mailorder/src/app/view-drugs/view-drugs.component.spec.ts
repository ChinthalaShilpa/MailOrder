import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewDrugsComponent } from './view-drugs.component';

describe('ViewDrugsComponent', () => {
  let component: ViewDrugsComponent;
  let fixture: ComponentFixture<ViewDrugsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewDrugsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewDrugsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
