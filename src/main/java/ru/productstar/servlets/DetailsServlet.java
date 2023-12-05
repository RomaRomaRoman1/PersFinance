package ru.productstar.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.productstar.servlets.model.Transaction;

import java.io.IOException;
import java.util.List;
@WebServlet("/details")
public class DetailsServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();
        var session = req.getSession(false);
        if (session == null) {
            resp.getWriter().println("Not authorized");
            return;
        }
        resp.getWriter().println("Expenses: ");
        for ( Transaction e : (List<Transaction>)context.getAttribute("expenses")) {
            if ((e.getSum()) > 0) {
                resp.getWriter().println(String.format("+ %s(%d)", e.getName(), e.getSum()));
            }
            else {
                resp.getWriter().println(String.format("- %s(%d)", e.getName(), Math.abs(e.getSum())));
            }
        }
        resp.getWriter().println("\n");
    }
}
