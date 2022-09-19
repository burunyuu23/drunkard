package ru.vsu.edu.shlyikov_d_g;

import java.util.Queue;

public class SimpleLinkedListQueue<T> extends SimpleLinkedList<T> implements SimpleQueue<T> {
    @Override
    public void add(T value) {
        this.addFirst(value);
    }

    public void add(SimpleLinkedListQueue<T> queue) {
        while (!queue.empty()) {
            try {
                this.addFirst(queue.element());
                queue.removeLast();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public T remove() throws Exception {
        T result = this.element();
        this.removeLast();
        return result;
    }

    @Override
    public T element() throws Exception{
        if (this.empty()) {
            return null;
        }
        return this.getLast();
    }

    public String toString(){
        try {
            String str = element().toString();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String watchQueue(SimpleLinkedListQueue<Card> queue){
        SimpleLinkedListQueue<Card> copyQueue = new SimpleLinkedListQueue<>();
        StringBuilder sb = new StringBuilder();
        while (!queue.empty()){
            try {
                sb.append(queue.element());
                copyQueue.add(queue.element());
                queue.removeLast();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!queue.empty()){
                sb.append(".");
            }
        }
        sb.append(".");
        while (!copyQueue.empty()){
            try {
                queue.add(copyQueue.element());
                copyQueue.removeLast();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String watchQueue(Queue<Card> queue){
        SimpleLinkedListQueue<Card> copyQueue = new SimpleLinkedListQueue<>();
        StringBuilder sb = new StringBuilder();
        while (!(queue.size() == 0)){
            try {
                sb.append(queue.element());
                copyQueue.add(queue.element());
                queue.remove();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!(queue.size() == 0)){
                sb.append(".");
            }
        }
        sb.append(".");
        while (!copyQueue.empty()){
            try {
                queue.add(copyQueue.element());
                copyQueue.removeLast();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Override
    public int count() {
        return this.size();
    }

    @Override
    public boolean empty() {
        return this.count() == 0;
    }
}
