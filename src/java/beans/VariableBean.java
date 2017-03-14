/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import managers.LogManager;

/**
 *
 * @author Sapan
 */
@Entity
@Table(name = "variable_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VariableBean.findAll", query = "SELECT v FROM VariableBean v")
    , @NamedQuery(name = "VariableBean.findByName", query = "SELECT v FROM VariableBean v WHERE v.name = :name")
    , @NamedQuery(name = "VariableBean.findByValue", query = "SELECT v FROM VariableBean v WHERE v.value = :value")})
public class VariableBean extends Bean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "value")
    private String value;

    public VariableBean() {
    }

    public VariableBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VariableBean)) {
            return false;
        }
        VariableBean other = (VariableBean) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.VariableBean[ name=" + name + " ]";
    }

    public void persist(Object object) {
        EntityManager em = super.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            LogManager.log("Exception in persist method: " + e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public VariableBean findByName() {
        EntityManager em = super.getEntityManager();
        VariableBean vb = null;
        try {
            TypedQuery<VariableBean> query = em.createNamedQuery("VariableBean.findByName", VariableBean.class);
            query.setParameter("name", name);
            vb = query.getSingleResult();
        } catch (NoResultException e) {
            LogManager.log("Variable Details not found\nName:" + this.name);
        } catch (NonUniqueResultException e) {
            LogManager.log("More than one Variable Details found\nName:" + this.name);
        } catch (Exception e) {
            LogManager.log("Unknown Exception in VariableBean::findByName()\n" + e.getMessage());
        }
        return vb;
    }
}
