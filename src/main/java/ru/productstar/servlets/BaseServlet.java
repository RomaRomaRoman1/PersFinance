package ru.productstar.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.productstar.servlets.errors.InternalServerErrorException;
import ru.productstar.servlets.errors.NotFoundException;

import java.io.IOException;
public class BaseServlet extends HttpServlet {//этот подход оказался нереализуем, так как контейнер сервлетов все равно перехватывает ошибки сам
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getMethod().equals("GET")) {
                doGet(req, resp);
            } else if (req.getMethod().equals("POST")) {
                doPost(req, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                resp.getWriter().println("Error (405) — Method Not Allowed");
            }
        }
        catch(Exception e){
            handleError(req, resp, e);
        }
    }
    protected void handleError(HttpServletRequest req, HttpServletResponse resp, Exception e) throws IOException {
        if (e instanceof ServletException && ((ServletException)e).getRootCause() instanceof NotFoundException) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found!!! OMG!!!");
            resp.getWriter().println("Help me please!!!!");
        } else if (e instanceof ServletException && ((ServletException)e).getRootCause() instanceof InternalServerErrorException)
        {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "OMG! It's a vary bad situation!!!");
            resp.getWriter().println("Oh, my shit!!!");
        }
    }
}

