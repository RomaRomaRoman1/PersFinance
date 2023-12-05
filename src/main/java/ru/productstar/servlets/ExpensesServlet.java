package ru.productstar.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.productstar.servlets.listeners.LogAttributeChanges;
import ru.productstar.servlets.model.Transaction;
import ru.productstar.servlets.model.Transaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/expenses")
public class ExpensesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        var context = req.getServletContext();
        var session = req.getSession(false);
        if (session == null) {
            req.getRequestDispatcher("/").include(req, resp);
            return;
        }
        int freeMoney = (int)context.getAttribute("freeMoney");
        var expenses = new ArrayList<Transaction>((List)context.getAttribute("expenses"));
        for(var k : req.getParameterMap().keySet()) {
            int value = Integer.parseInt(req.getParameter(k));
            freeMoney += value;
            expenses.add(new Transaction(k, value));
        }
        context.setAttribute("expenses", expenses);
        context.setAttribute("freeMoney", freeMoney);
        boolean redirect = false;
        if (req.getParameterMap().isEmpty()==false) {
            resp.getWriter().println("Expenses were added");
            redirect = true;
        }
        else {
            resp.getWriter().println("Don't add transactions");
            req.getRequestDispatcher("/summary").forward(req, resp);
        }


    }
}
