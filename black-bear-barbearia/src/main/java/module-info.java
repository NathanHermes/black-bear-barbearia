module br.ifsp.edu.blackbearbarbearia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens br.ifsp.edu.blackbearbarbearia.application.view to javafx.fxml;
    opens br.ifsp.edu.blackbearbarbearia.application.controller to javafx.fxml;
    opens br.ifsp.edu.blackbearbarbearia.application.main to javafx.fxml;

    exports br.ifsp.edu.blackbearbarbearia.application.view;
    exports br.ifsp.edu.blackbearbarbearia.application.controller;
    exports br.ifsp.edu.blackbearbarbearia.application.main;
}