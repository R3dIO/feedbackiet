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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "class_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClassBean.findAll", query = "SELECT c FROM ClassBean c")
    , @NamedQuery(name = "ClassBean.findById", query = "SELECT c FROM ClassBean c WHERE c.id = :id")
    , @NamedQuery(name = "ClassBean.findByYear", query = "SELECT c FROM ClassBean c WHERE c.year = :year")
    , @NamedQuery(name = "ClassBean.findBySection", query = "SELECT c FROM ClassBean c WHERE c.section = :section")
    , @NamedQuery(name = "ClassBean.findByDepartmentId", query = "SELECT c FROM ClassBean c WHERE c.departmentId = :departmentId")
    , @NamedQuery(name = "ClassBean.findClassByDepartmentIdNotInScheduledFeedback", query = "SELECT c FROM ClassBean c WHERE c.departmentId = :departmentId AND NOT EXISTS (SELECT s FROM ScheduledFeedbackBean s WHERE s.classId = c)")
    , @NamedQuery(name = "ClassBean.findClassByDepartmentIdInScheduledFeedback", query = "SELECT c FROM ClassBean c WHERE c.departmentId = :departmentId AND EXISTS (SELECT s FROM ScheduledFeedbackBean s WHERE s.classId = c)")})
public class ClassBean extends Bean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Expose
    @Basic(optional = false)
    @Column(name = "year")
    private int year;
    @Expose
    @Column(name = "section")
    private String section;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classId")
    private Collection<CsfBean> csfBeanCollection;
    @Expose
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private DepartmentBean departmentId;
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    @ManyToOne
    private FacultyBean facultyId;

    @JoinTable(name = "schema_table", joinColumns = {
        @JoinColumn(name = "class_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "subject_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<SubjectBean> subjectBeanCollection;

    @OneToOne(mappedBy = "classId")
    private ScheduledFeedbackBean scheduledFeedbackBean;

    public ClassBean() {
    }

    public ClassBean(Long id) {
        this.id = id;
    }

    public ClassBean(Long id, int year) {
        this.id = id;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
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

    public FacultyBean getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(FacultyBean facultyId) {
        this.facultyId = facultyId;
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
        if (!(object instanceof ClassBean)) {
            return false;
        }
        ClassBean other = (ClassBean) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.ClassBean[ id=" + id + " ]";
    }

    public ScheduledFeedbackBean getScheduledFeedbackBean() {
        return scheduledFeedbackBean;
    }

    public void setScheduledFeedbackBean(ScheduledFeedbackBean scheduledFeedbackBean) {
        this.scheduledFeedbackBean = scheduledFeedbackBean;
    }

    @XmlTransient
    public Collection<SubjectBean> getSubjectBeanCollection() {
        return subjectBeanCollection;
    }

    public void setSubjectBeanCollection(Collection<SubjectBean> subjectBeanCollection) {
        this.subjectBeanCollection = subjectBeanCollection;
    }

    public boolean persist(ClassBean cb) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cb);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
        LogManager.log("Class added to db successfully!");
        LogManager.log(UtilsManager.beanAsJsonString(cb));
        return true;
    }

    public boolean merge(ClassBean cb) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cb);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            LogManager.log("Exception in editing class: " + e);
            LogManager.log("Class: " + UtilsManager.beanAsJsonString(cb));
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
        LogManager.log("Class added to db successfully!");
        LogManager.log(UtilsManager.beanAsJsonString(cb));
        return true;
    }

    public String getClassCodeById() {
        TypedQuery<ClassBean> query = getEntityManager().createNamedQuery("ClassBean.findById", ClassBean.class);
        query.setParameter("id", id);
        ClassBean cb = null;
        try {
            cb = query.getSingleResult();
        } catch (NoResultException e) {
            LogManager.log("No result found (ClassBean:getClassCodeById()) for id " + id + " " + e);
        }
        String courseCode = cb.departmentId.getCourseId().getCourseCode();
        String departmentCode = cb.departmentId.getDepartmentCode();
        if (cb.section == null) {
            cb.section = "";
        }
        String classCode = courseCode + cb.year + departmentCode + cb.section;
        return classCode;
    }

    public List<ClassBean> findAll() {
        TypedQuery<ClassBean> query = getEntityManager().createNamedQuery("ClassBean.findAll", ClassBean.class);
        return query.getResultList();
    }

    public List<ClassBean> findClassByDepartmentId() {
        TypedQuery<ClassBean> query = getEntityManager().createNamedQuery("ClassBean.findByDepartmentId", ClassBean.class);
        query.setParameter("departmentId", departmentId);
        return query.getResultList();
    }

    public List<ClassBean> findClassByDepartmentIdNotInScheduledFeedback() {
        TypedQuery<ClassBean> query = getEntityManager().createNamedQuery("ClassBean.findClassByDepartmentIdNotInScheduledFeedback", ClassBean.class);
        query.setParameter("departmentId", departmentId);
        return query.getResultList();
    }

    public List<ClassBean> findClassByDepartmentIdInScheduledFeedback() {
        TypedQuery<ClassBean> query = getEntityManager().createNamedQuery("ClassBean.findClassByDepartmentIdInScheduledFeedback", ClassBean.class);
        query.setParameter("departmentId", departmentId);
        return query.getResultList();
    }

    public ClassBean findById() {
        TypedQuery<ClassBean> query = getEntityManager().createNamedQuery("ClassBean.findById", ClassBean.class);
        query.setParameter("id", id);
        ClassBean cb = null;
        try {
            cb = query.getSingleResult();
        } catch (NoResultException e) {
            LogManager.log("Class not found\nId:" + this.id);
        } catch (NonUniqueResultException e) {
            LogManager.log("More than one Class details found\nId:" + this.id);
        } catch (Exception e) {
            LogManager.log("Unknown Exception in ClassBean::findById()\n" + e.getMessage());
        }
        return cb;
    }

    public boolean deleteById(ClassBean cb) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            ClassBean removeClass = em.find(ClassBean.class, cb.getId());
            em.remove(removeClass);
            em.getTransaction().commit();
        } catch (Exception e) {
            LogManager.log("Exception in ClassBean::deleteById(): " + e.getMessage());
            LogManager.log(UtilsManager.beanAsJsonString(cb));
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
        LogManager.log("Class deleted from db successfully!");
        LogManager.log(UtilsManager.beanAsJsonString(cb));
        return true;
    }

}
