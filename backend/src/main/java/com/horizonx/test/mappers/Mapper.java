package com.horizonx.test.mappers;

public interface Mapper<A,B> {

    A mapFromDto(B b);
    B mapToDto( A a);
}
