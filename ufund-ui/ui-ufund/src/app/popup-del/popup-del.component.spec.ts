import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupDelComponent } from './popup-del.component';

describe('PopupDelComponent', () => {
  let component: PopupDelComponent;
  let fixture: ComponentFixture<PopupDelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PopupDelComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PopupDelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
