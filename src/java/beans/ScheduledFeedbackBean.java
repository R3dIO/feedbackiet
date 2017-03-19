package beans;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.OneToOne;
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
@Table(name = "scheduled_feedback_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScheduledFeedbackBean.findAll", query = "SELECT s FROM ScheduledFeedbackBean s")
    , @NamedQuery(name = "ScheduledFeedbackBean.findById", query = "SELECT s FROM ScheduledFeedbackBean s WHERE s.id = :id")
    , @NamedQuery(name = "ScheduledFeedbackBean.findByStartTime", query = "SELECT s FROM ScheduledFeedbackBean s WHERE s.startTime = :startTime")
    , @NamedQuery(name = "ScheduledFeedbackBean.findByEndTime", query = "SELECT s FROM ScheduledFeedbackBean s WHERE s.endTime = :endTime")
    , @NamedQuery(name = "ScheduledFeedbackBean.findByPassword", query = "SELECT s FROM ScheduledFeedbackBean s WHERE s.password = :password")
    , @NamedQuery(name = "ScheduledFeedbackBean.findByClassId", query = "SELECT s FROM ScheduledFeedbackBean s WHERE s.classId = :classId")
    , @NamedQuery(name = "ScheduledFeedbackBean.findAllFeedbackEligibleClass", query = "SELECT s FROM ScheduledFeedbackBean s WHERE s.startTime <= :currentTime and s.endTime > :currentTime")
    , @NamedQuery(name = "ScheduledFeedbackBean.findByClassIdAndPassword", query = "SELECT s FROM ScheduledFeedbackBean s WHERE s.classId = :classId AND s.password = :password")})
public class ScheduledFeedbackBean extends Bean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "start_time")
    private int startTime;//in seconds
    @Basic(optional = false)
    @Column(name = "end_time")
    private int endTime;//in seconds
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @OneToOne
    private ClassBean classId;

    public ScheduledFeedbackBean() {
    }

    public ScheduledFeedbackBean(Long id) {
        this.id = id;
    }

    public ScheduledFeedbackBean(Long id, int startTime, int endTime, String password) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClassBean getClassId() {
        return classId;
    }

    public void setClassId(ClassBean classId) {
        this.classId = classId;
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
        if (!(object instanceof ScheduledFeedbackBean)) {
            return false;
        }
        ScheduledFeedbackBean other = (ScheduledFeedbackBean) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.ScheduledFeedbackBean[ id=" + id + " ]";
    }

    private boolean persist(Object object) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
        return true;
    }

    public boolean scheduleClassFeedback() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<ScheduledFeedbackBean> query = em.createNamedQuery("ScheduledFeedbackBean.findByClassId", ScheduledFeedbackBean.class);
            query.setParameter("classId", classId);
            ScheduledFeedbackBean sfb = null;
            sfb = query.getSingleResult();
            //Found record...update the existing one
            this.setId(sfb.getId());
            em.getTransaction().begin();
            em.merge(this);
            em.getTransaction().commit();
        } catch (NoResultException e) {
            //No Record Found...Can add new feedback
            persist(this);
        } finally {
            em.close();
        }
        return true;
    }

    public boolean deleteFeedbackScheduleOfClass() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<ScheduledFeedbackBean> query = em.createNamedQuery("ScheduledFeedbackBean.findByClassId", ScheduledFeedbackBean.class);
            query.setParameter("classId", classId);
            ScheduledFeedbackBean sfb = null;
            sfb = query.getSingleResult();
            //Found record...delete the existing one
            em.getTransaction().begin();
            em.remove(sfb);
            em.getTransaction().commit();
        } catch (NoResultException e) {
            //No Record Found...
        } catch (Exception e) {
            LogManager.log("Exception in " + ScheduledFeedbackBean.class.getName() + "deleteFeedbackScheduleOfClass(): " + e);
            return false;
        } finally {
            em.close();
        }
        return true;
    }

    public List<ScheduledFeedbackBean> findAll() {
        EntityManager em = getEntityManager();
        TypedQuery<ScheduledFeedbackBean> query = em.createNamedQuery("ScheduledFeedbackBean.findAll", ScheduledFeedbackBean.class);
        return query.getResultList();
    }

    public List<ScheduledFeedbackBean> findAllFeedbackEligibleClass(int currentTime) {
        EntityManager em = getEntityManager();
        TypedQuery<ScheduledFeedbackBean> query = em.createNamedQuery("ScheduledFeedbackBean.findAllFeedbackEligibleClass", ScheduledFeedbackBean.class);
        query.setParameter("currentTime", currentTime);
        return query.getResultList();
    }

    public ScheduledFeedbackBean findByClassIdAndPassword() {
        TypedQuery<ScheduledFeedbackBean> query = getEntityManager().createNamedQuery("ScheduledFeedbackBean.findByClassIdAndPassword", ScheduledFeedbackBean.class);
        query.setParameter("classId", classId);
        query.setParameter("password", password);
        ScheduledFeedbackBean sfb = null;
        try {
            sfb = query.getSingleResult();
        } catch (NoResultException e) {
            LogManager.log("No result found (ScheduledFeedbackBean:findByClassIdAndPassword()) for classId " + classId + " and password " + password + " " + e);
            return null;
        } catch (NonUniqueResultException e) {
            LogManager.log("More than one result found (ScheduledFeedbackBean:findByClassIdAndPassword()) for classId " + classId + " and password " + password + " " + e);
            return null;
        }
        return sfb;
    }
}
