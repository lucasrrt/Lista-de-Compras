package io.github.lucasrrt.listaDeCompras;
import static spark.Spark.*;
import java.sql.*;
import java.util.*;


public class App {
	public static void main(String[] args) {
		get("/:table", (request, response) -> {
			return index(request.params("table"));
		});
		post("/products", (request, response) -> {
			return query(request.queryParams("sql"));
		});
		put("/products", (request, response) -> {
			return query(request.queryParams("sql"));
		});
		delete("/products", (request, response) -> {
			return query(request.queryParams("sql"));
		});
	}

	private static ResultSet query(String sql){
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName("org.postgresql.Driver");

			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/lista_de_compras","postgres","postgres");
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			//statement.close();
			//connection.close();

			return resultSet;
		} catch (Exception e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}
	}
	private static String index(String table){
		try{
			ResultSet resultSet = query("select array_to_json(array_agg(row_to_json(t))) from( select * from "+table+")t");
			resultSet.next();
			return resultSet.getString(1);
		} catch (Exception e) {
			System.out.println("Index Failed! Check output console");
			e.printStackTrace();
			return null;
		}
	}
}
