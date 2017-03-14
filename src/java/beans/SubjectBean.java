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
@Table(name = "subject_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubjectBean.findAll", query = "SELECT s FROM SubjectBean s")
    , @NamedQuery(name = "SubjectBean.findById", query = "SELECT s FROM SubjectBean s WHERE s.id = :id")
    , @NamedQuery(name = "SubjectBean.findBySubjectCode", query = "SELECT s FROM SubjectBean s WHERE s.subjectCode = :subjectCode")
    , @NamedQuery(name = "SubjectBean.findBySubjectName", query = "SELECT s FROM SubjectBean s WHERE s.subjectName = :subjectName")})
public class SubjectBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "subject_code")
    private String subjectCode;
    @Basic(optional = false)
    @Column(name = "subject_name")
    private String subjectName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subjectId")
    private Collection<CsfBean> csfBeanCollection;

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
    
}
