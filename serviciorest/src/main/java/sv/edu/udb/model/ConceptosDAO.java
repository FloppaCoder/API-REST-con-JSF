package sv.edu.udb.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConceptosDAO extends AppConnection{
	
	public void insert(Concepto concepto) throws SQLException{
		connect();
		pstmt = conn.prepareStatement("insert into concepto (nombre,valor,categoria_id) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, concepto.getName());
		pstmt.setDouble(2, concepto.getValue());
		pstmt.setInt(3, concepto.getCategoryId());
		pstmt.executeUpdate();
		
		//obteniendo el ultimo id generado
		ResultSet keys= pstmt.getGeneratedKeys();
		keys.next();
		int id = keys.getInt(1);
		
		concepto.setId(id);
		close();
	}
	
	public void update(Concepto concepto) throws SQLException{
		connect();
		pstmt = conn.prepareStatement("update concepto set nombre = ? , valor = ? , categoria_id = ? where id = ?");
		pstmt.setString(1, concepto.getName());
		pstmt.setDouble(2, concepto.getValue());
		pstmt.setInt(3, concepto.getCategoryId());
		pstmt.setInt(4, concepto.getId());
		pstmt.executeUpdate();
		close();
	}
	
	public void delete(int id) throws SQLException{
		connect();
		pstmt = conn.prepareStatement("delete from concepto where id = ?");
		pstmt.setInt(1, id);
		pstmt.execute();
		close();
	}
	
	public ArrayList<Concepto> findAll() throws SQLException{
		connect();
		stmt = conn.createStatement();
		resultSet = stmt.executeQuery("select id, nombre, valor, categoria_id from concepto");
		ArrayList<Concepto> conceptos = new ArrayList();
		
		while(resultSet.next()){
			Concepto tmp = new Concepto();
			tmp.setId(resultSet.getInt(1));
			tmp.setName(resultSet.getString(2));
			tmp.setValue(resultSet.getDouble(3));
			tmp.setCategoryId(resultSet.getInt(4));
			conceptos.add(tmp);
		}
		close();
		
		return conceptos;
	}
		
		public Concepto findById(int id) throws SQLException{
			Concepto concepto = null;
			connect();
			pstmt = conn.prepareStatement("select id, nombre, valor, categoria_Id from concepto where id = ?");
			pstmt.setInt(1, id);
			resultSet = pstmt.executeQuery();
			while(resultSet.next()){
			concepto = new Concepto();
			concepto.setId(resultSet.getInt(1));
			concepto.setName(resultSet.getString(2));
			concepto.setValue(resultSet.getDouble(3));
			concepto.setCategoryId(resultSet.getInt(4));
			}
			close();
			return concepto;
			}
		
	}
