import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {Review} from "../model/review";

const reviewsApiPrefix = '/api/reviews';

@Injectable({ providedIn: 'root' })
export class ReviewService {


  constructor(private http: HttpClient) {}

  getReviewsForBook(bookId: number): Observable<Review[]> {
    console.log(`api request: ${reviewsApiPrefix}?forBook=${bookId}`);
    return this.http.get<Review[]>(`${reviewsApiPrefix}?forBook=${bookId}`);
  }

  addReview(review: Review): Observable<Review> {
    return this.http.post<Review>(reviewsApiPrefix, review);
  }
}
