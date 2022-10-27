module br.edu.ifsp.blackbearbarbearia {
    requires javafx.controls;
    requires javafx.fxml;


    opens br.edu.ifsp.blackbearbarbearia to javafx.fxml;
    exports br.edu.ifsp.blackbearbarbearia;
}