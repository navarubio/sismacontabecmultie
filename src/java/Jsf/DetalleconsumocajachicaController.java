package Jsf;

import Modelo.Detalleconsumocajachica;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.DetalleconsumocajachicaFacade;
import Jpa.DetalleconsumocajachicaFacadeLocal;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("detalleconsumocajachicaController")
@ViewScoped
public class DetalleconsumocajachicaController implements Serializable {

    @EJB
    private Jpa.DetalleconsumocajachicaFacadeLocal ejbFacade;
    private List<Detalleconsumocajachica> items = null;
    private Detalleconsumocajachica selected;
    @Inject
    private RequerimientosController requerimientosController;

    public DetalleconsumocajachicaController() {
    }

    public Detalleconsumocajachica getSelected() {
        return selected;
    }

    public void setSelected(Detalleconsumocajachica selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DetalleconsumocajachicaFacadeLocal getFacade() {
        return ejbFacade;
    }

    public Detalleconsumocajachica prepareCreate() {
        selected = new Detalleconsumocajachica();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundlecajachica").getString("DetalleconsumocajachicaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundlecajachica").getString("DetalleconsumocajachicaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundlecajachica").getString("DetalleconsumocajachicaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Detalleconsumocajachica> getItems() {
        if (items == null) {
            items = getFacade().detallesconsumoscajachicaAll(requerimientosController.getEmpresa());
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlecajachica").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlecajachica").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Detalleconsumocajachica getDetalleconsumocajachica(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Detalleconsumocajachica> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Detalleconsumocajachica> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Detalleconsumocajachica.class)
    public static class DetalleconsumocajachicaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DetalleconsumocajachicaController controller = (DetalleconsumocajachicaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "detalleconsumocajachicaController");
            return controller.getDetalleconsumocajachica(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Detalleconsumocajachica) {
                Detalleconsumocajachica o = (Detalleconsumocajachica) object;
                return getStringKey(o.getIddetalleconsumocajachica());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Detalleconsumocajachica.class.getName()});
                return null;
            }
        }

    }

}
