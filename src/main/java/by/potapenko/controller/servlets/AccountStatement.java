package by.potapenko.controller.servlets;

import by.potapenko.database.entity.AccountEntity;
import by.potapenko.service.AccountService;
import by.potapenko.service.OutPutService;
import by.potapenko.service.TransactionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static by.potapenko.controller.servlets.util.PagesUtil.ACCOUNT_STATEMENT;

@WebServlet("/my-accounts/account-statement")
public class AccountStatement extends HttpServlet {
    private final AccountService accountService = AccountService.getInstance();
    private final TransactionService transactionService = TransactionService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            AccountEntity account = accountService.findById(Long.parseLong(id)).get();
            String dateFrom = req.getParameter("date_from");
            String dateTo = req.getParameter("date_to");
            req.setAttribute("account_id", true);
            req.setAttribute("account", account);
            req.setAttribute("date_from", dateFrom);
            req.setAttribute("date_to", dateTo);
            account.setTransactions(transactionService.findAllByAccountAndDate(
                    account.getId(), dateFrom, dateTo));
            req.setAttribute("date_time", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm")));
            req.setAttribute("transactions", account.getTransactions());
            req.getRequestDispatcher(ACCOUNT_STATEMENT).forward(req, resp);
            OutPutService.writeStatementPdfFile(account, dateFrom, dateTo);
        } else {
            req.setAttribute("account_id", false);
            req.getRequestDispatcher(ACCOUNT_STATEMENT).forward(req, resp);
        }
    }
}
