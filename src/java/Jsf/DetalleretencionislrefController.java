package Jsf;

import Modelo.Detalleretencionislref;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.DetalleretencionislrefFacadeLocal;
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

@Named("detalleretencionislrefController")
@SessionScoped
public class DetalleretencionislrefController implements Serializable {

    @EJB
    private Jpa.DetalleretencionislrefFacadeLocal ejbFacade;
    private List<Detalleretencionislref> items = null;
    private Detalleretencionislref selected;

    public DetalleretencionislrefController() {
    }

    public Detalleretencionislref getSelected() {
        return selected;
    }

    public void setSelected(Detalleretencionislref selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DetalleretencionislrefFacadeLocal getFacade() {
        return ejbFacade;
    }

    public Detalleretencionislref prepareCreate() {
        selected = new Detalleretencionislref();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundletributos").getString("DetalleretencionislrefCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundletributos").getString("DetalleretencionislrefUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundletributos").getString("DetalleretencionislrefDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Detalleretencionislref> getItems() {
        if (items == null) {
            items = getFacade().findAll();
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundletributos").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundletributos").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Detalleretencionislref getDetalleretencionislref(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Detalleretencionislref> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Detalleretencionislref> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Detalleretencionislref.class)
    public static class DetalleretencionislrefControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DetalleretencionislrefController controller = (DetalleretencionislrefController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "detalleretencionislrefController");
            return controller.getDetalleretencionislref(getKey(value));
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
            if (object instanceof Detalleretencionislref) {
                Detalleretencionislref o = (Detalleretencionislref) object;
                return getStringKey(o.getIddetalleretencionislref());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Detalleretencionislref.class.getName()});
                return null;
            }
        }

    }

}