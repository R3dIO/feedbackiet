/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sapan
 */
@Entity
@Table(name = "schedule_feedback_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScheduleFeedbackBean.findAll", query = "SELECT s FROM ScheduleFeedbackBean s")
    , @NamedQuery(name = "ScheduleFeedbackBean.findById", query = "SELECT s FROM ScheduleFeedbackBean s WHERE s.id = :id")
    , @NamedQuery(name = "ScheduleFeedbackBean.findByStartTime", query = "SELECT s FROM ScheduleFeedbackBean s WHERE s.startTime = :startTime")
    , @NamedQuery(name = "ScheduleFeedbackBean.findByForTime", query = "SELECT s FROM ScheduleFeedbackBean s WHERE s.forTime = :forTime")
    , @NamedQuery(name = "ScheduleFeedbackBean.findByPassword", query = "SELECT s FROM ScheduleFeedbackBean s WHERE s.password = :password")})
public class ScheduleFeedbackBean implements Serializable {

    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @OneToOne
    private ClassBean classId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "start_time")
    private int startTime;
    @Basic(optional = false)
    @Column(name = "for_time")
    private int forTime;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    public ScheduleFeedbackBean() {
    }

    public ScheduleFeedbackBean(Long id) {
        this.id = id;
    }

    public ScheduleFeedbackBean(Long id, int startTime, int forTime, String password) {
        this.id = id;
        this.startTime = startTime;
        this.forTime = forTime;
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

    public int getForTime() {
        return forTime;
    }

    public void setForTime(int forTime) {
        this.forTime = forTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        if (!(object instanceof ScheduleFeedbackBean)) {
            return false;
        }
        ScheduleFeedbackBean other = (ScheduleFeedbackBean) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.ScheduleFeedbackBean[ id=" + id + " ]";
    }

    public ClassBean getClassId() {
        return classId;
    }

    public void setClassId(ClassBean classId) {
        this.classId = classId;
    }
    
}
