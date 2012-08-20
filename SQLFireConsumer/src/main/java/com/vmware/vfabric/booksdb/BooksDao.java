package com.vmware.vfabric.booksdb;/*
 * Copyright (c) 2011. Written by Pronam Chatterjee. Copying and Use prohibited unless explicit permission is granted.
 */

import com.vmware.vfabric.booksdb.domain.Book;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: pronam
 * Date: 03/12/11
 * Time: 4:59 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BooksDao {
	
    void setDataSource(DataSource dataSource);

    void insertBook(Book book);
}
