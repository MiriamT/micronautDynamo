package example.micronaut;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;

public class BookTable {
    private static final Region AWS_REGION = Region.of(System.getenv("AWS_REGION"));
    private static final String TABLE_NAME = System.getenv("DYNAMO_DB_TABLE_NAME"); // set in AWS as a lambda env var
    private static final DynamoDbClient DDB_CLIENT = DynamoDbClient.builder()
            .region(AWS_REGION)
            .build();
    private static final DynamoDbEnhancedClient DDB_ENHANCED_CLIENT = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(DDB_CLIENT)
            .build();
    private static final DynamoDbTable<Book> BOOK_TABLE = DDB_ENHANCED_CLIENT.table(TABLE_NAME, TableSchema.fromBean(Book.class));

    public static Book getBook(Book book) {
        return BOOK_TABLE.getItem(book);
    }

    public static Book getBook(String name) {
        return BOOK_TABLE.getItem(createDynamoKey(name));
    }

    public static Book updateBook(Book book) {
        return BOOK_TABLE.updateItem(book);
    }

    public static Book deleteBook(String name) {
        return BOOK_TABLE.deleteItem(createDynamoKey(name));
    }

    public static ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        BOOK_TABLE.scan().items().forEach(books::add);
        return books;
    }

    private static Key createDynamoKey(String partitionKeyValue) {
        return Key.builder().partitionValue(partitionKeyValue).build();
    }
}
