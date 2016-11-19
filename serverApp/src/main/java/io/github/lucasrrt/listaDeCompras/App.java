package io.github.lucasrrt.listaDeCompras;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Fala viado!");
    }
}
