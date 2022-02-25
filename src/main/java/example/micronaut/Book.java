package example.micronaut;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import javax.validation.constraints.NotBlank;

@Introspected
@DynamoDbBean
public class Book {
    private static final BookTable BOOK_TABLE = new BookTable();

    @NonNull
    @NotBlank
    private String name;

    @NonNull
    @NotBlank
    private String isbn;

    public Book() {

    }

    @NonNull
    @DynamoDbPartitionKey
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @NonNull
    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    public void save() {
        BOOK_TABLE.updateBook(this);
    }
}
