package com.vmware.vfabric.booksdb;/*
 * Copyright (c) 2011. Written by Pronam Chatterjee. Copying and Use prohibited unless explicit permission is granted.
 */

import com.vmware.vfabric.booksdb.BooksConfiguration;
import com.vmware.vfabric.booksdb.domain.Book;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: pronam
 * Date: 03/12/11
 * Time: 2:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class SQLFireInserterFromRabbitMQ {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BooksConfiguration.class);
        AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
        Book o = (Book) amqpTemplate.receiveAndConvert();
    }
}
