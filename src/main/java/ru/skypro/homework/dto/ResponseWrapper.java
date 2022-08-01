package ru.skypro.homework.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class ResponseWrapper<T> {

    private final int count;
    private final Collection<T> data;

    public ResponseWrapper(Collection<T> data) {
        this.count = data.size();
        this.data = data;
    }
}
