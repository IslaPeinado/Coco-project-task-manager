import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, HostBinding, Input } from '@angular/core';

export interface FooterLinkItem {
  label: string;
  href?: string;
}

export interface FooterSectionItem {
  title: string;
  links: FooterLinkItem[];
}

export interface FooterSocialItem {
  id: string;
  icon: string;
  ariaLabel: string;
  href?: string;
}

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './footer.html',
  styleUrl: './footer.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Footer {
  @Input() brand = 'COCO';
  @Input() tagline = 'Project management for teams that need clarity, pace and control.';
  @Input() caption = 'Professional cocoa-themed workspace';
  @Input() copyright = `(c) ${new Date().getFullYear()} COCO`;
  @Input() legalText = 'All rights reserved.';
  @Input() sections: FooterSectionItem[] = [];
  @Input() metaLinks: FooterLinkItem[] = [];
  @Input() socialLinks: FooterSocialItem[] = [];
  @Input() compact = false;

  @HostBinding('class')
  get hostClass(): string {
    return 'coco-footer-host';
  }
}
