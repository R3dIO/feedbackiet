/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Query;
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
@Table(name = "csf_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CsfBean.findAll", query = "SELECT c FROM CsfBean c")
    , @NamedQuery(name = "CsfBean.findById", query = "SELECT c FROM CsfBean c WHERE c.id = :id")
    ,  @NamedQuery(name = "CsfBean.findByClassIdAndSessionId", query = "SELECT c FROM CsfBean c WHERE c.classId = :classId AND c.sessionId = :sessionId")
    ,  @NamedQuery(name = "CsfBean.findSubjectByFacultyId", query = "SELECT DISTINCT c.subjectId FROM CsfBean c WHERE c.facultyId = :facultyId AND c.sessionId = :sessionId")
    ,  @NamedQuery(name = "CsfBean.findClassByFacultyId", query = "SELECT DISTINCT c.classId FROM CsfBean c WHERE c.facultyId = :facultyId AND c.sessionId = :sessionId")
    ,  @NamedQuery(name = "CsfBean.findByFacultyIdAndSubjectId", query = "SELECT c FROM CsfBean c WHERE c.facultyId = :facultyId AND c.sessionId = :sessionId AND c.subjectId =:subjectId ")
    ,  @NamedQuery(name = "CsfBean.findByClassIdAndSubjectIdAndSessionIdAndFacultyId", query = "SELECT c FROM CsfBean c WHERE c.classId = :classId AND c.sessionId = :sessionId AND c.facultyId=:facultyId AND c.subjectId=:subjectId ")})
public class CsfBean extends Bean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Expose
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ClassBean classId;
    @Expose
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FacultyBean facultyId;
    @Expose
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SessionBean sessionId;
    @Expose
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SubjectBean subjectId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "csfId")
    private Collection<FeedbackBean> feedbackBeanCollection;

    public CsfBean() {
    }

    public CsfBean(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClassBean getClassId() {
        return classId;
    }

    public void setClassId(ClassBean classId) {
        this.classId = classId;
    }

    public FacultyBean getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(FacultyBean facultyId) {
        this.facultyId = facultyId;
    }

    public SessionBean getSessionId() {
        return sessionId;
    }

    public void setSessionId(SessionBean sessionId) {
        this.sessionId = sessionId;
    }

    public SubjectBean getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(SubjectBean subjectId) {
        this.subjectId = subjectId;
    }

    @XmlTransient
    public Collection<FeedbackBean> getFeedbackBeanCollection() {
        return feedbackBeanCollection;
    }

    public void setFeedbackBeanCollection(Collection<FeedbackBean> feedbackBeanCollection) {
        this.feedbackBeanCollection = feedbackBeanCollection;
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
        if (!(object instanceof CsfBean)) {
            return false;
        }
        CsfBean other = (CsfBean) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.CsfBean[ id=" + id + " ]";
    }

    public boolean persist(CsfBean csf) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(csf);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();

        }
        LogManager.log("CSF added to db successfully!");
        LogManager.log(UtilsManager.beanAsJsonString(csf));
        return true;
    }

    public CsfBean findById() {
        TypedQuery<CsfBean> query = getEntityManager().createNamedQuery("CsfBean.findById", CsfBean.class);
        query.setParameter("id", id);
        CsfBean csf = null;
        try {
            csf = query.getSingleResult();
        } catch (NoResultException e) {
            LogManager.log("Csf not found\nId:" + this.id);
        } catch (NonUniqueResultException e) {
            LogManager.log("More than one Csf details found\nId:" + this.id);
        } catch (Exception e) {
            LogManager.log("Unknown Exception in CsfBean::findById()\n" + e.getMessage());
        }
        return csf;
    }

    public List<CsfBean> findByClassIdAndSessionId() {
        TypedQuery<CsfBean> query = getEntityManager().createNamedQuery("CsfBean.findByClassIdAndSessionId", CsfBean.class);
        if (sessionId == null) {
            sessionId = UtilsManager.getCurrentSessionId();
        }
        query.setParameter("classId", classId);
        query.setParameter("sessionId", sessionId);
        return query.getResultList();
    }

    public List<CsfBean> findByClassIdAndSubjectIdAndSessionIdAndFacultyId() {
        TypedQuery<CsfBean> query = getEntityManager().createNamedQuery("CsfBean.findByClassIdAndSubjectIdAndSessionIdAndFacultyId", CsfBean.class);
        if (sessionId == null) {
            sessionId = UtilsManager.getCurrentSessionId();
        }
        query.setParameter("classId", classId);
        query.setParameter("sessionId", sessionId);
        query.setParameter("facultyId", facultyId);
        query.setParameter("subjectId", subjectId);
        return query.getResultList();
    }

    public List<SubjectBean> findSubjectByFacultyId() {
        TypedQuery<SubjectBean> query = getEntityManager().createNamedQuery("CsfBean.findSubjectByFacultyId", SubjectBean.class);
        if (sessionId == null) {
            sessionId = UtilsManager.getCurrentSessionId();
        }
        query.setParameter("facultyId", facultyId);
        query.setParameter("sessionId", sessionId);
        return query.getResultList();
    }

    public List<ClassBean> findClassByFacultyId() {
        TypedQuery<ClassBean> query = getEntityManager().createNamedQuery("CsfBean.findClassByFacultyId", ClassBean.class);
        if (sessionId == null) {
            sessionId = UtilsManager.getCurrentSessionId();
        }
        query.setParameter("facultyId", facultyId);
        query.setParameter("sessionId", sessionId);
        return query.getResultList();
    }

    public List<CsfBean> findByFacultyIdAndSubjectId() {
        TypedQuery<CsfBean> query = getEntityManager().createNamedQuery("CsfBean.findByFacultyIdAndSubjectId", CsfBean.class);
        if (sessionId == null) {
            sessionId = UtilsManager.getCurrentSessionId();
        }
        query.setParameter("facultyId", facultyId);
        query.setParameter("sessionId", sessionId);
        query.setParameter("subjectId", subjectId);
        return query.getResultList();
    }

