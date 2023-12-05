package ru.productstar.servlets.listeners;

import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.annotation.WebServlet;

@WebListener
public class LogAttributeChanges implements ServletContextAttributeListener {

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        event.getServletContext().log(String.format("[LogAttributeChanges] New value of %s: %s", event.getName(), event.getValue()));
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        event.getServletContext().log(String.format("[LogAttributeChanges] Old value of %s: %s", event.getName(), event.getValue()));
    }
}
