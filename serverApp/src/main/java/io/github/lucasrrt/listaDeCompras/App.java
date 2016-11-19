package io.github.lucasrrt.listaDeCompras;
import static spark.Spark.*;
import java.sql.*;


public class App {
	public static void main(String[] args) {
		get("/connect", (req, res) -> {
			return query(req.queryParams("sql"));
		});
	}

	private static String query(String sql){
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/lista_de_compras","postgres","postgres");

			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			//TODO fazer as requisições no banco de dados
			statement.close();
			connection.close();

		} catch (Exception e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return "ERRO";


		}
		return "TODO";
	}
}
