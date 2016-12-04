import java.util.Calendar;
import java.util.GregorianCalendar;

class Event implements Comparable<Event> {
    String name;
    Calendar begin;
    Calendar end;

    public Event(String name, GregorianCalendar begin, GregorianCalendar end) {
        this.name = name;
        this.begin = begin;
        this.end = end;
    }

    public int compareTo(Event newEvent) {
        if (newEvent.begin.before(this.begin) && newEvent.end.before(this.begin)) {
            return 1;
        }
        else if (newEvent.begin.after(this.end) && newEvent.end.after(this.end)) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
