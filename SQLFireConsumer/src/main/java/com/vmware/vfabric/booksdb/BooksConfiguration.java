package com.vmware.vfabric.booksdb;/*
 * 
 */


import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
public class BooksConfiguration {
    protected final String booksQueue = "books.queue";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        //The routing key is set to the name of the queue by the broker for the default exchange.
        template.setRoutingKey(this.booksQueue);
        //Where we will synchronously receive messages from
        template.setQueue(this.booksQueue);
        return template;
    }

    @Bean
    // Every queue is bound to the default direct exchange
    public Queue helloWorldQueue() {
        return new Queue(this.booksQueue);
    }

    @Bean
    public SimpleMessageListenerContainer listenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueueNames(this.booksQueue);
        container.setMessageListener(new MessageListenerAdapter(new BooksQueueHandler(booksDao())));
        return container;
    }


    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource("com.vmware.sqlfire.jdbc.ClientDriver", "jdbc:sqlfire://localhost:1527", "books", "books");
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager ds = new DataSourceTransactionManager();
        ds.setDataSource(dataSource());
        return ds;
    }

    @Bean
    public BooksDao booksDao() {
        BooksDao booksDao = new BooksDaoImpl();
        booksDao.setDataSource(dataSource());
        return booksDao;
    }

}
