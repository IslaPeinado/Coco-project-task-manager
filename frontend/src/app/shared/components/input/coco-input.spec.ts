import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CocoInput } from './coco-input';

describe('CocoInput', () => {
  let component: CocoInput;
  let fixture: ComponentFixture<CocoInput>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CocoInput]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CocoInput);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit value changes', () => {
    spyOn(component.valueChange, 'emit');

    const input: HTMLInputElement = fixture.nativeElement.querySelector('input');
    input.value = 'demo';
    input.dispatchEvent(new Event('input'));

    expect(component.valueChange.emit).toHaveBeenCalledWith('demo');
  });
});
