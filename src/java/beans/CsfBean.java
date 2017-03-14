/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sapan
 */
@Entity
@Table(name = "csf_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CsfBean.findAll", query = "SELECT c FROM CsfBean c")
    , @NamedQuery(name = "CsfBean.findById", query = "SELECT c FROM CsfBean c WHERE c.id = :id")})
public class CsfBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ClassBean classId;
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FacultyBean facultyId;
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SessionBean sessionId;
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
    
}
