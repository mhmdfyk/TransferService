/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer.controler;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import transfer.entities.TransferRequest;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import transfer.controler.exceptions.IllegalOrphanException;
import transfer.controler.exceptions.NonexistentEntityException;
import transfer.controler.exceptions.RollbackFailureException;
import transfer.entities.Accounts;

/**
 *
 * @author REACH_MFAYEK
 */
public class AccountsJpaController implements Serializable {

    public AccountsJpaController(EntityManagerFactory emf) {
        
        this.emf = emf;
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Accounts accounts) throws RollbackFailureException, Exception {
        if (accounts.getTransferRequestList() == null) {
            accounts.setTransferRequestList(new ArrayList<TransferRequest>());
        }
        if (accounts.getTransferRequestList1() == null) {
            accounts.setTransferRequestList1(new ArrayList<TransferRequest>());
        }
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            em.getTransaction().begin();
            List<TransferRequest> attachedTransferRequestList = new ArrayList<TransferRequest>();
            for (TransferRequest transferRequestListTransferRequestToAttach : accounts.getTransferRequestList()) {
                transferRequestListTransferRequestToAttach = em.getReference(transferRequestListTransferRequestToAttach.getClass(), transferRequestListTransferRequestToAttach.getId());
                attachedTransferRequestList.add(transferRequestListTransferRequestToAttach);
            }
            accounts.setTransferRequestList(attachedTransferRequestList);
            List<TransferRequest> attachedTransferRequestList1 = new ArrayList<TransferRequest>();
            for (TransferRequest transferRequestList1TransferRequestToAttach : accounts.getTransferRequestList1()) {
                transferRequestList1TransferRequestToAttach = em.getReference(transferRequestList1TransferRequestToAttach.getClass(), transferRequestList1TransferRequestToAttach.getId());
                attachedTransferRequestList1.add(transferRequestList1TransferRequestToAttach);
            }
            accounts.setTransferRequestList1(attachedTransferRequestList1);
            em.persist(accounts);
            for (TransferRequest transferRequestListTransferRequest : accounts.getTransferRequestList()) {
                Accounts oldAccountFromIdOfTransferRequestListTransferRequest = transferRequestListTransferRequest.getAccountFromId();
                transferRequestListTransferRequest.setAccountFromId(accounts);
                transferRequestListTransferRequest = em.merge(transferRequestListTransferRequest);
                if (oldAccountFromIdOfTransferRequestListTransferRequest != null) {
                    oldAccountFromIdOfTransferRequestListTransferRequest.getTransferRequestList().remove(transferRequestListTransferRequest);
                    oldAccountFromIdOfTransferRequestListTransferRequest = em.merge(oldAccountFromIdOfTransferRequestListTransferRequest);
                }
            }
            for (TransferRequest transferRequestList1TransferRequest : accounts.getTransferRequestList1()) {
                Accounts oldAccountToIdOfTransferRequestList1TransferRequest = transferRequestList1TransferRequest.getAccountToId();
                transferRequestList1TransferRequest.setAccountToId(accounts);
                transferRequestList1TransferRequest = em.merge(transferRequestList1TransferRequest);
                if (oldAccountToIdOfTransferRequestList1TransferRequest != null) {
                    oldAccountToIdOfTransferRequestList1TransferRequest.getTransferRequestList1().remove(transferRequestList1TransferRequest);
                    oldAccountToIdOfTransferRequestList1TransferRequest = em.merge(oldAccountToIdOfTransferRequestList1TransferRequest);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Accounts accounts) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            
            em = getEntityManager();
            em.getTransaction().begin();
            Accounts persistentAccounts = em.find(Accounts.class, accounts.getId());
            List<TransferRequest> transferRequestListOld = persistentAccounts.getTransferRequestList();
            List<TransferRequest> transferRequestListNew = accounts.getTransferRequestList();
            List<TransferRequest> transferRequestList1Old = persistentAccounts.getTransferRequestList1();
            List<TransferRequest> transferRequestList1New = accounts.getTransferRequestList1();
            List<String> illegalOrphanMessages = null;
            for (TransferRequest transferRequestListOldTransferRequest : transferRequestListOld) {
                if (!transferRequestListNew.contains(transferRequestListOldTransferRequest)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransferRequest " + transferRequestListOldTransferRequest + " since its accountFromId field is not nullable.");
                }
            }
            for (TransferRequest transferRequestList1OldTransferRequest : transferRequestList1Old) {
                if (!transferRequestList1New.contains(transferRequestList1OldTransferRequest)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransferRequest " + transferRequestList1OldTransferRequest + " since its accountToId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<TransferRequest> attachedTransferRequestListNew = new ArrayList<TransferRequest>();
            for (TransferRequest transferRequestListNewTransferRequestToAttach : transferRequestListNew) {
                transferRequestListNewTransferRequestToAttach = em.getReference(transferRequestListNewTransferRequestToAttach.getClass(), transferRequestListNewTransferRequestToAttach.getId());
                attachedTransferRequestListNew.add(transferRequestListNewTransferRequestToAttach);
            }
            transferRequestListNew = attachedTransferRequestListNew;
            accounts.setTransferRequestList(transferRequestListNew);
            List<TransferRequest> attachedTransferRequestList1New = new ArrayList<TransferRequest>();
            for (TransferRequest transferRequestList1NewTransferRequestToAttach : transferRequestList1New) {
                transferRequestList1NewTransferRequestToAttach = em.getReference(transferRequestList1NewTransferRequestToAttach.getClass(), transferRequestList1NewTransferRequestToAttach.getId());
                attachedTransferRequestList1New.add(transferRequestList1NewTransferRequestToAttach);
            }
            transferRequestList1New = attachedTransferRequestList1New;
            accounts.setTransferRequestList1(transferRequestList1New);
            accounts = em.merge(accounts);
            for (TransferRequest transferRequestListNewTransferRequest : transferRequestListNew) {
                if (!transferRequestListOld.contains(transferRequestListNewTransferRequest)) {
                    Accounts oldAccountFromIdOfTransferRequestListNewTransferRequest = transferRequestListNewTransferRequest.getAccountFromId();
                    transferRequestListNewTransferRequest.setAccountFromId(accounts);
                    transferRequestListNewTransferRequest = em.merge(transferRequestListNewTransferRequest);
                    if (oldAccountFromIdOfTransferRequestListNewTransferRequest != null && !oldAccountFromIdOfTransferRequestListNewTransferRequest.equals(accounts)) {
                        oldAccountFromIdOfTransferRequestListNewTransferRequest.getTransferRequestList().remove(transferRequestListNewTransferRequest);
                        oldAccountFromIdOfTransferRequestListNewTransferRequest = em.merge(oldAccountFromIdOfTransferRequestListNewTransferRequest);
                    }
                }
            }
            for (TransferRequest transferRequestList1NewTransferRequest : transferRequestList1New) {
                if (!transferRequestList1Old.contains(transferRequestList1NewTransferRequest)) {
                    Accounts oldAccountToIdOfTransferRequestList1NewTransferRequest = transferRequestList1NewTransferRequest.getAccountToId();
                    transferRequestList1NewTransferRequest.setAccountToId(accounts);
                    transferRequestList1NewTransferRequest = em.merge(transferRequestList1NewTransferRequest);
                    if (oldAccountToIdOfTransferRequestList1NewTransferRequest != null && !oldAccountToIdOfTransferRequestList1NewTransferRequest.equals(accounts)) {
                        oldAccountToIdOfTransferRequestList1NewTransferRequest.getTransferRequestList1().remove(transferRequestList1NewTransferRequest);
                        oldAccountToIdOfTransferRequestList1NewTransferRequest = em.merge(oldAccountToIdOfTransferRequestList1NewTransferRequest);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = accounts.getId();
                if (findAccounts(id) == null) {
                    throw new NonexistentEntityException("The accounts with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            em.getTransaction().begin();
            em = getEntityManager();
            Accounts accounts;
            try {
                accounts = em.getReference(Accounts.class, id);
                accounts.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accounts with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TransferRequest> transferRequestListOrphanCheck = accounts.getTransferRequestList();
            for (TransferRequest transferRequestListOrphanCheckTransferRequest : transferRequestListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Accounts (" + accounts + ") cannot be destroyed since the TransferRequest " + transferRequestListOrphanCheckTransferRequest + " in its transferRequestList field has a non-nullable accountFromId field.");
            }
            List<TransferRequest> transferRequestList1OrphanCheck = accounts.getTransferRequestList1();
            for (TransferRequest transferRequestList1OrphanCheckTransferRequest : transferRequestList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Accounts (" + accounts + ") cannot be destroyed since the TransferRequest " + transferRequestList1OrphanCheckTransferRequest + " in its transferRequestList1 field has a non-nullable accountToId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(accounts);
            em.getTransaction().commit();
        } catch (Exception ex) {
            try {
                em.getTransaction().rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Accounts> findAccountsEntities() {
        return findAccountsEntities(true, -1, -1);
    }

    public List<Accounts> findAccountsEntities(int maxResults, int firstResult) {
        return findAccountsEntities(false, maxResults, firstResult);
    }

    private List<Accounts> findAccountsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Accounts.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Accounts findAccounts(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Accounts.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccountsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Accounts> rt = cq.from(Accounts.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Accounts findByAccountId(Integer accountId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Accounts> q = (TypedQuery<Accounts>) em.createNamedQuery("Accounts.findByAccountId");
            q.setParameter("accountId" , accountId);
            
            if (q.getResultList().isEmpty()) {
                return null;
            }
            
            return q.getResultList().get(0);
        } finally {
            em.close();
        }
    }
    
}
