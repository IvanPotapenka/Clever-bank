package by.potapenko.service;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import lombok.SneakyThrows;

public class ListenerEvent implements ServletContextListener {
    @SneakyThrows
    public void contextInitialized(ServletContextEvent event) {
        Thread thread = new Thread(() -> {
            java.util.Timer timer = new java.util.Timer(true);
            timer.schedule(new InterestCalculation(event.getServletContext()), 0, 30000);
        });
        thread.start();
    }
}
