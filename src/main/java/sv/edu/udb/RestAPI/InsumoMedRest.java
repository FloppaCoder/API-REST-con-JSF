package sv.edu.udb.RestAPI;
import sv.edu.udb.model.InsumoMedDAO;

import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sv.edu.udb.model.InsumoMed;

@Path("insumoMed") // Asignando ruta para acceder a la API Rest

public class InsumoMedRest {

    InsumoMedDAO insumoMedDAO = new InsumoMedDAO();

    @POST // Petición POST agregar nuevo insumo
    @Produces(MediaType.APPLICATION_JSON)
    public Response addInsumo(
            @FormParam("nombre") String nombre,
            @FormParam("cantidad") int cantidad,
            @FormParam("precio") double precio
    ) throws SQLException{
        InsumoMed insumoMed = new InsumoMed();

        insumoMed.setNombre(nombre);
        insumoMed.setCantidad(cantidad);
        insumoMed.setPrecio(precio);
        insumoMedDAO.addInsumo(insumoMed);

        return Response.status(201)
                .header("Access-Control-Allow-Origin", "*")
                .entity(insumoMed)
                .build();
    }

    @GET // Petición GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response getInsumoMed() throws SQLException{ // Listar todos los registros
            InsumoMedDAO dao = new InsumoMedDAO();
            List<InsumoMed> listaInsumoMed = dao.getDataList();

            return Response.status(200).entity(listaInsumoMed).build();
        }

    @GET
    @Path("{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getInsumoMed(@PathParam("id") int id) throws SQLException{
            InsumoMedDAO dao = new InsumoMedDAO();

            InsumoMed selectedInsumo = dao.getDetails(id);

            if(selectedInsumo == null || selectedInsumo.getId() == 0){
                return Response.status(404).header("Access-Control-Allow-Origin", "*")
                        .entity("Insumo no encontrado").build();
            }

            return Response.status(200).entity(selectedInsumo).build();
        }

    @POST //@DELETE
        @Produces(MediaType.APPLICATION_JSON)
        @Path("delete/{id}")
        public Response eliminarConcepto(@PathParam("id") int id) throws SQLException{
            InsumoMedDAO dao = new InsumoMedDAO();
            InsumoMed insumoMed = dao.getDetails(id);

            if(insumoMed == null){
                return Response.status(400)
                        .entity("El insumo seleccionado no existe").header("Access-Control-Allow-Origin", "*").build();
            }
            dao.deleteInsumo(id);
            return Response.status(200)
                    .header("Access-Control-Allow-Origin", "*").entity(insumoMed).build();
        }
}
