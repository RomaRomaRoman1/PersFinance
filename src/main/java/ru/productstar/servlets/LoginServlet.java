package ru.productstar.servlets;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebServlet(value = "/login", asyncSupported = true)
public class LoginServlet extends HttpServlet{
    private final String passwordCred = "123456";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");

        if (passwordCred.equals(password)) {
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(1800);
            resp.sendRedirect("/summary");
        } else {
            Thread messageThread = new Thread(()->{
                try {
                    resp.getWriter().println("Wrong password");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            AsyncContext asyncContext = req.startAsync();
            asyncContext.setTimeout(5000);
            messageThread.start();
            asyncContext.start(() ->{

                try {
                    messageThread.join();
                    HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
                    response.sendRedirect("/");
                    asyncContext.complete();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
