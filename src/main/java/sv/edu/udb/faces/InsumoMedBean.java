package sv.edu.udb.faces;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sv.edu.udb.model.InsumoMed;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class InsumoMedBean implements Serializable {

    private Client client;
    private WebTarget target;
    private List<InsumoMed> insumos;
    private InsumoMed selectedInsumo;
    private String mensajeError;
    private boolean addMode = true;
    private boolean updateMode = false;

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
            if (insumos.isEmpty()) {
                mensajeError = "No hay registros";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensajeError, null));
            }
        } else {
            mensajeError = "Error al cargar insumos";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeError, null));
        }
        response.close();
    }

    public void addInsumo() {
        Response response = target.path("create")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(selectedInsumo));
        if (response.getStatus() == 201) {
            loadInsumos(); // Recargar la lista después de agregar
            clearForm();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Insumo agregado correctamente", null));
            // Redirigir a insumos.xhtml
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("insumos.xhtml");
            } catch (IOException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al redirigir", null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al agregar insumo", null));
        }
        response.close();
    }



    public void updateInsumo() {
        Response response = target.path("update/" + selectedInsumo.getId())
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(selectedInsumo));
        if (response.getStatus() == 200) {
            loadInsumos(); // Recargar la lista después de agregar
            clearForm();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("insumos.xhtml");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Insumo actualizado correctamente", null));
            } catch (IOException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al redirigir", null));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar insumo", null));
        }
        response.close();
    }

    public void deleteInsumo(int id) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            // Imprimir ID para depuración
            System.out.println("Attempting to delete insumo with ID: " + id);

            // Realizar solicitud de eliminación
            Response response = target.path("delete/" + id)
                    .request(MediaType.APPLICATION_JSON)
                    .post(null); // Enviar solicitud POST vacía

            // Verificar el estado de la respuesta
            if (response.getStatus() == 200) {
                loadInsumos(); // Recargar la lista después de eliminar
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Insumo eliminado correctamente", null));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar insumo", null));
            }
        } catch (Exception e) {
            // Manejo de excepciones
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar insumo", null));
        }
    }

    public String prepareEdit(int id) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            // Obtener el insumo desde la API
            Response response = target.path(String.valueOf(id))
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (response.getStatus() == 200) {
                selectedInsumo = response.readEntity(InsumoMed.class);
                context.getExternalContext().getSessionMap().put("selectedInsumo", selectedInsumo);
                return "editInsumo?faces-redirect=true"; // Redirige a editInsumo.xhtml
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Insumo no encontrado", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al preparar edición", null));
            return null;
        }
    }

    public void clearForm() {
        selectedInsumo = new InsumoMed(); // Limpia el formulario
        addMode = true;
        updateMode = false;
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
        this.addMode = false;
        this.updateMode = true;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public boolean isAddMode() {
        return addMode;
    }

    public void setAddMode(boolean addMode) {
        this.addMode = addMode;
    }

    public boolean isUpdateMode() {
        return updateMode;
    }

    public void setUpdateMode(boolean updateMode) {
        this.updateMode = updateMode;
    }
}
