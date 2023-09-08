package by.potapenko.controller.servlets;

import by.potapenko.database.entity.BankEntity;
import by.potapenko.database.entity.UserEntity;
import by.potapenko.service.BankService;
import by.potapenko.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.potapenko.controller.servlets.util.PagesUtil.REGISTRATION;

@WebServlet("/registration")
public class Registration extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private final BankService bankService = BankService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(REGISTRATION).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (userService.findByEmail(req.getParameter("email")).isEmpty()) {
            UserEntity userCreate = UserEntity.builder()
                    .fullName(req.getParameter("full_name"))
                    .email(req.getParameter("email"))
                    .password(req.getParameter("password"))
                    .build();
            userService.create(userCreate);
            BankEntity bank = bankService.findByBank(req.getParameter("bank")).get();
            bank.addUser(userCreate);
            userService.createBankClient(bank.getId(), userCreate.getId());
            req.setAttribute("create_user", true);
            req.getRequestDispatcher(REGISTRATION).forward(req, resp);
        } else {
            req.setAttribute("email_error", true);
            req.getRequestDispatcher(REGISTRATION).forward(req, resp);
        }
    }
}
