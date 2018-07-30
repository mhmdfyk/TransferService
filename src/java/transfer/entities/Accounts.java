/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author REACH_MFAYEK
 */
@Entity
@Table(name = "ACCOUNTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accounts.findAll", query = "SELECT a FROM Accounts a"),
    @NamedQuery(name = "Accounts.findByAccountId", query = "SELECT a FROM Accounts a WHERE a.accountId = :accountId"),
    @NamedQuery(name = "Accounts.findByBalance", query = "SELECT a FROM Accounts a WHERE a.balance = :balance"),
    @NamedQuery(name = "Accounts.findByEmployeeName", query = "SELECT a FROM Accounts a WHERE a.employeeName = :employeeName"),
    @NamedQuery(name = "Accounts.findById", query = "SELECT a FROM Accounts a WHERE a.id = :id"),
    @NamedQuery(name = "Accounts.findByCreatedBy", query = "SELECT a FROM Accounts a WHERE a.createdBy = :createdBy"),
    @NamedQuery(name = "Accounts.findByCreationDate", query = "SELECT a FROM Accounts a WHERE a.creationDate = :creationDate")})
public class Accounts extends EntityBean implements Serializable  {
    @Basic(optional = false)
    @NotNull
    @Column(name = "BALANCE")
    private BigDecimal balance;
    private static final long serialVersionUID = 1L;
    
    @Column(name = "ACCOUNT_ID")
    private BigInteger accountId;
    @Size(max = 100)
    @Column(name = "EMPLOYEE_NAME")
    private String employeeName;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountFromId")
    private List<TransferRequest> transferRequestList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountToId")
    private List<TransferRequest> transferRequestList1;

    public Accounts() {
    }

    public Accounts(Integer id) {
        this.id = id;
    }

    public Accounts(Integer id, String createdBy, Date creationDate) {
        this.id = id;
        this.createdBy = createdBy;
        this.creationDate = creationDate;
    }

    public BigInteger getAccountId() {
        return accountId;
    }

    public void setAccountId(BigInteger accountId) {
        this.accountId = accountId;
    }


    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @XmlTransient
    public List<TransferRequest> getTransferRequestList() {
        return transferRequestList;
    }

    public void setTransferRequestList(List<TransferRequest> transferRequestList) {
        this.transferRequestList = transferRequestList;
    }

    @XmlTransient
    public List<TransferRequest> getTransferRequestList1() {
        return transferRequestList1;
    }

    public void setTransferRequestList1(List<TransferRequest> transferRequestList1) {
        this.transferRequestList1 = transferRequestList1;
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
        if (!(object instanceof Accounts)) {
            return false;
        }
        Accounts other = (Accounts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "transfer.entities.Accounts[ id=" + id + " ]";
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
}
