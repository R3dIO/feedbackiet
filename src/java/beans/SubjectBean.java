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
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
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
@Table(name = "subject_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubjectBean.findAll", query = "SELECT s FROM SubjectBean s")
    , @NamedQuery(name = "SubjectBean.findById", query = "SELECT s FROM SubjectBean s WHERE s.id = :id")
    , @NamedQuery(name = "SubjectBean.findBySubjectCode", query = "SELECT s FROM SubjectBean s WHERE s.subjectCode = :subjectCode")
    , @NamedQuery(name = "SubjectBean.findBySubjectName", query = "SELECT s FROM SubjectBean s WHERE s.subjectName = :subjectName")
    , @NamedQuery(name = "SubjectBean.findSubjectNotAssosiatedWithClassByClassId", query = "SELECT s FROM SubjectBean s WHERE :classId NOT MEMBER OF s.classBeanCollection")})
public class SubjectBean extends Bean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Expose
    @Basic(optional = false)
    @Column(name = "subject_code")
    private String subjectCode;
    @Expose
    @Basic(optional = false)
    @Column(name = "subject_name")
    private String subjectName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subjectId")
    private Collection<CsfBean> csfBeanCollection;

    @ManyToMany(mappedBy = "subjectBeanCollection")
    private Collection<ClassBean> classBeanCollection;

    public SubjectBean() {
    }

    public SubjectBean(Long id) {
        this.id = id;
    }

    public SubjectBean(Long id, String subjectCode, String subjectName) {
        this.id = id;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @XmlTransient
    public Collection<CsfBean> getCsfBeanCollection() {
        return csfBeanCollection;
    }

    public void setCsfBeanCollection(Collection<CsfBean> csfBeanCollection) {
        this.csfBeanCollection = csfBeanCollection;
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
        if (!(object instanceof SubjectBean)) {
            return false;
        }
        SubjectBean other = (SubjectBean) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.SubjectBean[ id=" + id + " ]";
    }

    public boolean persist(SubjectBean sb) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(sb);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
        LogManager.log("Subject added to db successfully!");
        LogManager.log(UtilsManager.beanAsJsonString(sb));
        return true;
    }

    public boolean merge(SubjectBean sb) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(sb);
            em.getTransaction().commit();
        } catch (Exception e) {
            LogManager.log("Exception in editing subject: " + e);
            LogManager.log("Subject: " + UtilsManager.beanAsJsonString(sb));
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
        LogManager.log("Subject added to db successfully!");
        LogManager.log(UtilsManager.beanAsJsonString(sb));
        return true;
    }

    public List<SubjectBean> findAll() {
        TypedQuery<SubjectBean> query = getEntityManager().createNamedQuery("SubjectBean.findAll", SubjectBean.class);
        return query.getResultList();
    }

    public SubjectBean findById() {
        TypedQuery<SubjectBean> query = getEntityManager().createNamedQuery("SubjectBean.findById", SubjectBean.class);
        query.setParameter("id", id);
        SubjectBean sb = null;
        try {
            sb = query.getSingleResult();
        } catch (NoResultException e) {
            LogManager.log("Subject not found\nId:" + this.id);
        } catch (NonUniqueResultException e) {
            LogManager.log("More than one Subject details found\nId:" + this.id);
        } catch (Exception e) {
            LogManager.log("Unknown Exception in SubjectBean::findById()\n" + e.getMessage());
        }
        return sb;
    }

    public List<SubjectBean> findSubjectNotAssosiatedWithClassByClassId(ClassBean cb) {
        TypedQuery<SubjectBean> query = getEntityManager().createNamedQuery("SubjectBean.findSubjectNotAssosiatedWithClassByClassId", SubjectBean.class);
        query.setParameter("classId", cb);
        return query.getResultList();

    }
}
