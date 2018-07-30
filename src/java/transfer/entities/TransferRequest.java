/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author REACH_MFAYEK
 */
@Entity
@Table(name = "TRANSFER_REQUEST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransferRequest.findAll", query = "SELECT t FROM TransferRequest t"),
    @NamedQuery(name = "TransferRequest.findById", query = "SELECT t FROM TransferRequest t WHERE t.id = :id"),
    @NamedQuery(name = "TransferRequest.findByTransferredAmount", query = "SELECT t FROM TransferRequest t WHERE t.transferredAmount = :transferredAmount"),
    @NamedQuery(name = "TransferRequest.findByCreatedBy", query = "SELECT t FROM TransferRequest t WHERE t.createdBy = :createdBy"),
    @NamedQuery(name = "TransferRequest.findByCreationDate", query = "SELECT t FROM TransferRequest t WHERE t.creationDate = :creationDate")})
public class TransferRequest extends EntityBean implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRANSFERRED_AMOUNT")
    private BigDecimal transferredAmount;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
  
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @JoinColumn(name = "ACCOUNT_FROM_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Accounts accountFromId;
    @JoinColumn(name = "ACCOUNT_TO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Accounts accountToId;

    public TransferRequest() {
    }

    public TransferRequest(Integer id) {
        this.id = id;
    }

    public TransferRequest(Integer id, BigDecimal transferredAmount, String createdBy, Date creationDate) {
        this.id = id;
        this.transferredAmount = transferredAmount;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTransferredAmount() {
        return transferredAmount;
    }

    public void setTransferredAmount(BigDecimal transferredAmount) {
        this.transferredAmount = transferredAmount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Accounts getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(Accounts accountFromId) {
        this.accountFromId = accountFromId;
    }

    public Accounts getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(Accounts accountToId) {
        this.accountToId = accountToId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransferRequest)) {
            return false;
        }
        TransferRequest other = (TransferRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "transfer.entities.TransferRequest[ id=" + id + " ]";
    }

 
    
}
