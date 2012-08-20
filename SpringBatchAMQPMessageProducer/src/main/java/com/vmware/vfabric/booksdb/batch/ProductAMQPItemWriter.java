package com.vmware.vfabric.booksdb.batch;

import com.vmware.vfabric.booksdb.domain.Book;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA.
 * User: pronam
 * Date: 29/11/11
 * Time: 6:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductAMQPItemWriter implements ItemWriter<Book> {
    @Autowired
    private volatile RabbitTemplate rabbitTemplate;

    @Autowired
    private volatile AmqpAdmin amqpAdmin;

    private final AtomicInteger counter = new AtomicInteger();


    public void sendMessage() {

    }

    public void write(List<? extends Book> books) throws Exception {
        Queue booksQueue = new Queue("books.queue");
        amqpAdmin.declareQueue(booksQueue);
        rabbitTemplate.convertAndSend(books.get(0));
    }


}
