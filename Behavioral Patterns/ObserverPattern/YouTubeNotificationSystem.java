import java.util.ArrayList;
import java.util.List;

// Interface for notification handling
interface NotificationHandler {
    void handleNotification(Notification notification);
}

// Interface for logging
interface Logger {
    void log(String message);
}

// Logger implementation
class ConsoleLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}

// Channel class handling channel-related operations
class Channel implements NotificationHandler {
    private String channelId;
    private String channelName;
    private List<Subscriber> subscribers;
    private Logger logger;

    public Channel(String channelId, String channelName, Logger logger) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.subscribers = new ArrayList<>();
        this.logger = logger;
    }

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
        subscriber.update(new Notification("Subscribed to channel: " + channelName));
        logger.log("Subscriber " + subscriber.getSubscriberName() + " subscribed to channel: " + channelName);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
        logger.log("Subscriber " + subscriber.getSubscriberName() + " unsubscribed from channel: " + channelName);
    }

    public void notifySubscribers(Notification notification) {
        for (Subscriber subscriber : subscribers) {
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

    @Override
    public void handleNotification(Notification notification) {
        notifySubscribers(notification);
    }
}

// Subscriber class handling subscriber-related operations
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
        //System.out.println("Channels subscribed by " + subscriberName + " (ID: " + subscriberId + "):");
        for (Channel channel : subscribedChannels) {
            System.out.println("- " + channel.getChannelName());
        }
    }

    @Override
    public void update(Notification notification) {
        //System.out.println("Notification received by " + subscriberName + ": " + notification.getContent());
        logger.log("Notification received by " + subscriberName + ": " + notification.getContent());
    }

    public String getSubscriberName() {
        return subscriberName;
    }
}

// YouTubeNotificationSystem class
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
        channel1.uploadNewContent("New video on Channel 1");
        channel2.uploadNewContent("New video on Channel 2");
    }
}
