/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer.bus;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import transfer.controler.AccountsJpaController;
import transfer.controler.TransferRequestJpaController;
import transfer.entities.Accounts;
import transfer.entities.TransferRequest;

/**
 *
 * @author REACH_MFAYEK
 */
public class TransferRequestHelper {
    
     public void createTransferRequestAndUpdateAccountData(TransferRequest transferRequest) throws Exception {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory(PersistenceUnitConstants.TRASFER_SERVICE_PU);
            TransferRequestJpaController transferRequestJpaController = new TransferRequestJpaController(emf);
            transferRequestJpaController.createTransferRequestAndUpdateAccountData(transferRequest);
        } catch (Exception ex) {
            throw new Exception(" Error occured while processing request, " + ex.getMessage());
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }
}
