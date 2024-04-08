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

// Logger interface
interface Logger {
    void log(String message);
}

// Console logger implementation
class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}

// Channel class implementing Observable
class Channel implements Observable {
    private String channelId;
    private String channelName;
    private List<Observer> subscribers;
    private Logger logger;

    public Channel(String channelId, String channelName, Logger logger) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.subscribers = new ArrayList<>();
        this.logger = logger;
    }

    @Override
    public void subscribe(Observer observer) {
        subscribers.add(observer);
        observer.update(new Notification("Subscribed to channel: " + channelName));
        logger.log("Subscriber " + ((Subscriber)observer).getSubscriberName() + " subscribed to channel: " + channelName);
    }

    @Override
    public void unsubscribe(Observer observer) {
        subscribers.remove(observer);
        logger.log("Subscriber " + ((Subscriber)observer).getSubscriberName() + " unsubscribed from channel: " + channelName);
    }

    @Override
    public void notifySubscribers(Notification notification) {
        for (Observer subscriber : subscribers) {
            subscriber.update(notification);
        }
        logger.log("Notification sent to subscribers of channel: " + channelName);
    }

    public void uploadNewContent(String content) {
        Notification notification = new Notification("New video on " + channelName + ": " + content);
        notifySubscribers(notification);
    }

    public String getChannelName() {
        return channelName;
    }

    public void handleNotification(Notification notification) {
        notifySubscribers(notification);
    }
}

// Subscriber class implementing Observer
class Subscriber implements Observer {
    private String subscriberId;
    private String subscriberName;
    private List<Channel> subscribedChannels;
    private Logger logger;

    public Subscriber(String subscriberId, String subscriberName, Logger logger) {
        this.subscriberId = subscriberId;
        this.subscriberName = subscriberName;
        this.subscribedChannels = new ArrayList<>();
        this.logger = logger;
    }

    public void subscribeToChannel(Channel channel) {
        if (!subscribedChannels.contains(channel)) {
            subscribedChannels.add(channel);
            channel.subscribe(this);
        }
    }

    public void unsubscribeFromChannel(Channel channel) {
        if (subscribedChannels.contains(channel)) {
            subscribedChannels.remove(channel);
            channel.unsubscribe(this);
        }
    }

    public void displaySubscribedChannels() {
        System.out.println("Channels subscribed by " + subscriberName + " (ID: " + subscriberId + "):");
        for (Channel channel : subscribedChannels) {
            System.out.println("- " + channel.getChannelName());
        }
    }

    @Override
    public void update(Notification notification) {
        logger.log("Notification received by " + subscriberName + ": " + notification.getContent());
    }

    public String getSubscriberName() {
        return subscriberName;
    }
}

// Main class
public class YouTubeNotificationSystem {
    public static void main(String[] args) {
        // Create logger
        Logger logger = new ConsoleLogger();

        // Create channels
        Channel channel1 = new Channel("1", "Channel 1", logger);
        Channel channel2 = new Channel("2", "Channel 2", logger);

        // Create subscribers
        Subscriber subscriber1 = new Subscriber("101", "Subscriber 1", logger);
        Subscriber subscriber2 = new Subscriber("102", "Subscriber 2", logger);

        // Subscribers subscribing to channels
        subscriber1.subscribeToChannel(channel1);
        subscriber1.subscribeToChannel(channel2);
        subscriber2.subscribeToChannel(channel2);

        // Display subscribed channels
        subscriber1.displaySubscribedChannels();
        subscriber2.displaySubscribedChannels();

        // Channels uploading new content
        channel1.uploadNewContent("New video on Channel 1 - YT Video");
        channel2.uploadNewContent("New video on Channel 2 - Hotstar new movie");
    }
}
