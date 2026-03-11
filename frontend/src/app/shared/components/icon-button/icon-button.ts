import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, HostBinding, Input } from '@angular/core';

@Component({
  selector: 'app-icon-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './icon-button.html',
  styleUrl: './icon-button.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class IconButton {
  @Input() variant: 'surface' | 'ghost' | 'primary' = 'surface';
  @Input() size: 'sm' | 'md' | 'lg' = 'md';
  @Input() disabled = false;
  @Input() ariaLabel = 'Icon button';
  @Input() type: 'button' | 'submit' | 'reset' = 'button';

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-icon-button-host';
  }
}
