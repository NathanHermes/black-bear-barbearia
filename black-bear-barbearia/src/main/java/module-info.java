module br.ifsp.edu.blackbearbarbearia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.github.librepdf.openpdf;

    opens br.ifsp.edu.blackbearbarbearia.application.view to javafx.fxml;
    opens br.ifsp.edu.blackbearbarbearia.application.controller to javafx.fxml;
    opens br.ifsp.edu.blackbearbarbearia.application.view.css to javafx.fxml;
    opens br.ifsp.edu.blackbearbarbearia.application.main to javafx.fxml;

    exports br.ifsp.edu.blackbearbarbearia.application.view;
    exports br.ifsp.edu.blackbearbarbearia.application.controller;
    exports br.ifsp.edu.blackbearbarbearia.application.main;

    exports br.ifsp.edu.blackbearbarbearia.domain.usecases.user;
    exports br.ifsp.edu.blackbearbarbearia.domain.usecases.employee;
    exports br.ifsp.edu.blackbearbarbearia.domain.usecases.client;
    exports br.ifsp.edu.blackbearbarbearia.domain.usecases.service;
    exports br.ifsp.edu.blackbearbarbearia.domain.usecases.booking;
    exports br.ifsp.edu.blackbearbarbearia.domain.usecases.utils;

    exports br.ifsp.edu.blackbearbarbearia.domain.entities.user;
    exports br.ifsp.edu.blackbearbarbearia.domain.entities.client;
    exports br.ifsp.edu.blackbearbarbearia.domain.entities.service;
    exports br.ifsp.edu.blackbearbarbearia.domain.entities.booking;
    exports br.ifsp.edu.blackbearbarbearia.application.repository;

}