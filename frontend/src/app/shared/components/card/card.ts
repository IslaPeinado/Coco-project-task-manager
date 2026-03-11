import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, HostBinding, Input } from '@angular/core';

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './card.html',
  styleUrl: './card.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Card {
  @Input() title = '';
  @Input() subtitle = '';
  @Input() padded = true;
  @Input() elevated = true;
  @Input() interactive = false;

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-card-host';
  }
}
