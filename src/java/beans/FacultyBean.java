/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import managers.LogManager;
import managers.UtilsManager;

/**
 *
 * @author Sapan
 */
@Entity
@Table(name = "faculty_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacultyBean.findAll", query = "SELECT f FROM FacultyBean f")
    , @NamedQuery(name = "FacultyBean.findById", query = "SELECT f FROM FacultyBean f WHERE f.id = :id")
    , @NamedQuery(name = "FacultyBean.findByTitle", query = "SELECT f FROM FacultyBean f WHERE f.title = :title")
    , @NamedQuery(name = "FacultyBean.findByFirstName", query = "SELECT f FROM FacultyBean f WHERE f.firstName = :firstName")
    , @NamedQuery(name = "FacultyBean.findByLastName", query = "SELECT f FROM FacultyBean f WHERE f.lastName = :lastName")
    , @NamedQuery(name = "FacultyBean.findByDesignation", query = "SELECT f FROM FacultyBean f WHERE f.designation = :designation")
    , @NamedQuery(name = "FacultyBean.findByEmail", query = "SELECT f FROM FacultyBean f WHERE f.email = :email")
    , @NamedQuery(name = "FacultyBean.findByPhone", query = "SELECT f FROM FacultyBean f WHERE f.phone = :phone")})
public class FacultyBean extends Bean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Expose
    @Column(name = "title")
    private String title;
    @Expose
    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;
    @Expose
    @Column(name = "last_name")
    private String lastName;
    @Expose
    @Column(name = "designation")
    private String designation;
    @Expose
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Expose
    @Column(name = "phone")
    private String phone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facultyId")
    private Collection<CsfBean> csfBeanCollection;
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @ManyToOne
    private DepartmentBean departmentId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facultyId")
    private Collection<LoginBean> loginBeanCollection;
    @OneToMany(mappedBy = "facultyId")
    private Collection<DepartmentBean> departmentBeanCollection;
    @OneToMany(mappedBy = "facultyId")
    private Collection<ClassBean> classBeanCollection;

    public FacultyBean() {
    }

    public FacultyBean(Long id) {
        this.id = id;
    }

    public FacultyBean(Long id, String firstName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlTransient
    public Collection<CsfBean> getCsfBeanCollection() {
        return csfBeanCollection;
    }

    public void setCsfBeanCollection(Collection<CsfBean> csfBeanCollection) {
        this.csfBeanCollection = csfBeanCollection;
    }

    public DepartmentBean getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(DepartmentBean departmentId) {
        this.departmentId = departmentId;
    }

    @XmlTransient
    public Collection<LoginBean> getLoginBeanCollection() {
        return loginBeanCollection;
    }

    public void setLoginBeanCollection(Collection<LoginBean> loginBeanCollection) {
        this.loginBeanCollection = loginBeanCollection;
    }

    @XmlTransient
    public Collection<DepartmentBean> getDepartmentBeanCollection() {
        return departmentBeanCollection;
    }

    public void setDepartmentBeanCollection(Collection<DepartmentBean> departmentBeanCollection) {
        this.departmentBeanCollection = departmentBeanCollection;
    }

    @XmlTransient
    public Collection<ClassBean> getClassBeanCollection() {
        return classBeanCollection;
    }

    public void setClassBeanCollection(Collection<ClassBean> classBeanCollection) {
        this.classBeanCollection = classBeanCollection;
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
        if (!(object instanceof FacultyBean)) {
            return false;
        }
        FacultyBean other = (FacultyBean) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.FacultyBean[ id=" + id + " ]";
    }

    public boolean persist(FacultyBean fb) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(fb);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            LogManager.log("Exception in adding faculty: " + e);
            LogManager.log("Faculty: " + UtilsManager.beanAsJsonString(fb));
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
        LogManager.log("Faculty added to db successfully!");
        LogManager.log(UtilsManager.beanAsJsonString(fb));
        return true;
    }

    public boolean merge(FacultyBean fb) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(fb);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            LogManager.log("Exception in editing faculty: " + e);
            LogManager.log("Faculty: " + UtilsManager.beanAsJsonString(fb));
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
        LogManager.log("Faculty edited to db successfully!");
        LogManager.log(UtilsManager.beanAsJsonString(fb));
        return true;
    }

    public FacultyBean findById() {
        TypedQuery<FacultyBean> query = getEntityManager().createNamedQuery("FacultyBean.findById", FacultyBean.class);
        query.setParameter("id", id);
        FacultyBean fb = null;
        try {
            fb = query.getSingleResult();
        } catch (NoResultException e) {
            LogManager.log("Faculty not found\nId:" + this.id);
        } catch (NonUniqueResultException e) {
            LogManager.log("More than one Faculty details found\nId:" + this.id);
        } catch (Exception e) {
            LogManager.log("Unknown Exception in FacultyBean::findById()\n" + e.getMessage());
        }
        return fb;
    }

    public List<FacultyBean> findAll() {
        TypedQuery<FacultyBean> query = getEntityManager().createNamedQuery("FacultyBean.findAll", FacultyBean.class);
        return query.getResultList();
    }

    public boolean deleteById(FacultyBean fb) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            FacultyBean removeFaculty = em.find(FacultyBean.class, fb.getId());
            em.remove(removeFaculty);
            em.getTransaction().commit();
        } catch (Exception e) {
            LogManager.log("Exception in FacultyBean::deleteById(): " + e.getMessage());
            LogManager.log(UtilsManager.beanAsJsonString(fb));
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
        LogManager.log("Faculty deleted from db successfully!");
        LogManager.log(UtilsManager.beanAsJsonString(fb));
        return true;
    }
}
