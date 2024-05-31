import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HelperDetailComponent } from './helper-detail.component';

describe('HelperDetailComponent', () => {
  let component: HelperDetailComponent;
  let fixture: ComponentFixture<HelperDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HelperDetailComponent]
    });
    fixture = TestBed.createComponent(HelperDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
