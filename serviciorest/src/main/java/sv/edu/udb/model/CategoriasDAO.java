package sv.edu.udb.model;

import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriasDAO extends AppConnection{

	public void insert(Categoria categoria) throws SQLException{
		connect();
		pstmt = conn.prepareStatement("insert into categoria (nombre)values(?)");
		pstmt.setString(1, categoria.getName());
		pstmt.execute();
		close();
	}
	
	public void update(Categoria categoria) throws SQLException{
		connect();
		pstmt = conn.prepareStatement("update categoria set nombre = ? where id = ?");
		pstmt.setString(1, categoria.getName());
		pstmt.execute();
		close();
	}
	
	public void delete(int id) throws SQLException{
		connect();
		pstmt = conn.prepareStatement("delete from categoria where id = ?");
		pstmt.setInt(1, id);
		pstmt.execute();
		close();
	}
	
	public ArrayList<Categoria> findAll() throws SQLException{
		connect();
		stmt = conn.createStatement();
		resultSet = stmt.executeQuery("select id, nombre from categoria");
		ArrayList<Categoria> categorias = new ArrayList();
		
		while(resultSet.next()){
			Categoria tmp = new Categoria();
			tmp.setId(resultSet.getInt(1));
			tmp.setName(resultSet.getString(2));
			categorias.add(tmp);
		}
		
		close();
		
		return categorias;
	}
	
	public Categoria findById(int id) throws SQLException{
		Categoria categoria = null;
		connect();
		pstmt = conn.prepareStatement("select id, nombre from categoria where id = ?");
		pstmt.setInt(1, id);
		resultSet = pstmt.executeQuery();
		
		while(resultSet.next()){
			categoria = new Categoria();
			categoria.setId(resultSet.getInt(1));
			categoria.setName(resultSet.getString(2));
		}
		
		close();
		return categoria;
	}
	
}
