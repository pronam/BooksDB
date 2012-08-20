package com.vmware.vfabric.booksdb;/*
 * Copyright (c) 2011. Written by Pronam Chatterjee. Copying and Use prohibited unless explicit permission is granted.
 */

import com.vmware.vfabric.booksdb.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: pronam
 * Date: 03/12/11
 * Time: 4:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class BooksDaoImpl implements BooksDao {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    String query = "INSERT INTO Books (ID,NAME, TAG, SIZE, LOCATION) VALUES (?,?,?,?,?)";

    public void insertBook(Book book) {
        jdbcTemplate.update(query, Integer.valueOf(book.getId()),
                book.getName(), book.getTag().toString(), book.getSize(), book.getLocation());
    }


}
