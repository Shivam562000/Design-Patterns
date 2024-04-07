import java.util.ArrayList;
import java.util.List;

// Interface for Observer pattern
interface Observer {
    void update(Notification notification);
}

// Interface for Observable pattern
interface Observable {
    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
    void notifySubscribers(Notification notification);
}

// Notification class
class Notification {
    private String content;

    public Notification(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

// Channel class implementing Observable
class Channel implements Observable {
    private String channelId;
    private String channelName;
    private List<Observer> subscribers;

    public Channel(String channelId, String channelName) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.subscribers = new ArrayList<>();
    }

    @Override
    public void subscribe(Observer observer) {
        subscribers.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        subscribers.remove(observer);
    }

    @Override
    public void notifySubscribers(Notification notification) {
        for (Observer subscriber : subscribers) {
            subscriber.update(notification);
        }
    }

    public void uploadNewContent(String content) {
        Notification notification = new Notification(content);
        notifySubscribers(notification);
    }
}

// Subscriber class implementing Observer
class Subscriber implements Observer {
    private String subscriberId;
    private String subscriberName;

    public Subscriber(String subscriberId, String subscriberName) {
        this.subscriberId = subscriberId;
        this.subscriberName = subscriberName;
    }

    @Override
    public void update(Notification notification) {
        System.out.println("Notification received by " + subscriberName + ": " + notification.getContent());
    }
}

public class YouTubeNotificationSystem {
    public static void main(String[] args) {
        // Create channels
        Channel channel1 = new Channel("1", "Channel 1");
        Channel channel2 = new Channel("2", "Channel 2");

        // Create subscribers
        Subscriber subscriber1 = new Subscriber("101", "Subscriber 1");
        Subscriber subscriber2 = new Subscriber("102", "Subscriber 2");

        // Subscribers subscribing to channels
        channel1.subscribe(subscriber1);
        channel2.subscribe(subscriber1);
        channel2.subscribe(subscriber2);

        // Channels uploading new content
        channel1.uploadNewContent("New video on Channel 1");
        channel2.uploadNewContent("New video on Channel 2");
    }
}
