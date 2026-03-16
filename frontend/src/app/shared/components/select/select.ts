import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, EventEmitter, forwardRef, HostBinding, Input, Output } from '@angular/core';
import { ControlValueAccessor, FormsModule, NG_VALUE_ACCESSOR } from '@angular/forms';

export interface SelectOption {
  label: string;
  value: string;
  disabled?: boolean;
}

@Component({
  selector: 'app-select',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './select.html',
  styleUrl: './select.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => Select),
      multi: true,
    },
  ],
})
export class Select implements ControlValueAccessor {
  @Input() label = '';
  @Input() hint = '';
  @Input() error = '';
  @Input() placeholder = 'Select an option';
  @Input() disabled = false;
  @Input() required = false;
  @Input() id = `coco-select-${Math.random().toString(36).slice(2, 9)}`;
  @Input() options: SelectOption[] = [];
  @Output() valueChange = new EventEmitter<string>();

  value = '';
  isFocused = false;

  private onChange: (value: string) => void = () => undefined;
  private onTouched: () => void = () => undefined;

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-select-host';
  }

  handleValueChange(nextValue: string): void {
    this.value = nextValue;
    this.onChange(nextValue);
    this.valueChange.emit(nextValue);
  }

  handleBlur(): void {
    this.isFocused = false;
    this.onTouched();
  }

  writeValue(value: string | null): void {
    this.value = value ?? '';
  }

  registerOnChange(fn: (value: string) => void): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }
}
