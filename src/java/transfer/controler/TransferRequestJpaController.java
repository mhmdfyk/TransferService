/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer.controler;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import transfer.controler.exceptions.NonexistentEntityException;
import transfer.controler.exceptions.RollbackFailureException;
import transfer.entities.Accounts;
import transfer.entities.TransferRequest;

/**
 *
 * @author REACH_MFAYEK
 */
public class TransferRequestJpaController implements Serializable {

    public TransferRequestJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransferRequest transferRequest) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            em.getTransaction().begin();
            Accounts accountFromId = transferRequest.getAccountFromId();
            if (accountFromId != null) {
                accountFromId = em.getReference(accountFromId.getClass(), accountFromId.getId());
                transferRequest.setAccountFromId(accountFromId);
            }
            Accounts accountToId = transferRequest.getAccountToId();
            if (accountToId != null) {
                accountToId = em.getReference(accountToId.getClass(), accountToId.getId());
                transferRequest.setAccountToId(accountToId);
            }
            em.persist(transferRequest);
            if (accountFromId != null) {
                accountFromId.getTransferRequestList().add(transferRequest);
                accountFromId = em.merge(accountFromId);
            }
            if (accountToId != null) {
                accountToId.getTransferRequestList().add(transferRequest);
                accountToId = em.merge(accountToId);
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

    public void edit(TransferRequest transferRequest) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            em.getTransaction().begin();
            TransferRequest persistentTransferRequest = em.find(TransferRequest.class, transferRequest.getId());
            Accounts accountFromIdOld = persistentTransferRequest.getAccountFromId();
            Accounts accountFromIdNew = transferRequest.getAccountFromId();
            Accounts accountToIdOld = persistentTransferRequest.getAccountToId();
            Accounts accountToIdNew = transferRequest.getAccountToId();
            if (accountFromIdNew != null) {
                accountFromIdNew = em.getReference(accountFromIdNew.getClass(), accountFromIdNew.getId());
                transferRequest.setAccountFromId(accountFromIdNew);
            }
            if (accountToIdNew != null) {
                accountToIdNew = em.getReference(accountToIdNew.getClass(), accountToIdNew.getId());
                transferRequest.setAccountToId(accountToIdNew);
            }
            transferRequest = em.merge(transferRequest);
            if (accountFromIdOld != null && !accountFromIdOld.equals(accountFromIdNew)) {
                accountFromIdOld.getTransferRequestList().remove(transferRequest);
                accountFromIdOld = em.merge(accountFromIdOld);
            }
            if (accountFromIdNew != null && !accountFromIdNew.equals(accountFromIdOld)) {
                accountFromIdNew.getTransferRequestList().add(transferRequest);
                accountFromIdNew = em.merge(accountFromIdNew);
            }
            if (accountToIdOld != null && !accountToIdOld.equals(accountToIdNew)) {
                accountToIdOld.getTransferRequestList().remove(transferRequest);
                accountToIdOld = em.merge(accountToIdOld);
            }
            if (accountToIdNew != null && !accountToIdNew.equals(accountToIdOld)) {
                accountToIdNew.getTransferRequestList().add(transferRequest);
                accountToIdNew = em.merge(accountToIdNew);
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
                Integer id = transferRequest.getId();
                if (findTransferRequest(id) == null) {
                    throw new NonexistentEntityException("The transferRequest with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            em.getTransaction().begin();
            TransferRequest transferRequest;
            try {
                transferRequest = em.getReference(TransferRequest.class, id);
                transferRequest.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transferRequest with id " + id + " no longer exists.", enfe);
            }
            Accounts accountFromId = transferRequest.getAccountFromId();
            if (accountFromId != null) {
                accountFromId.getTransferRequestList().remove(transferRequest);
                accountFromId = em.merge(accountFromId);
            }
            Accounts accountToId = transferRequest.getAccountToId();
            if (accountToId != null) {
                accountToId.getTransferRequestList().remove(transferRequest);
                accountToId = em.merge(accountToId);
            }
            em.remove(transferRequest);
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

    public List<TransferRequest> findTransferRequestEntities() {
        return findTransferRequestEntities(true, -1, -1);
    }

    public List<TransferRequest> findTransferRequestEntities(int maxResults, int firstResult) {
        return findTransferRequestEntities(false, maxResults, firstResult);
    }

    private List<TransferRequest> findTransferRequestEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransferRequest.class));
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

    public TransferRequest findTransferRequest(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransferRequest.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransferRequestCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransferRequest> rt = cq.from(TransferRequest.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public void createTransferRequestAndUpdateAccountData(TransferRequest transferRequest) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {

            em = getEntityManager();
            em.getTransaction().begin();
            
            Accounts accountsFrom = em.merge(transferRequest.getAccountFromId());
            Accounts accountsTo = em.merge(transferRequest.getAccountToId());
            
            em.persist(transferRequest);
            
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

}
