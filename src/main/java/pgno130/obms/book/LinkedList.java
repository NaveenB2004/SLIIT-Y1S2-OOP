package pgno130.obms.book;

import lombok.Data;
import lombok.Getter;

import java.util.Iterator;

public final class LinkedList<T> implements Iterable<T> {
    @Data
    private static final class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T data;

        public Node(final T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    @Getter
    private int nodeCount;

    public synchronized void add(final T data) {
        Node<T> newNode = new Node<>(data);
        nodeCount++;
        if (head == null) {
            head = newNode;
            return;
        }
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
    }

    public synchronized boolean remove(final T data) {
        Iterator<T> iterator = iterator();
        nodeCount--;
        while (iterator.hasNext()) {
            if (iterator.next().equals(data)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null && current.next != null;
            }

            @Override
            public T next() {
                Node<T> temp = current;
                current = current.next;
                return temp.data;
            }

            @Override
            public void remove() {
                if (current.getPrev() != null) {
                    current.getPrev().setNext(current.getNext() == null ? null : current.getNext());
                }
                if (current.getNext() != null) {
                    current.getNext().setPrev(current.getPrev() == null ? null : current.getPrev());
                }
            }
        };
    }
}
