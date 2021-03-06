/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Auxiliarrequerimiento;
import Modelo.Requerimiento;
import Modelo.Residenciajuridica;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Inpeca
 */
@Local
public interface ResidenciajuridicaFacadeLocal {

    void create(Residenciajuridica residenciajuridica);

    void edit(Residenciajuridica residenciajuridica);

    void remove(Residenciajuridica residenciajuridica);

    Residenciajuridica find(Object id);

    List<Residenciajuridica> findAll();

    List<Residenciajuridica> findRange(int[] range);
    
    int count();
    
    List<Residenciajuridica> residenciaxPersona (int idpersona);
    
}
