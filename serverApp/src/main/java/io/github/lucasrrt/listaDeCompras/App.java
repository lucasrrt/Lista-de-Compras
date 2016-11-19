package io.github.lucasrrt.listaDeCompras;
import static spark.Spark.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


public class App {
	public static void main(String[] args) {
		get("/connect", (req, res) -> {
			doStuff();
			return "deu certo!";
		});
	}

	private static void doStuff(){
		System.out.println("-------- PostgreSQL "
				+ "JDBC Connection Testing ------------");

		try {

			Class.forName("org.postgresql.Driver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;

		}

		System.out.println("PostgreSQL JDBC Driver Registered!");

		Connection connection = null;

		try {

			connection = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/lista_de_compras","postgres","postgres");

		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;

		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}
}
