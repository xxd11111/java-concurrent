package 第10章_避免活跃性危险;

import net.jcip.annotations.GuardedBy;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description L1005在相互协作对象之间的锁顺序死锁，不要这么做
 * @Author xxd
 * @Date 2021/10/26
 * @Version 1.0
 */
public class L1005在相互协作对象之间的锁顺序死锁 {
    public class CooperatingDeadlock {
        // Warning: deadlock-prone!
        class Taxi {
            @GuardedBy("this") private Point location, destination;
            private final Dispatcher dispatcher;

            public Taxi(Dispatcher dispatcher) {
                this.dispatcher = dispatcher;
            }

            public synchronized Point getLocation() {
                return location;
            }

            public synchronized void setLocation(Point location) {
                this.location = location;
                if (location.equals(destination))
                    dispatcher.notifyAvailable(this);
            }

            public synchronized Point getDestination() {
                return destination;
            }

            public synchronized void setDestination(Point destination) {
                this.destination = destination;
            }
        }

        class Dispatcher {
            @GuardedBy("this") private final Set<Taxi> taxis;
            @GuardedBy("this") private final Set<Taxi> availableTaxis;

            public Dispatcher() {
                taxis = new HashSet<Taxi>();
                availableTaxis = new HashSet<Taxi>();
            }

            public synchronized void notifyAvailable(Taxi taxi) {
                availableTaxis.add(taxi);
            }

            public synchronized Image getImage() {
                Image image = new Image();
                for (Taxi t : taxis)
                    image.drawMarker(t.getLocation());
                return image;
            }
        }

        class Image {
            public void drawMarker(Point p) {
            }
        }
    }
}
