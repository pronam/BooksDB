package com.vmware.vfabric.booksdb;/*
 * Copyright (c) 2011. Written by Pronam Chatterjee. Copying and Use prohibited unless explicit permission is granted.
 */

import com.vmware.vfabric.booksdb.domain.Book;

/**
 * Created by IntelliJ IDEA.
 * User: pronam
 * Date: 03/12/11
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class BooksQueueHandler {
    private BooksDao booksDao;

    public BooksQueueHandler(BooksDao booksDao) {
      this.booksDao = booksDao;
    }

    public void handleMessage(Book o) {
       
    	System.out.println("Received: " +  o.getName());
        booksDao.insertBook(o);

    	}

}
