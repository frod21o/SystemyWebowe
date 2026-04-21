import { ResolveFn } from '@angular/router';
import {Review} from "../model/review";
import {ReviewService} from "../services/review.service";
import {inject} from "@angular/core";

export const reviewResolver: ResolveFn<Review[]> = (route, state) => {
  const bookId = route.paramMap.get('bookId');
  return inject(ReviewService).getReviewsForBook(Number(bookId));
};
