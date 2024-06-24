package org.devdynamos.interfaces;

@FunctionalInterface
public interface FilterIteratorCallback <T> {
    T execute(T element);
}
