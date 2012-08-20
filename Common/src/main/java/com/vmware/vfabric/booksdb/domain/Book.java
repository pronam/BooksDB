/*
 * Copyright (c) 2011. Written by Pronam Chatterjee. Copying and Use prohibited unless explicit permission is granted.
 */

package com.vmware.vfabric.booksdb.domain;

import java.io.Serializable;
import java.util.Arrays;

/**
 * The Book object. Each row in the CSV file represents a book. Each Book has some properties.
 * Some of these properties ca be null-able others not. This is what is finally inserted into the 
 * SQLFire grid  from the amqp consumer.
 */
public class Book implements Serializable {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = 6648416741847674063L;
    
    private String id;
    private String name;
    private String[] tag;
    private String location;
    private String type;
    private long size;

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (size != book.size) return false;
        if (!id.equals(book.id)) return false;
        if (location != null ? !location.equals(book.location) : book.location != null) return false;
        if (!name.equals(book.name)) return false;
        if (!Arrays.equals(tag, book.tag)) return false;
        if (type != null ? !type.equals(book.type) : book.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (tag != null ? Arrays.hashCode(tag) : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (int) (size ^ (size >>> 32));
        return result;
    }
}
