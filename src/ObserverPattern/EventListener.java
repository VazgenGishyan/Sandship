package ObserverPattern;

public interface EventListener {

    void listen(EventType eventType, String message);
}
