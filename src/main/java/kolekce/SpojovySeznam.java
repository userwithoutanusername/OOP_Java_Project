package kolekce;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class SpojovySeznam<E> implements Seznam<E> {
    private Node head;
    private Node current;
    private Node last;

    private int count;

    private class Node {
        private Node previous;
        private final E data;
        private Node next;

        public Node(E data) {
            this.data = data;
            this.next = null;
            this.previous = null;
        }
    }

    @Override
    public void nastavPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        current = head;
    }

    @Override
    public void nastavPosledni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        current = last;
    }

    @Override
    public void dalsi() throws KolekceException {
        if (!jeDalsi()) {
            throw new KolekceException();
        }
        current = current.next;
    }

    public void nastavPredchozi() throws KolekceException {
        if (!jePredchozi()) {
            throw new KolekceException();
        }
        current = current.previous;
    }


    @Override
    public boolean jeDalsi() {
        return current != null && current.next != null;
    }

    private boolean jePredchozi() {
        return current != null && current.previous != null;
    }


    public void vlozPrvni(E data) {
        Objects.requireNonNull(data);
        Node novy = new Node(data);
        if (jePrazdny()) {
            head = novy;
            last = novy;
        } else {
            novy.next = head;
            head.previous = novy;
            head = novy;
        }
        count++;
    }

    @Override
    public void vlozPosledni(E data) {
        Objects.requireNonNull(data);
        Node novy = new Node(data);
        if (jePrazdny()) {
            head = novy;
        } else {
            novy.previous = last;
            last.next = novy;
        }
        last = novy;
        count++;
    }


    private boolean aktualniTest() {
        return current == null;
    }

    @Override
    public void vlozZaAktualni(E data) throws KolekceException {
        Objects.requireNonNull(data);
        if (aktualniTest() || jePrazdny()) {
            throw new KolekceException();
        }
        Node novy = new Node(data);
        if (current == last) {
            vlozPosledni(data);
        } else {
            novy.next = current.next;
            current.next.previous = novy;
            current.next = novy;
            novy.previous = current;
            count++;
        }
    }

    @Override
    public boolean jePrazdny() {
        return count == 0;
    }

    @Override
    public E dejPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        return head.data;
    }

    @Override
    public E dejPosledni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        return last.data;
    }

    @Override
    public E dejAktualni() throws KolekceException {
        if (aktualniTest() || jePrazdny()) {
            throw new KolekceException();
        }
        return current.data;
    }


    @Override
    public E dejZaAktualnim() throws KolekceException {
        if (aktualniTest() || jePrazdny()) {
            throw new KolekceException();
        }
        if (current == last) {
            throw new KolekceException();
        }
        return current.next.data;
    }


    @Override
    public E odeberPrvni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        E data = head.data;
        if (current == head) {
            current = null;
        }
        if (count == 1) {
            head = null;
            last = null;
        } else {
            head = head.next;
            head.previous = null;
        }
        count--;
        return data;
    }


    @Override
    public E odeberPosledni() throws KolekceException {
        if (jePrazdny()) {
            throw new KolekceException();
        }
        E data = last.data;
        if (current == last) {
            current = null;
        }
        if (count == 1) {
            head = null;
            last = null;
        } else {
            last = last.previous;
            last.next = null;
        }
        count--;
        return data;
    }

    @Override
    public E odeberAktualni() throws KolekceException {
        if (aktualniTest() || jePrazdny()) {
            throw new KolekceException();
        }
        if (current == head) {
            return odeberPrvni();
        } else if (current == last) {
            return odeberPosledni();
        }

        E data = current.data;
        current.previous.next = current.next;
        current.next.previous = current.previous;
        count--;
        current = null;

        return data;
    }

    @Override
    public E odeberZaAktualnim() throws KolekceException {
        if (aktualniTest() || jePrazdny()) {
            throw new KolekceException();
        }
        if (current == last) {
            throw new KolekceException();
        }
        E data = current.next.data;
        if (current.next == last) {
            odeberPosledni();
        } else {
            current.next = current.next.next;
            current.next.previous = current;
            count--;
        }
        return data;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void zrus() {
        head = null;
        last = null;
        current = null;
        count = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Node aktualni = head;
            private Node iteratorPredchozi = null;

            @Override
            public boolean hasNext() {
                return aktualni != null;
            }

            @Override
            public E next() {
                if(!hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    iteratorPredchozi = aktualni;
                    E data = aktualni.data;
                    aktualni = aktualni.next;
                    return data;
                }

            }

            @Override
            public void remove() throws RuntimeException {

                E temp = iteratorPredchozi.data;

                if (iteratorPredchozi == head) {
                    try {
                        odeberPrvni();
                    } catch (KolekceException e) {
                        throw new RuntimeException(e);
                    }
                } else if (iteratorPredchozi == last) {
                    try {
                        odeberPosledni();
                    } catch (KolekceException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Node prepredchozi = head;
                    while (prepredchozi.next != iteratorPredchozi) {
                        prepredchozi = prepredchozi.next;
                    }
                    prepredchozi.next = iteratorPredchozi.next;
                }
            }

        };
    }
}