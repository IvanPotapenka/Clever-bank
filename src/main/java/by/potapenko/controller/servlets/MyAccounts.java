package by.potapenko.controller.servlets;

import by.potapenko.database.entity.AccountEntity;
import by.potapenko.database.entity.BankEntity;
import by.potapenko.database.entity.UserEntity;
import by.potapenko.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static by.potapenko.controller.servlets.util.PagesUtil.MY_ACCOUNTS;

@WebServlet("/account/my-accounts")
public class MyAccounts extends HttpServlet {
    private final AccountService accountService = AccountService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity user = (UserEntity) req.getSession().getAttribute("user");
        BankEntity bank = (BankEntity) req.getSession().getAttribute("bank");
        List<AccountEntity> accounts = accountService.findAllByClient(user.getId(), bank.getBankName().name());
        req.setAttribute("accounts", accounts);
        if (accounts.isEmpty()) {
            req.setAttribute("my_accounts", false);
        }
        req.getRequestDispatcher(MY_ACCOUNTS).forward(req, resp);
    }
}
