import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Book } from '../model/book';

const booksApiPrefix = '/api/books';


@Injectable({
  providedIn: 'root'
})
export class BooksService {

  constructor(private readonly http: HttpClient) { }

  getAllBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(booksApiPrefix);
  }

  findBookById(id: number): Observable<Book> {
    return this.http.get<Book>(`${booksApiPrefix}/${id}`);
  }

  saveBook(id: number, book: any): Observable<Object> {
    return this.http.put(`${booksApiPrefix}/${id}`, book);
  }

  searchBooks(query: string) {
    return this.http.get<Book[]>(
      `${booksApiPrefix}?q=${query}`
    );
  }
}
