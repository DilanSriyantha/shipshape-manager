package org.devdynamos.interfaces;

@FunctionalInterface
public interface FindIteratorCallback<T> {
    T execute(T lastElement, T element);
}
