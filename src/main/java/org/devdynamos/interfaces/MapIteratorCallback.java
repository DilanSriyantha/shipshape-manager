package org.devdynamos.interfaces;

@FunctionalInterface
public interface MapIteratorCallback<T> {
    T execute(T element);
}
