package by.potapenko.controller.servlets;

import by.potapenko.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.potapenko.controller.servlets.util.PagesUtil.USERS;

@WebServlet("/users/user/delete")
public class UserDelete extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            req.getRequestDispatcher(USERS).forward(req, resp);
        } else {
            userService.deleteById(Long.parseLong(id));
            req.setAttribute("user_delete_success", true);
            req.getRequestDispatcher(USERS).forward(req, resp);
        }
    }
}
