package com.ceratti.rinha_backend.base.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PersonCache {
    public String key;
    public String value;
}
