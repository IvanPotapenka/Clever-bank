package by.potapenko.controller.servlets;

import by.potapenko.database.enam.TypeAccount;
import by.potapenko.database.enam.TypeCurrency;
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

import static by.potapenko.controller.servlets.util.PagesUtil.ACCOUNT_CREATE;


@WebServlet("/account/bank-account/create")
public class AccountCreate extends HttpServlet {

    private final AccountService accountService = AccountService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ACCOUNT_CREATE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity user = (UserEntity) req.getSession().getAttribute("user");
        BankEntity bank = (BankEntity) req.getSession().getAttribute("bank");
        AccountEntity accountCreate = AccountEntity.builder()
                .account(TypeAccount.valueOf(req.getParameter("account")))
                .currency(TypeCurrency.valueOf(req.getParameter("currency")))
                .owner(user)
                .bank((BankEntity) req.getSession().getAttribute("bank"))
                .build();
        accountCreate.addUser(user);
        accountService.create(accountCreate, bank);
        req.setAttribute("create_account", false);
        req.getRequestDispatcher(ACCOUNT_CREATE).forward(req, resp);
    }
}
