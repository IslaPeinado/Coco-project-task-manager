import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Textarea } from './textarea';

describe('Textarea', () => {
  let component: Textarea;
  let fixture: ComponentFixture<Textarea>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Textarea]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Textarea);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit textarea changes', () => {
    spyOn(component.valueChange, 'emit');

    const textarea: HTMLTextAreaElement = fixture.nativeElement.querySelector('textarea');
    textarea.value = 'demo';
    textarea.dispatchEvent(new Event('input'));

    expect(component.valueChange.emit).toHaveBeenCalledWith('demo');
  });
});
