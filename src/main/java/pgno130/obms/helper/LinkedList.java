package pgno130.obms.helper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.util.Iterator;

public final class LinkedList<T> implements Iterable<T> {
//    @Getter
//    @Setter
//    @RequiredArgsConstructor
    private static final class Link<T> {
        private Link<T> next;
        private Link<T> prev;
        private final T data;

    public Link(T data) {
        this.data = data;
    }

    public Link<T> getNext() {
        return next;
    }

    public void setNext(Link<T> next) {
        this.next = next;
    }

    public Link<T> getPrev() {
        return prev;
    }

    public void setPrev(Link<T> prev) {
        this.prev = prev;
    }

    public T getData() {
        return data;
    }
}

    private Link<T> head;
    private long size;

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Link<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T data = current.getData();
                current = current.getNext();
                return data;
            }

            @Override
            public void remove() {
                System.out.println(current.getData() + " =====");
                if (current.next != null) {
                    current.next.prev = current.prev == null ? null : current.prev;
                }
                if (current.prev != null) {
                    current.prev.next = current.next == null ? null : current.next;
                }
            }
        };
    }

    public void add(T data) {
        var link = new Link<T>(data);
        size++;

        if (head == null) {
            head = link;
            return;
        }

        link.next = head;
        head.prev = link;
        head = link;
    }

    public static void main(String[] args) throws FileNotFoundException {
        LinkedList<Integer> x = new LinkedList<>();
        x.add(1);
        x.add(2);
        x.add(3);
        x.add(4);
        x.add(5);

        Iterator<Integer> iter = x.iterator();
        while (iter.hasNext()) {
            int i = iter.next();
            System.out.println(i);
            if (i == 2) {
                iter.remove();
            }
        }
        iter = x.iterator();
        while (iter.hasNext()) {
            int i = iter.next();
            System.out.println(i);
        }
    }
}
