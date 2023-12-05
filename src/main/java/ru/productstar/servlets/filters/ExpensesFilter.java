package ru.productstar.servlets.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
@WebFilter(filterName = "ExpensesFilter", urlPatterns = {"/expenses"})
public class ExpensesFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var context = request.getServletContext();

        context.log("[ExpensesFilter] Validate: " + request.getParameterMap());

        int freeMoney = (int) context.getAttribute("freeMoney");
        for (var p : request.getParameterMap().keySet()) {
            freeMoney += Integer.parseInt(request.getParameter(p));
            if (freeMoney < 0) {
                response.getWriter().println("Not enough money and you're bum");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
