import { Component } from '@angular/core';
import { Book } from '../../model/book';
import { ActivatedRoute, RouterLink } from '@angular/router';
import {Review} from "../../model/review";
import {ReviewItemComponent} from "../review-item/review-item.component";
import {CommonModule} from "@angular/common";
import {ReviewFormComponent} from "../review-form/review-form.component";
import {ReviewService} from "../../services/review.service";

@Component({
  selector: 'bs-book-details',
  standalone: true,
  imports: [RouterLink, ReviewItemComponent, CommonModule, ReviewFormComponent],
  templateUrl: './book-details.component.html',
  styleUrl: './book-details.component.scss'
})

export class BookDetailsComponent {
  readonly book: Book;

  reviews: Review[] = [];

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private reviewService: ReviewService
  ) {
    this.book = this.activatedRoute.snapshot.data['book'];
    this.reviews = this.activatedRoute.snapshot.data['reviews'];
  }

  reloadReviews() {
    this.reviewService
      .getReviewsForBook(this.book.id)
      .subscribe(data => this.reviews = data);
  }
}
