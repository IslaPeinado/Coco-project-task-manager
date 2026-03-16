import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, EventEmitter, forwardRef, HostBinding, Input, Output } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'app-checkbox',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './checkbox.html',
  styleUrl: './checkbox.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => Checkbox),
      multi: true,
    },
  ],
})
export class Checkbox implements ControlValueAccessor {
  @Input() label = '';
  @Input() hint = '';
  @Input() disabled = false;
  @Input() id = `coco-checkbox-${Math.random().toString(36).slice(2, 9)}`;
  @Output() checkedChange = new EventEmitter<boolean>();

  checked = false;

  private onChange: (value: boolean) => void = () => undefined;
  private onTouched: () => void = () => undefined;

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-checkbox-host';
  }

  handleChange(event: Event): void {
    const nextValue = (event.target as HTMLInputElement).checked;
    this.checked = nextValue;
    this.onChange(nextValue);
    this.checkedChange.emit(nextValue);
  }

  handleBlur(): void {
    this.onTouched();
  }

  writeValue(value: boolean | null): void {
    this.checked = !!value;
  }

  registerOnChange(fn: (value: boolean) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }
}
