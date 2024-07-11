package com.dev2prod.demo.mappers;

public interface Mapper<A,B> {

    A mapFromDto(B b);
    B mapToDto( A a);
}
