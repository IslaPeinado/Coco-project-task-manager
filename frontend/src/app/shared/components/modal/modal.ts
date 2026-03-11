import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, EventEmitter, HostBinding, Input, Output } from '@angular/core';

@Component({
  selector: 'app-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './modal.html',
  styleUrl: './modal.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Modal {
  @Input() open = false;
  @Input() title = '';
  @Input() subtitle = '';
  @Input() size: 'sm' | 'md' | 'lg' = 'md';
  @Input() closeOnBackdrop = true;
  @Input() showCloseButton = true;
  @Output() closed = new EventEmitter<void>();

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-modal-host';
  }

  handleBackdrop(): void {
    if (this.closeOnBackdrop) {
      this.closed.emit();
    }
  }

  handleClose(): void {
    this.closed.emit();
  }
}
