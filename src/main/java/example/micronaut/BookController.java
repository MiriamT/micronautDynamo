package example.micronaut;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.UUID;

@Controller
public class BookController {

    @Get("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello World using Micronaut";
    }

    @Post("/books")
    public Book createBook(@Valid @Body BookRequest bookRequest) {
        Book newBook = new Book();
        newBook.setName(bookRequest.getName());
        newBook.setIsbn(UUID.randomUUID().toString());
        newBook.save(); // saves to DynamoDb
        return newBook;
    }

    @Get("/books")
    public ArrayList<Book> getAllBooks() {
        return BookTable.getAllBooks();
    }

    @Get("/books/{name}")
    public Book getBook(String name) {
        return BookTable.getBook(name);
    }

    @Delete("/books/{name}")
    public Book deleteBook(String name) {
        return BookTable.deleteBook(name);
    }
}
