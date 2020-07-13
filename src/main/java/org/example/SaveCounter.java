//Реализация слушателя событий
package org.example;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.SaveOrUpdateEventListener;

import java.util.concurrent.atomic.AtomicInteger;

public class SaveCounter implements SaveOrUpdateEventListener {
    AtomicInteger counter = new AtomicInteger();
    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent e) throws HibernateException {
        System.out.println("\n\n-----------This is block Listener---------------");
        System.out.println("Saving: " + e.getResultId());
        System.out.println("Totally seen " + counter.incrementAndGet() + " save events");
        System.out.println("--------------------------" + "\n\n");
    }
}

