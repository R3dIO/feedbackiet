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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Table(name = "course_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CourseBean.findAll", query = "SELECT c FROM CourseBean c")
    , @NamedQuery(name = "CourseBean.findById", query = "SELECT c FROM CourseBean c WHERE c.id = :id")
    , @NamedQuery(name = "CourseBean.findByCourseCode", query = "SELECT c FROM CourseBean c WHERE c.courseCode = :courseCode")
    , @NamedQuery(name = "CourseBean.findByCourseName", query = "SELECT c FROM CourseBean c WHERE c.courseName = :courseName")
    , @NamedQuery(name = "CourseBean.findByDuration", query = "SELECT c FROM CourseBean c WHERE c.duration = :duration")})
public class CourseBean extends Bean implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseId")
    private Collection<DepartmentBean> departmentBeanCollection;

    private static final long serialVersionUID = 1L;
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Expose
    @Basic(optional = false)
    @Column(name = "course_code")
    private String courseCode;
    @Expose
    @Basic(optional = false)
    @Column(name = "course_name")
    private String courseName;
    @Expose
    @Basic(optional = false)
    @Column(name = "duration")
    private int duration;

    public CourseBean() {
    }

    public CourseBean(Long id) {
        this.id = id;
    }

    public CourseBean(Long id, String courseCode, String courseName, int duration) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
        if (!(object instanceof CourseBean)) {
            return false;
        }
        CourseBean other = (CourseBean) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.CourseBean[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<DepartmentBean> getDepartmentBeanCollection() {
        return departmentBeanCollection;
    }

    public void setDepartmentBeanCollection(Collection<DepartmentBean> departmentBeanCollection) {
        this.departmentBeanCollection = departmentBeanCollection;
    }

    public boolean persist(CourseBean cb) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cb);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            LogManager.log("Exception in adding course: " + e);
            LogManager.log("Course: " + UtilsManager.beanAsJsonString(cb));
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
        LogManager.log("Course added to db successfully!");
        LogManager.log(UtilsManager.beanAsJsonString(cb));
        return true;
    }

    public boolean merge(CourseBean cb) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(cb);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            LogManager.log("Exception in editing course: " + e);
            LogManager.log("Course: " + UtilsManager.beanAsJsonString(cb));
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
        LogManager.log("Course edited to db successfully!");
        LogManager.log(UtilsManager.beanAsJsonString(cb));
        return true;
    }

    public List<CourseBean> findAll() {
        TypedQuery<CourseBean> query = getEntityManager().createNamedQuery("CourseBean.findAll", CourseBean.class);
        return query.getResultList();
    }

    public CourseBean findById() {
        TypedQuery<CourseBean> query = getEntityManager().createNamedQuery("CourseBean.findById", CourseBean.class);
        query.setParameter("id", id);
        CourseBean cb = null;
        try {
            cb = query.getSingleResult();
        } catch (NoResultException e) {
            LogManager.log("Course not found\nId:" + this.id);
        } catch (NonUniqueResultException e) {
            LogManager.log("More than one Course login details found\nId:" + this.id);
        } catch (Exception e) {
            LogManager.log("Unknown Exception in CourseBean::findById()\n" + e.getMessage());
        }
        return cb;
    }
}