//    public AverageFeedbackBean getAverageFeedbackById() {
//        String queryString = "SELECT AVG(fb.attribute1) AS attribute_1,AVG(fb.attribute2) AS attribute_2,AVG(fb.attribute3) AS attribute_3,AVG(fb.attribute4) AS attribute_4,AVG(fb.attribute5) AS attribute_5,AVG(fb.attribute6) AS attribute_6,AVG(fb.attribute7) AS attribute_7,AVG(fb.attribute8) AS attribute_8,AVG(fb.attribute9) AS attribute_9,AVG(fb.attribute10) AS attribute_10  FROM feedback_table AS fb WHERE fb.csf_id = :id";
//        Query query = getEntityManager().createNativeQuery(queryString);
////        TypedQuery<AverageFeedback> query = getEntityManager().createNamedQuery("CsfBean.findAverageFeedbackById", AverageFeedbackBean.class);
//        query.setParameter("id", id);
//        AverageFeedbackBean avgFb = null;
//        try {
////            avgFb = query.getSingleResult();
//        } catch (NoResultException e) {
//            LogManager.log("Feedback not found\nId:" + this.id);
//        } catch (NonUniqueResultException e) {
//            LogManager.log("More than one Feedback details found\nId:" + this.id);
//        } catch (Exception e) {
//            LogManager.log("Unknown Exception in FeedbackBean::findById()\n" + e.getMessage());
//        }
////        avgFb.setCsfId(this);
//        return avgFb;
//    }
//
    public boolean deleteById(CsfBean csf) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            CsfBean removeCsf = em.find(CsfBean.class, csf.getId());
            em.remove(removeCsf);
            em.getTransaction().commit();
        } catch (Exception e) {
            LogManager.log("Exception in CsfBean::deleteById(): " + e.getMessage());
            LogManager.log(UtilsManager.beanAsJsonString(csf));
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
        LogManager.log("CSF deleted from db successfully!");
        LogManager.log(UtilsManager.beanAsJsonString(csf));
        return true;
    }
}
