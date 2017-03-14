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
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import managers.LogManager;

/**
 *
 * @author Sapan
 */
@Entity
@Table(name = "scheduler_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SchedulerBean.findAll", query = "SELECT s FROM SchedulerBean s")
    , @NamedQuery(name = "SchedulerBean.findByUsername", query = "SELECT s FROM SchedulerBean s WHERE s.username = :username")
    , @NamedQuery(name = "SchedulerBean.findByPassword", query = "SELECT s FROM SchedulerBean s WHERE s.password = :password")
    , @NamedQuery(name = "SchedulerBean.findByName", query = "SELECT s FROM SchedulerBean s WHERE s.name = :name")
    , @NamedQuery(name = "SchedulerBean.findByUsernameAndPassword", query = "SELECT s FROM SchedulerBean s WHERE s.username = :username and s.password = :password")})
public class SchedulerBean extends Bean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public SchedulerBean() {
    }

    public SchedulerBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SchedulerBean(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchedulerBean)) {
            return false;
        }
        SchedulerBean other = (SchedulerBean) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.SchedulerBean[ username=" + username + " ]";
    }

    public void persist(Object object) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    //Custom Methods
    public SchedulerBean findByUsernameAndPassword() {
        TypedQuery<SchedulerBean> query = super.getEntityManager().createNamedQuery("SchedulerBean.findByUsernameAndPassword", SchedulerBean.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        SchedulerBean schedulerBean = null;
        try {
            schedulerBean = query.getSingleResult();
        } catch (NoResultException e) {
            LogManager.log("Scheduler login details not found\nUsername:" + this.username + "\nPassword:" + this.password);
        } catch (NonUniqueResultException e) {
            LogManager.log("More than one Scheduler login details found\nUsername:" + this.username + "\nPassword:" + this.password);
        } catch (Exception e) {
            LogManager.log("Unknown Exception in SchedulerBean::findByUsernameAndPassword()\n" + e.getMessage());
        }
        return schedulerBean;
    }

}
