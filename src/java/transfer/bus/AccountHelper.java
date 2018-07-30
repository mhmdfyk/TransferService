/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer.bus;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import transfer.controler.AccountsJpaController;
import transfer.entities.Accounts;

/**
 *
 * @author REACH_MFAYEK
 */
public class AccountHelper {

    public void createAccount(Accounts account) throws Exception {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory(PersistenceUnitConstants.TRASFER_SERVICE_PU);
            AccountsJpaController accountsJpaController = new AccountsJpaController(emf);
            accountsJpaController.create(account);
        } catch (Exception ex) {
            throw new Exception(" Error occured while processing request, " + ex.getMessage());
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }

    public Accounts findByAccountId(Integer accountId) throws Exception {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory(PersistenceUnitConstants.TRASFER_SERVICE_PU);
            AccountsJpaController accountsJpaController = new AccountsJpaController(emf);
            return accountsJpaController.findByAccountId(accountId);
        } catch (Exception ex) {
            throw new Exception(" Error occured while processing request, " + ex.getMessage());
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }
    
    

}
