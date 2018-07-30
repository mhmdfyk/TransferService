/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mohamed Fayek.
 */
@MappedSuperclass
public abstract class EntityBean implements Serializable {
    public abstract void setCreatedBy(String createdBy);
    public abstract void setCreationDate(Date createdDate);
    
     @PrePersist
    public void prePersist(){
        setCreationDate(new Date());
        setCreatedBy("Admin");
    }
}
