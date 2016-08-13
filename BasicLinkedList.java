import javax.lang.model.element.Element;
import java.lang.annotation.ElementType;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
/**
 * Created by Antonio on 5/28/16.
 */
public class BasicLinkedList<E> extends Object implements BasicList<E>{
    private Node head;
    private Node tail;
    private int size;

    public BasicLinkedList(){
        head = null;
        tail = null;
        size = 0;
    }
    public void add(E e){ // Add to the end of the list
        Node newNode = new Node(e);

        if(tail == null){
            this.head = newNode;
        }
        else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
        size++;
    }


    public void add(int index, E e){
        int mid = (size/2);
        Node newNode = new Node(e);
        if (index > size()){
            throw new IndexOutOfBoundsException();
        }
        if(index < 0){
            throw new IndexOutOfBoundsException();
        }
        if(size == 0){
            head = newNode;
            tail = newNode;
        }
        else if(index == 0){
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
        else if(index == size){
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        else if(index < mid){
            Node temp = head;
            for (int i = 0; i < index ; i++){
                temp = temp.next;
            }
            newNode.next = temp.next;
            newNode.prev = temp;
            temp.next = newNode;
        }
        else {
            Node temp = tail;
            for(int i = size -1; i > index; i--){
                temp = temp.prev;
            }
            newNode.next = temp.prev;
            newNode.next = temp;
            temp.prev = newNode;
        }
        size ++;
    }
    public BasicListIterator<E> basicListIterator(){ // CGet this checked. No <E>
        return new BasicLinkedListIterator();
    }
    public Iterator<E> iterator(){

        return new BasicLinkedListIterator();
    }

    private class BasicLinkedListIterator implements BasicListIterator<E> {
        private int index = 0;
        private Node hold;
        public boolean hasNext() {
            if (index < size) {
                return true;
            }
            return false;
        }
        public E next() {
            if (hasNext() != true) {
                throw new NoSuchElementException();
            }
            if(index == 0){
                hold = head;
                index ++;
                return hold.element;
            }
            hold = hold.next;
            index++;
            return hold.element;
        }
        public void remove() { // What
            throw new UnsupportedOperationException();
        }

        public boolean hasPrevious() {

            if (index > 0) {
                return true;
            }
            return false;
        }
        public E previous() {
            if (hasPrevious() != true) {
                throw new NoSuchElementException();
            }
            if(index == size){
                index --;
                return hold.element;
            }
            index--;
            hold = hold.prev;
            return hold.element;
        }
    }
    public void clear(){
        head = null;
        tail = null;
        size = 0;
    }
    public boolean contains(Object e){
        Node temp = head;

        if(e == null){
            if(head.element == null){
                return true;
            }
            if(tail.element == null){
                return true;
            }
        }
        else{

            if(head.element.equals(e)){
                return true;
            }
            if(tail.element.equals(e)){
                return true;
            }

        }

        for(int i = 0; i < size -1; i++){
            temp = temp.next;
            if(e == null){
                if(temp.element == null){
                    return true;
                }
            }
            else {
                if (temp.element.equals(e)) {
                    return true;
                }
            }
        }
        return false;

    }
    public E get(int index) throws IndexOutOfBoundsException{
        int mid = (size/2);
        Node ret = null;
        if(index < 0){
            throw new IndexOutOfBoundsException();
        }
        if (index >= size()){
            throw new IndexOutOfBoundsException();
        }
        if(size == 0){
            throw new IndexOutOfBoundsException();
        }
        if(index == size){
            return tail.element;
        }
        else if(index == 0){
            return head.element;
        }

        else if(index < mid){
            Node p = head;
            for(int i = 0; i < index; i++){
                p = p.next;
            }
            ret = p;
        }
        else{
            Node p  = tail;
            for(int i = size-1; i > index; i--){
                p = p.prev;
            }
            ret = p;
        }
        return ret.element;
    }
    public int indexOf(Object e) throws NoSuchElementException {
        if (head == null) {
            throw new NoSuchElementException();
        }
        Node temp = head;
        int index = 0;
        if (e != null) {
            while (index < size && !temp.element.equals(e)) {
                temp = temp.next;
                index++;
            }
        }
        if(e == null){
            while (index < size && !(temp.element == null)) {
                temp = temp.next;
                index++;
            }
        }

        if (temp == null)
        {
            throw new NoSuchElementException();
        }
        else
            return index;

    }
    public E remove(int index) throws IndexOutOfBoundsException{
        Node p = head;
        int mid = (size/2);
        if (index >= size()){
            throw new IndexOutOfBoundsException();
        }
        if(size == 0){
            throw new IndexOutOfBoundsException();
        }
        if(index < 0){
            throw new IndexOutOfBoundsException();
        }
        if(size == 1){
            Node temp = head;
            head = null;
            tail = null;
            size--;
            return temp.element;
        }
        if(index == size -1){
            Node temp = tail;
            tail.prev.next = null;
            tail = tail.prev;
            size --;
            return temp.element;
        }
        else if(index == 0){
            Node oldHead = head;
            head.next.prev = null;
            head = head.next;
            size --;
            return oldHead.element;
        }
        else if(index < mid){
            p = head;
            for(int i = 0; i < index; i++){
                p = p.next;
            }
            p.prev.next = p.next;
            p.next.prev = p.prev;
            p.prev = null;
            p.next = null;
        }
        else{
            p  = tail;
            for(int i = size-1; i > index; i--){
                p = p.prev;
            }
            p.next.prev = p.prev;
            p.prev.next = p.next;
            p.prev = null;
            p.next = null;

        }

        size--;

        return p.element;


    }
    public E set(int index, E e){
        Node newNode = new Node(e);
        Node p;
        int mid = size/2;
        if (index >= size()){
            throw new IndexOutOfBoundsException();
        }
        if(index < 0){
            throw new IndexOutOfBoundsException();
        }
        if(size == 0){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){
            Node old = new Node(head.element);
            head.element = newNode.element;
            return old.element;
        }
        if(index == size -1){
            Node old = new Node(tail.element);
            tail.element = newNode.element;
            return old.element;
        }
        else if(index < mid){
            p = head;
            for(int i = 0; i < index; i++){
                p = p.next;
            }
            newNode.next = p.next;
            newNode.prev = p.prev;
            newNode.prev.next = newNode;
            p.next = null;
            p.prev = null;
        }
        else {
            p = tail;
            for(int i = size-1; i > index; i--){
                p = p.prev;
            }
            newNode.next = p.next;
            newNode.prev = p.prev;
            newNode.next.prev = newNode;
            p.next = null;
            p.prev = null;
        }

        return p.element;
    }
    public int size(){
        return size;
    }
    private class Node {
        E element;
        Node next;
        Node prev;

        public Node(E element) {
            this.element = element;
            this.next = null;
            this.prev = null;
        }
    }
}
