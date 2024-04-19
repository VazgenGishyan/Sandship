package ObserverPattern;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class EventManager {
    Map<EventType, HashSet<EventListener>> listeners = new HashMap<>();

    public void subscribe(EventType eventType, EventListener listener) {
        if(listeners.containsKey(eventType)) {
            listeners.get(eventType).add(listener);
        } else {
            HashSet<EventListener> set = new HashSet<>();
            set.add(listener);
            listeners.put(eventType, set);
        }
    }

    public void unsubscribe(EventType eventType, EventListener listener) {
        if(listeners.containsKey(eventType)) {
            listeners.get(eventType).remove(listener);
        }
    }

    public void notify(EventType eventType, String message) {
        if(!listeners.containsKey(eventType)) {
            return;
        }

        for (EventListener listener : listeners.get(eventType)) {
            listener.listen(eventType, message);
        }
    }
}
