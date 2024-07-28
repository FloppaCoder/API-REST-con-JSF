package sv.edu.udb.faces;

import sv.edu.udb.model.InsumoMed;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class InsumoMedBean implements Serializable {

    private Client client;
    private WebTarget target;
    private List<InsumoMed> insumos;
    private InsumoMed selectedInsumo;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        target = client.target("http://localhost:8080/DWF404_AmayaEleazar_CruzMarcelo-1.0-SNAPSHOT/api/insumoMed");
        loadInsumos();
        selectedInsumo = new InsumoMed();
    }

    public void loadInsumos() {
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        if (response.getStatus() == 200) {
            insumos = response.readEntity(new GenericType<List<InsumoMed>>() {});
        } else {
            // Manejar error
            System.out.println("Error al cargar insumos: " + response.getStatus());
        }
        response.close();
    }

    public void addInsumo() {
        Response response = target.path("create")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(selectedInsumo));
        if (response.getStatus() == 201) {
            loadInsumos(); // Recargar la lista después de agregar
        } else {
            // Manejar error
            System.out.println("Error al agregar insumo: " + response.getStatus());
        }
        response.close();
    }

    public void updateInsumo() {
        Response response = target.path("update/" + selectedInsumo.getId())
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(selectedInsumo));
        if (response.getStatus() == 200) {
            loadInsumos(); // Recargar la lista después de actualizar
        } else {
            // Manejar error
            System.out.println("Error al actualizar insumo: " + response.getStatus());
        }
        response.close();
    }

    public void deleteInsumo(int id) {
        Response response = target.path("delete/" + id)
                .request(MediaType.APPLICATION_JSON)
                .post(null);
        if (response.getStatus() == 200) {
            loadInsumos(); // Recargar la lista después de eliminar
        } else {
            // Manejar error
            System.out.println("Error al eliminar insumo: " + response.getStatus());
        }
        response.close();
    }

    // Getters y Setters

    public List<InsumoMed> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<InsumoMed> insumos) {
        this.insumos = insumos;
    }

    public InsumoMed getSelectedInsumo() {
        return selectedInsumo;
    }

    public void setSelectedInsumo(InsumoMed selectedInsumo) {
        this.selectedInsumo = selectedInsumo;
    }
}
