package ru.productstar.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.productstar.servlets.model.Transaction;
import ru.productstar.servlets.model.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@WebServlet("/summary")
public class SummaryServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        var context = config.getServletContext();
        context.setAttribute("salary", 100);
        context.setAttribute("rent", -30);
        int salary = (int) context.getAttribute("salary");
        int rent = (int) context.getAttribute("rent");

        context.setAttribute("freeMoney", salary + rent);
        context.setAttribute("expenses",  new ArrayList<Transaction>() {{ add(new Transaction("rent", rent));}});
        context.log("[SummaryServlet] Init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();

        var session = req.getSession(false);
        if (session == null) {
            req.getRequestDispatcher("/").include(req, resp);
            return;
        }
        req.getRequestDispatcher("/details").include(req, resp);
        int freeMoney = (int)context.getAttribute("freeMoney");

        context.log("[SummaryServlet] Free money: " + freeMoney);
        resp.getWriter().println("Free money: " + freeMoney);
    }
}
