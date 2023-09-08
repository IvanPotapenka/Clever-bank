package by.potapenko.controller.servlets;

import by.potapenko.database.enam.TypeTransaction;
import by.potapenko.database.entity.AccountEntity;
import by.potapenko.database.entity.TransactionEntity;
import by.potapenko.service.AccountService;
import by.potapenko.service.TransactionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

import static by.potapenko.controller.servlets.util.PagesUtil.DEPOSIT;

@WebServlet("/my-accounts/deposit/account")
public class Deposit extends HttpServlet {

    private final AccountService accountService = AccountService.getInstance();
    private final TransactionService transactionService = TransactionService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            req.setAttribute("account_id", true);
            req.setAttribute("account", accountService.findById(Long.parseLong(id)).get());
            req.getRequestDispatcher(DEPOSIT).forward(req, resp);
        } else {
            req.setAttribute("account_id", false);
            req.getRequestDispatcher(DEPOSIT).forward(req, resp);
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        AccountEntity account = accountService.findById(id).get();
        TransactionEntity transaction = TransactionEntity.builder()
                .type(TypeTransaction.valueOf(req.getParameter("type")))
                .accountSend(account)
                .accountRecipient(account)
                .amount(Double.parseDouble(req.getParameter("amount")))
                .build();
        accountService.deposit(transaction);
        transactionService.create(transaction).ifPresentOrElse(
                Account -> {
                    successUpdateAccount(req, resp, account);
                    account.addTransaction(transaction);
                },
                () -> faultUpdateCar(req, resp));
    }

    @SneakyThrows
    private static void successUpdateAccount(HttpServletRequest req, HttpServletResponse resp, AccountEntity account) {
        req.setAttribute("deposit_account_error", false);
        req.setAttribute("account", account);
        req.getRequestDispatcher(DEPOSIT).forward(req, resp);
    }

    @SneakyThrows
    private static void faultUpdateCar(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("deposit_account_error", true);
        req.getRequestDispatcher(DEPOSIT).forward(req, resp);
    }
}
