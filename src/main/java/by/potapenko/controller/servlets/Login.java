package by.potapenko.controller.servlets;

import by.potapenko.database.entity.UserEntity;
import by.potapenko.service.BankService;
import by.potapenko.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

import static by.potapenko.controller.servlets.util.PagesUtil.LOGIN;

@WebServlet("/login")
public class Login extends HttpServlet {

    private final UserService userService = UserService.getInstance();
    private final BankService bankService = BankService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(LOGIN).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bankName = req.getParameter("bank");
        req.getSession().setAttribute("bank", bankService.findByBank(bankName).get());
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        userService.findByEmailAndPassword(email, password, bankName).ifPresentOrElse(
                user -> successSingIn(req, resp, user),
                () -> faultSingIn(req, resp));
    }

    @SneakyThrows
    private static void successSingIn(HttpServletRequest req, HttpServletResponse resp, UserEntity user) {
        req.getSession().setAttribute("user", user);
        resp.sendRedirect("/account");
    }

    @SneakyThrows
    private static void faultSingIn(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("find_user_error", true);
        req.getRequestDispatcher(LOGIN).forward(req, resp);
    }
}
