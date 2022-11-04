module br.ifsp.edu.blackbearbarbearia {
    requires javafx.controls;
    requires javafx.fxml;


    opens br.ifsp.edu.blackbearbarbearia to javafx.fxml;
    exports br.ifsp.edu.blackbearbarbearia;
}