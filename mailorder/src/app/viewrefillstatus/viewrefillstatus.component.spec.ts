import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewrefillstatusComponent } from './viewrefillstatus.component';

describe('ViewrefillstatusComponent', () => {
  let component: ViewrefillstatusComponent;
  let fixture: ComponentFixture<ViewrefillstatusComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewrefillstatusComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewrefillstatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
