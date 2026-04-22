import {Component} from '@angular/core';
import { Book } from '../../model/book';
import { ActivatedRoute, RouterLink } from '@angular/router';
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {debounceTime, switchMap} from "rxjs";
import {BooksService} from "../../services/books.service";

@Component({
    selector: 'bs-book-list',
    templateUrl: './book-list.component.html',
    styleUrls: ['./book-list.component.scss'],
    standalone: true,
  imports: [RouterLink, ReactiveFormsModule]
})
export class BookListComponent {

  searchControl: FormControl = new FormControl('');
  books: Book[];

  constructor(private readonly activatedRoute: ActivatedRoute, private readonly booksService: BooksService) {
    this.books = this.activatedRoute.snapshot.data['books'];
  }


  ngOnInit() {

    this.searchControl.valueChanges.pipe(

      debounceTime(200),

      switchMap(value => {
        if (!value || value.length < 2) {
          return this.booksService.getAllBooks();
        }
        console.log(value);
        return this.booksService.searchBooks(value);
      })

    ).subscribe(results => {
      this.books = results;
    });

  }
}
