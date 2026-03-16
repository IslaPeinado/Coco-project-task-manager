import { CommonModule } from '@angular/common';
import {ChangeDetectionStrategy, Component, EventEmitter, forwardRef, HostBinding, Output, Input} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';

@Component({
  selector: 'app-input',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './coco-input.html',
  styleUrl: './coco-input.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => CocoInput),
      multi: true,
    },
  ],
})
export class CocoInput implements ControlValueAccessor {
  @Input() label = '';
  @Input() placeholder = '';
  @Input() type: 'text' | 'email' | 'password' | 'search' | 'number' = 'text';
  @Input() hint = '';
  @Input() error = '';
  @Input() disabled = false;
  @Input() required = false;
  @Input() id = `coco-input-${Math.random().toString(36).slice(2, 9)}`;
  @Input() prefix = '';
  @Input() suffix = '';
  @Output() valueChange = new EventEmitter<string>();

  value = '';
  isFocused = false;

  private onChange: (value: string) => void = () => undefined;
  private onTouched: () => void = () => undefined;

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-input-host';
  }

  handleInput(event: Event): void {
    const nextValue = (event.target as HTMLInputElement).value;
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
