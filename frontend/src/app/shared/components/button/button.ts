import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, HostBinding, Input } from '@angular/core';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './button.html',
  styleUrl: './button.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Button {
  @Input() variant: 'primary' | 'secondary' | 'ghost' = 'primary';
  @Input() size: 'sm' | 'md' | 'lg' = 'md';
  @Input() type: 'button' | 'submit' | 'reset' = 'button';
  @Input() fullWidth = false;
  @Input() disabled = false;
  @Input() loading = false;

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-button-host';
  }
}
