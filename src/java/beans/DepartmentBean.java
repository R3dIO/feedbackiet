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
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sapan
 */
@Entity
@Table(name = "department_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DepartmentBean.findAll", query = "SELECT d FROM DepartmentBean d")
    , @NamedQuery(name = "DepartmentBean.findById", query = "SELECT d FROM DepartmentBean d WHERE d.id = :id")
    , @NamedQuery(name = "DepartmentBean.findByDepartmentCode", query = "SELECT d FROM DepartmentBean d WHERE d.departmentCode = :departmentCode")
    , @NamedQuery(name = "DepartmentBean.findByDepartmentName", query = "SELECT d FROM DepartmentBean d WHERE d.departmentName = :departmentName")
    ,@NamedQuery(name = "DepartmentBean.findByCourseId", query = "SELECT d FROM DepartmentBean d WHERE d.courseId = :courseId")})
public class DepartmentBean extends Bean implements Serializable {
    @Expose
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CourseBean courseId;

    private static final long serialVersionUID = 1L;
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Expose
    @Basic(optional = false)
    @Column(name = "department_code")
    private String departmentCode;
    @Expose
    @Basic(optional = false)
    @Column(name = "department_name")
    private String departmentName;
    @OneToMany(mappedBy = "departmentId")
    private Collection<FacultyBean> facultyBeanCollection;
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    @ManyToOne
    private FacultyBean facultyId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departmentId")
    private Collection<ClassBean> classBeanCollection;

    public DepartmentBean() {
    }

    public DepartmentBean(Long id) {
        this.id = id;
    }

    public DepartmentBean(Long id, String departmentCode, String departmentName) {
        this.id = id;
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @XmlTransient
    public Collection<FacultyBean> getFacultyBeanCollection() {
        return facultyBeanCollection;
    }

    public void setFacultyBeanCollection(Collection<FacultyBean> facultyBeanCollection) {
        this.facultyBeanCollection = facultyBeanCollection;
    }

    public FacultyBean getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(FacultyBean facultyId) {
        this.facultyId = facultyId;
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
        if (!(object instanceof DepartmentBean)) {
            return false;
        }
        DepartmentBean other = (DepartmentBean) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.DepartmentBean[ id=" + id + " ]";
    }

    public CourseBean getCourseId() {
        return courseId;
    }

    public void setCourseId(CourseBean courseId) {
        this.courseId = courseId;
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

    public List<DepartmentBean> findAll() {
        TypedQuery<DepartmentBean> query = getEntityManager().createNamedQuery("DepartmentBean.findAll", DepartmentBean.class);
        return query.getResultList();
    }

    public List<DepartmentBean> findByCourseId() {
        TypedQuery<DepartmentBean> query = getEntityManager().createNamedQuery("DepartmentBean.findByCourseId", DepartmentBean.class);
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }

}
