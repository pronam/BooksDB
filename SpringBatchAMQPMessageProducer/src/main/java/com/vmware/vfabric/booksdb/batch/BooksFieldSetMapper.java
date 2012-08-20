package com.vmware.vfabric.booksdb.batch;

import com.vmware.vfabric.booksdb.domain.Book;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 *FieldMapper for the Book domain class.
 */
public class BooksFieldSetMapper implements FieldSetMapper<Book>{

    public Book mapFieldSet(FieldSet fieldSet) throws BindException {
        Book book = new Book();
        book.setId(fieldSet.readString("BOOK_ID"));
        book.setName(fieldSet.readString("NAME"));
        book.setLocation(fieldSet.readString("LOCATION"));
        book.setSize(fieldSet.readLong("SIZE"));
        book.setType(fieldSet.readString("TYPE"));
        book.setTag(new String[]{fieldSet.readRawString("TAG")});
        return book;

    }

}
