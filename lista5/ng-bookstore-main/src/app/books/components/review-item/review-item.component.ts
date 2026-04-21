import {Component, Input} from '@angular/core';
import {Review} from "../../model/review";

@Component({
  selector: 'bs-review-item',
  standalone: true,
  imports: [],
  templateUrl: './review-item.component.html',
  styleUrl: './review-item.component.scss'
})
export class ReviewItemComponent {
  @Input() review!: Review;
}
