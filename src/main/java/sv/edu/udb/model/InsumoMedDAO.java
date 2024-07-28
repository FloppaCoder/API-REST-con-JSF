package sv.edu.udb.model;

import java.sql.SQLException;
import java.util.ArrayList;

public class InsumoMedDAO extends AppConnection {

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

    public void deleteInsumo(int id) throws SQLException {
        connect();
        pstmt = conn.prepareStatement("DELETE FROM insumos_medicos WHERE id = ?");
            pstmt.setInt(1, id);
        pstmt.execute();
        close();
    }
}
