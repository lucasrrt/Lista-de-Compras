package io.github.lucasrrt.listaDeCompras;
import static spark.Spark.*;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;


public class App {
	public static void main(String[] args) {
		get("/:table", (request, response) -> {
			return index(request.params("table"));
		});
		post("/:table", (request, response) -> {
			return create(request.params("table"),request.queryMap().toMap());
		});
		put("/:table", (request, response) -> {
			return query(request.queryParams("sql"));
		});
		delete("/:table/:id", (request, response) -> {
			return destroy(request.params(":table"),request.params(":id"));
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
	private static String create(String table, Map<String,String[]> params){
		try{
			List<String> col = new ArrayList();
			List<String> val = new ArrayList();
			for (Entry<String, String[]> entry :params.entrySet()){
				if(!entry.getKey().equals(":table")&&!entry.getKey().equals(":id")){
					col.add(entry.getKey());
					val.add(String.join(",", entry.getValue()));
				}
			}	
			System.out.println("insert into "+table+" ("+String.join(",",col)+") values ("+String.join(",",val)+")");
			query("insert into "+table+" ("+String.join(",",col)+") values ('"+String.join("','",val)+"')");
			return "ok!";
		} catch (Exception e) {
			System.out.println("Create Failed! Check output console");
			e.printStackTrace();
			return null;
		}
	}
	private static String update(String table){
		try{
			System.out.println("select array_to_json(array_agg(row_to_json(t))) from( select * from "+table+")t");
			ResultSet resultSet = query("select array_to_json(array_agg(row_to_json(t))) from( select * from "+table+")t");
			resultSet.next();
			return resultSet.getString(1);
		} catch (Exception e) {
			System.out.println("Update Failed! Check output console");
			e.printStackTrace();
			return null;
		}
	}
	private static String destroy(String table, String id){
		try{
			System.out.println("delete from "+table+" where id="+id);
			ResultSet resultSet = query("delete from "+table+" where id="+id);
			return "ok!";
		} catch (Exception e) {
			System.out.println("Destroy Failed! Check output console");
			e.printStackTrace();
			return null;
		}
	}
}
