package by.potapenko.controller.servlets;

import by.potapenko.database.entity.UserEntity;
import by.potapenko.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.potapenko.controller.servlets.util.PagesUtil.USER_CREATE;

@WebServlet("/users/create")
public class UserCreate extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(USER_CREATE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity userCreate = UserEntity.builder()
                .fullName(req.getParameter("full_name"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();
        userService.create(userCreate);
        resp.sendRedirect("/users");
    }
}
