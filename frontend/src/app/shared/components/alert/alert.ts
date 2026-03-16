import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, HostBinding, Input } from '@angular/core';

@Component({
  selector: 'app-alert',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './alert.html',
  styleUrl: './alert.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Alert {
  @Input() variant: 'info' | 'success' | 'warning' | 'danger' = 'info';
  @Input() title = '';
  @Input() description = '';

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-alert-host';
  }

  get icon(): string {
    switch (this.variant) {
      case 'success':
        return '✓';
      case 'warning':
        return '!';
      case 'danger':
        return '×';
      default:
        return 'i';
    }
  }
}
