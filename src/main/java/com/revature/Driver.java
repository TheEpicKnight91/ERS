package com.revature;

import com.revature.controllers.ReimbursementController;
import com.revature.controllers.SessionController;
import com.revature.controllers.UserController;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Driver {

    public static void main(String[] args)
    {
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/", Location.CLASSPATH);
        }).start(9000);

        UserController userController = new UserController();
        ReimbursementController reimbursementController = new ReimbursementController();
        SessionController sessionController = new SessionController();

        app.routes(() ->
        {
            path("reimbursement", () ->{
                get(reimbursementController::getAllReimbursements);
                path("create", () ->{
                    post(reimbursementController::createReimbursement);
                });
                path("{reimId}", () -> {
                    get(reimbursementController::getReimbursmentById);
                    path("{status}", () ->
                    {
                        path("{userId}", () -> {
                            patch(reimbursementController::updateReimbursement);
                        });
                    });
                });
            });
            path("session", () ->{
                post(sessionController::login);
                get(sessionController::checkSession);
                delete(sessionController::logout);
            });
            post("/register",userController::registerUser);
        });
    }
}
