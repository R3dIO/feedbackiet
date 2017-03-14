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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "login_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LoginBean.findAll", query = "SELECT l FROM LoginBean l")
    , @NamedQuery(name = "LoginBean.findByUsername", query = "SELECT l FROM LoginBean l WHERE l.username = :username")
    , @NamedQuery(name = "LoginBean.findByPassword", query = "SELECT l FROM LoginBean l WHERE l.password = :password")
    , @NamedQuery(name = "LoginBean.findByType", query = "SELECT l FROM LoginBean l WHERE l.type = :type")
    ,@NamedQuery(name = "LoginBean.findByUsernameAndPassword", query = "SELECT l FROM LoginBean l WHERE l.username = :username and l.password = :password")})
public class LoginBean extends Bean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FacultyBean facultyId;

    public LoginBean() {
    }

    public LoginBean(String username) {
        this.username = username;
    }

    public LoginBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginBean(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FacultyBean getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(FacultyBean facultyId) {
        this.facultyId = facultyId;
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
        if (!(object instanceof LoginBean)) {
            return false;
        }
        LoginBean other = (LoginBean) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.LoginBean[ username=" + username + " ]";
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

    //Custom Methods
    public LoginBean findByUsernameAndPassword() {
        TypedQuery<LoginBean> query = super.getEntityManager().createNamedQuery("LoginBean.findByUsernameAndPassword", LoginBean.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        LoginBean loginBean = null;
        try {
            loginBean = query.getSingleResult();
        } catch (NoResultException e) {
            LogManager.log("Faculty login details not found\nUsername:" + this.username + "\nPassword:" + this.password);
        } catch (NonUniqueResultException e) {
            LogManager.log("More than one Faculty login details found\nUsername:" + this.username + "\nPassword:" + this.password);
        } catch (Exception e) {
            LogManager.log("Unknown Exception in LoginBean::findByUsernameAndPassword()\n" + e.getMessage());
        }
        return loginBean;
    }
}
