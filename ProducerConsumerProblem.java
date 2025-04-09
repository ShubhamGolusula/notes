package multithreading;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerProblem {
    public static void main(String[] args) {
        SharedResource obj = new SharedResource();

        for (int i = 0; i < 10; i++) {
            Thread t1 = new Thread(new ProducerTask(obj));
            Thread t2 = new Thread(new ConsumerTask(obj));
            t1.start();
            t2.start();
        }

    }
}

class SharedResource{
    public List<String> messages = new ArrayList<>();

    int counter = 0;

    public synchronized void produceMessage(){
        messages.add("hello :" + counter);
        System.out.println("Added: " + counter);
    }
    public synchronized void consumeMessage() throws InterruptedException {
        while (messages.isEmpty()){
            wait();
        }
        System.out.println(messages.get(counter++));
        notifyAll();
    }
}

class ProducerTask implements Runnable{
    private SharedResource sharedResource;

    public ProducerTask(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        sharedResource.produceMessage();
    }
}

class ConsumerTask implements Runnable{
    private SharedResource sharedResource;

    public ConsumerTask(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        try {
            sharedResource.consumeMessage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
