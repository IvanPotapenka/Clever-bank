package by.potapenko.controller.servlets;

import by.potapenko.database.entity.UserEntity;
import by.potapenko.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

import static by.potapenko.controller.servlets.util.PagesUtil.USER_UPDATE;

@WebServlet("/users/user/update")
public class UserUpdate extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        req.setAttribute("user", userService.findById(Long.parseLong(id)).get());
        req.getRequestDispatcher(USER_UPDATE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity updateUser = UserEntity.builder()
                .fullName(req.getParameter("full_name"))
                .id(Long.parseLong(req.getParameter("id")))
                .build();
        userService.update(updateUser).ifPresentOrElse(
                user -> successUpdateUser(req, resp, user),
                () -> faultUpdateCar(req, resp));
    }

    @SneakyThrows
    private static void successUpdateUser(HttpServletRequest req, HttpServletResponse resp, UserEntity user) {
        req.setAttribute("update_user_error", false);
        req.setAttribute("user", user);
        req.getRequestDispatcher(USER_UPDATE).forward(req, resp);
    }

    @SneakyThrows
    private static void faultUpdateCar(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("update_user_error", true);
        req.getRequestDispatcher(USER_UPDATE).forward(req, resp);
    }
}
