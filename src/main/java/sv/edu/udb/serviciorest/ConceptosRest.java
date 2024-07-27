package sv.edu.udb.serviciorest;

import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sv.edu.udb.model.CategoriasDAO;
import sv.edu.udb.model.Concepto;
import sv.edu.udb.model.ConceptosDAO;

@Path("conceptos")
public class ConceptosRest {

	ConceptosDAO conceptosDAO = new ConceptosDAO();
	CategoriasDAO categoriasDAO = new CategoriasDAO();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getConceptos() throws SQLException{
		List<Concepto> conceptos = conceptosDAO.findAll();
		return Response.status(200).entity(conceptos).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getConceptosById(@PathParam("id") int id) throws SQLException{
	
		Concepto concepto = conceptosDAO.findById(id);
		if(concepto == null){
			return Response.status(404)
					.header("Access-Control-Allow-Origin", "*")
					.entity("Concepto no encontrado").build();
		}
	
		return Response.status(200)
				.header("Access-Control-Allow-Origin", "*")
				.entity(concepto).build();
		}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertConcepto(
			@FormParam("name") String name,
			@FormParam("value") Double value,
			@FormParam("category_id") int category_id
			)throws SQLException{
		
		Concepto concepto = new Concepto();
		
		if(categoriasDAO.findById(category_id)==null){
			return Response.status(400)
			.header("Access-Control-Allow-Origin", "*")
			.entity("Categoria no corresponde a ninguna existencia").build();
			}
			concepto.setName(name);
			concepto.setValue(value);
			concepto.setCategoryId(category_id);
			conceptosDAO.insert(concepto);
			return Response.status(201)
			.header("Access-Control-Allow-Origin", "*")
			.entity(concepto)
			.build();
		
	}
	
	//@DELETE
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("delete/{id}")
	public Response eliminarConcepto(
	@PathParam("id") int id
	) throws SQLException{
	Concepto concepto = conceptosDAO.findById(id);
	if(concepto == null){
	return Response.status(400)
	.entity("Concepto no corresponde a ninguna existencia")
	.header("Access-Control-Allow-Origin", "*")
	.build();
	}
	conceptosDAO.delete(id);
	return Response.status(200)
	.header("Access-Control-Allow-Origin", "*")
	.entity(concepto)
	.build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response updateConcepto(
	@PathParam("id") int id,
	@FormParam("name") String name,
	@FormParam("value") Double value,
	@FormParam("category_id") int category_id
	) throws SQLException{
	Concepto concepto = conceptosDAO.findById(id);
	if(concepto == null){
	return Response.status(404)
	.header("Access-Control-Allow-Origin", "*")
	.entity("Concepto no corresponde a ninguna existencia")

	.build();

	}
	if(categoriasDAO.findById(category_id)==null){
	return Response.status(400)
	.header("Access-Control-Allow-Origin", "*")
	.entity("Categoria no corresponde a ninguna existencia")

	.build();

	}
	
	concepto.setName(name);
	concepto.setValue(value);
	concepto.setCategoryId(category_id);
	conceptosDAO.update(concepto);
	return Response.status(200)
	.header("Access-Control-Allow-Origin", "*")
	.entity(concepto)
	.build();
	}
	
}
