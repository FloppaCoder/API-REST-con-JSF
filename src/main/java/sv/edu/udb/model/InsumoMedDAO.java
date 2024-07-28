package sv.edu.udb.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class InsumoMedDAO extends AppConnection {

    //CREAR un nuevo insumo
    public void addInsumo(InsumoMed insumoMed) throws SQLException{
        connect();
        pstmt = conn.prepareStatement("INSERT INTO insumos_medicos (Nombre, Cantidad, Precio) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, insumoMed.getNombre());
        pstmt.setInt(2, insumoMed.getCantidad());
        pstmt.setDouble(3, insumoMed.getPrecio());
        pstmt.executeUpdate();

        //obteniendo el ultimo id generado
        ResultSet keys= pstmt.getGeneratedKeys();
        keys.next();
        int id = keys.getInt(1);

        insumoMed.setId(id);
        close();
    }

    //OBTENER Insumos
    public ArrayList<InsumoMed> getDataList() throws SQLException { // Consultar todos los registros
        connect(); // Iniciamos la conexión

        stmt = conn.createStatement(); // Creación de statment
        resultSet = stmt.executeQuery("SELECT * FROM insumos_medicos"); // Query para solicitar datos
        ArrayList<InsumoMed> listadoInsumos = new ArrayList<>(); // Declarando ArrayList

        while (resultSet.next()) { // Creación del objeto para añadirlo al listado
            InsumoMed row = new InsumoMed();
            row.setId(resultSet.getInt("Id"));
            row.setNombre(resultSet.getString("Nombre"));
            row.setCantidad(resultSet.getInt("Cantidad"));
            row.setPrecio(resultSet.getDouble("Precio"));
            listadoInsumos.add(row);
        }
        close();
        return listadoInsumos;
    }

    //OBTENER Insumos por ID
    public InsumoMed getDetails(int id) throws SQLException { // Consultar un registro específico
        InsumoMed selectedInsumo = new InsumoMed();
        connect();

        pstmt = conn.prepareStatement("SELECT * FROM insumos_medicos WHERE Id = ?");
        pstmt.setInt(1, id);
        resultSet = pstmt.executeQuery();

        while (resultSet.next()) {
            selectedInsumo.setId(resultSet.getInt("Id"));
            selectedInsumo.setNombre(resultSet.getString("Nombre"));
            selectedInsumo.setCantidad(resultSet.getInt("Cantidad"));
            selectedInsumo.setPrecio(resultSet.getDouble("Precio"));
        }

        close();
        return selectedInsumo;
    }


    //ELIMINAR Insumos por ID
    public void deleteInsumo(int id) throws SQLException {
        connect();
        pstmt = conn.prepareStatement("DELETE FROM insumos_medicos WHERE id = ?");
            pstmt.setInt(1, id);
        pstmt.execute();
        close();
    }
}
