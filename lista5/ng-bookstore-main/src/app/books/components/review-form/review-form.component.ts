import {Component, Input} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ReviewService} from "../../services/review.service";
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'bs-review-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './review-form.component.html',
  styleUrl: './review-form.component.scss'
})
export class ReviewFormComponent {

  @Input() bookId!: number;
  @Input() refreshFunction!: Function
  // @Output() reviewAdded = new EventEmitter();

  form: FormGroup = this.fb.group({
    title: ['', Validators.required],
    description: ['', Validators.required],
    rate: [5, Validators.required]
  });

  constructor(private fb: FormBuilder,
              private reviewService: ReviewService) {}

  save() {
    if (this.form.invalid) return;

    const review = {
      ...this.form.value,
      forBook: this.bookId
    };

    this.reviewService.addReview(review).subscribe(() => {
      this.form.reset({ rating: 5 });
      this.refreshFunction();
      // this.reviewAdded.emit("reviewed");
    });
  }
}
