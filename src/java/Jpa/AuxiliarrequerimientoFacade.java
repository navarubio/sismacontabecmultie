/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Auxiliarrequerimiento;
import Modelo.Empresa;
import Modelo.Requerimiento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sofimarye
 */
@Stateless
public class AuxiliarrequerimientoFacade extends AbstractFacade<Auxiliarrequerimiento> implements AuxiliarrequerimientoFacadeLocal{

    @PersistenceContext(unitName = "SismacontabecPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuxiliarrequerimientoFacade() {
        super(Auxiliarrequerimiento.class);
    }
    
    @Override
    public List<Auxiliarrequerimiento> buscarrequerimientosActivos(Empresa empre) {
        String consulta;
        int idstatus = 1;
        List<Auxiliarrequerimiento> lista = null;
        try {
            consulta = "From Auxiliarrequerimiento a where a.iddepartamento.idempresa.idempresa = ?1 and  a.idestatusrequerimiento.idestatusrequerimiento = ?2 Order By a.idauxiliarrequerimiento Desc";
            Query query = em.createQuery(consulta);
            query.setParameter(1, empre.getIdempresa());
            query.setParameter(2, idstatus);
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
}
        return lista;
    }
}
