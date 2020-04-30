package com.sample.rest.util;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> {
    T getId();
}