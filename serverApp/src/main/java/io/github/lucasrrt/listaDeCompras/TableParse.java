package io.github.lucasrrt.listaDeCompras;

import java.sql.*;
import java.util.*;

public class TableParse {
	static public String table2string(ResultSet resultSet){
		try{
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			String allRows = "";
		    while(resultSet.next()){
		        for(int i = 1;i<=columnsNumber;i++){
			        allRows += resultSet.getString(i);
			        allRows += " ";
		        }
		        allRows += ",";
		    }
			
			return allRows;
		
		}catch(Exception e){
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return null;
		}
	}
}
