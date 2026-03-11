import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Toast } from './toast';

describe('Toast', () => {
  let component: Toast;
  let fixture: ComponentFixture<Toast>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Toast]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Toast);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit dismissed event', () => {
    spyOn(component.dismissed, 'emit');
    component.handleDismiss();
    expect(component.dismissed.emit).toHaveBeenCalled();
  });
});
