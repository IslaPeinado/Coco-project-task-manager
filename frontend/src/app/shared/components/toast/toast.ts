import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, EventEmitter, HostBinding, Input, Output } from '@angular/core';

@Component({
  selector: 'app-toast',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './toast.html',
  styleUrl: './toast.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Toast {
  @Input() title = '';
  @Input() description = '';
  @Input() tone: 'info' | 'success' | 'warning' | 'danger' = 'info';
  @Input() closable = true;
  @Output() dismissed = new EventEmitter<void>();

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-toast-host';
  }

  handleDismiss(): void {
    this.dismissed.emit();
  }
}
