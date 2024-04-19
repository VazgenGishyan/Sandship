package ObserverPattern.Listeners;

import ObserverPattern.EventListener;
import ObserverPattern.EventType;

import static Sandship.Constants.ANSI_GREEN;
import static Sandship.Constants.ANSI_RESET;

public class Player implements EventListener {

    @Override
    public void listen(EventType eventType, String message) {
        System.out.println(ANSI_GREEN + "Player notified - " + eventType + " - " + message + ANSI_RESET);
    }
}
