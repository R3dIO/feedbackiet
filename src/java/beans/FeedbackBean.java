/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.google.gson.annotations.Expose;
import com.mysql.jdbc.StringUtils;
import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import managers.LogManager;
import org.apache.commons.lang3.StringEscapeUtils;
import sun.net.util.IPAddressUtil;

/**
 *
 * @author Sapan
 */
@Entity
@Table(name = "feedback_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FeedbackBean.findAll", query = "SELECT f FROM FeedbackBean f")
    , @NamedQuery(name = "FeedbackBean.findById", query = "SELECT f FROM FeedbackBean f WHERE f.id = :id")
    , @NamedQuery(name = "FeedbackBean.findByTimestamp", query = "SELECT f FROM FeedbackBean f WHERE f.timestamp = :timestamp")
    , @NamedQuery(name = "FeedbackBean.findByIpAddress", query = "SELECT f FROM FeedbackBean f WHERE f.ipAddress = :ipAddress")
    , @NamedQuery(name = "FeedbackBean.findByAttribute1", query = "SELECT f FROM FeedbackBean f WHERE f.attribute1 = :attribute1")
    , @NamedQuery(name = "FeedbackBean.findByAttribute2", query = "SELECT f FROM FeedbackBean f WHERE f.attribute2 = :attribute2")
    , @NamedQuery(name = "FeedbackBean.findByAttribute3", query = "SELECT f FROM FeedbackBean f WHERE f.attribute3 = :attribute3")
    , @NamedQuery(name = "FeedbackBean.findByAttribute4", query = "SELECT f FROM FeedbackBean f WHERE f.attribute4 = :attribute4")
    , @NamedQuery(name = "FeedbackBean.findByAttribute5", query = "SELECT f FROM FeedbackBean f WHERE f.attribute5 = :attribute5")
    , @NamedQuery(name = "FeedbackBean.findByAttribute6", query = "SELECT f FROM FeedbackBean f WHERE f.attribute6 = :attribute6")
    , @NamedQuery(name = "FeedbackBean.findByAttribute7", query = "SELECT f FROM FeedbackBean f WHERE f.attribute7 = :attribute7")
    , @NamedQuery(name = "FeedbackBean.findByAttribute8", query = "SELECT f FROM FeedbackBean f WHERE f.attribute8 = :attribute8")
    , @NamedQuery(name = "FeedbackBean.findByAttribute9", query = "SELECT f FROM FeedbackBean f WHERE f.attribute9 = :attribute9")
    , @NamedQuery(name = "FeedbackBean.findByAttribute10", query = "SELECT f FROM FeedbackBean f WHERE f.attribute10 = :attribute10")
    , @NamedQuery(name = "FeedbackBean.findByAttribute11", query = "SELECT f FROM FeedbackBean f WHERE f.attribute11 = :attribute11")
    , @NamedQuery(name = "FeedbackBean.findByAttribute12", query = "SELECT f FROM FeedbackBean f WHERE f.attribute12 = :attribute12")
    , @NamedQuery(name = "FeedbackBean.findByAttribute13", query = "SELECT f FROM FeedbackBean f WHERE f.attribute13 = :attribute13")
    , @NamedQuery(name = "FeedbackBean.findByAttribute14", query = "SELECT f FROM FeedbackBean f WHERE f.attribute14 = :attribute14")
    , @NamedQuery(name = "FeedbackBean.findByAttribute15", query = "SELECT f FROM FeedbackBean f WHERE f.attribute15 = :attribute15")
    , @NamedQuery(name = "FeedbackBean.findByComment1", query = "SELECT f FROM FeedbackBean f WHERE f.comment1 = :comment1")
    , @NamedQuery(name = "FeedbackBean.findByComment2", query = "SELECT f FROM FeedbackBean f WHERE f.comment2 = :comment2")
    , @NamedQuery(name = "FeedbackBean.findByComment3", query = "SELECT f FROM FeedbackBean f WHERE f.comment3 = :comment3")})
public class FeedbackBean extends Bean implements Serializable {

    private static final long serialVersionUID = 1L;
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Expose
    @Basic(optional = false)
    @Column(name = "timestamp")
    private int timestamp;
    @Expose
    @Column(name = "ip_address")
    private String ipAddress;
    @Expose
    @Basic(optional = false)
    @Column(name = "attribute_1")
    private int attribute1;
    @Expose
    @Basic(optional = false)
    @Column(name = "attribute_2")
    private int attribute2;
    @Expose
    @Basic(optional = false)
    @Column(name = "attribute_3")
    private int attribute3;
    @Expose
    @Basic(optional = false)
    @Column(name = "attribute_4")
    private int attribute4;
    @Expose
    @Basic(optional = false)
    @Column(name = "attribute_5")
    private int attribute5;
    @Expose
    @Basic(optional = false)
    @Column(name = "attribute_6")
    private int attribute6;
    @Expose
    @Basic(optional = false)
    @Column(name = "attribute_7")
    private int attribute7;
    @Expose
    @Basic(optional = false)
    @Column(name = "attribute_8")
    private int attribute8;
    @Expose
    @Basic(optional = false)
    @Column(name = "attribute_9")
    private int attribute9;
    @Expose
    @Basic(optional = false)
    @Column(name = "attribute_10")
    private int attribute10;
    @Expose
    @Basic(optional = false)
    @Column(name = "attribute_11")
    private int attribute11;
    @Expose
    @Basic(optional = false)
    @Column(name = "attribute_12")
    private int attribute12;
    @Expose
    @Basic(optional = false)
    @Column(name = "attribute_13")
    private int attribute13;
    @Expose
    @Basic(optional = false)
    @Column(name = "attribute_14")
    private int attribute14;
    @Expose
    @Basic(optional = false)
    @Column(name = "attribute_15")
    private int attribute15;
    @Expose
    @Basic(optional = false)
    @Column(name = "comment_1")
    private String comment1;
    @Expose
    @Basic(optional = false)
    @Column(name = "comment_2")
    private String comment2;
    @Expose
    @Basic(optional = false)
    @Column(name = "comment_3")
    private String comment3;
    @JoinColumn(name = "csf_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CsfBean csfId;

    public FeedbackBean() {
    }

    public FeedbackBean(Long id) {
        this.id = id;
    }

    public FeedbackBean(Long id, int timestamp, int attribute1, int attribute2, int attribute3, int attribute4, int attribute5, int attribute6, int attribute7, int attribute8, int attribute9, int attribute10, int attribute11, int attribute12, int attribute13, int attribute14, int attribute15, String comment1, String comment2, String comment3) {
        this.id = id;
        this.timestamp = timestamp;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
        this.attribute4 = attribute4;
        this.attribute5 = attribute5;
        this.attribute6 = attribute6;
        this.attribute7 = attribute7;
        this.attribute8 = attribute8;
        this.attribute9 = attribute9;
        this.attribute10 = attribute10;
        this.attribute11 = attribute11;
        this.attribute12 = attribute12;
        this.attribute13 = attribute13;
        this.attribute14 = attribute14;
        this.attribute15 = attribute15;
        this.comment1 = comment1;
        this.comment2 = comment2;
        this.comment3 = comment3;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(int attribute1) {
        this.attribute1 = attribute1;
    }

    public int getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(int attribute2) {
        this.attribute2 = attribute2;
    }

    public int getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(int attribute3) {
        this.attribute3 = attribute3;
    }

    public int getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(int attribute4) {
        this.attribute4 = attribute4;
    }

    public int getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(int attribute5) {
        this.attribute5 = attribute5;
    }

    public int getAttribute6() {
        return attribute6;
    }

    public void setAttribute6(int attribute6) {
        this.attribute6 = attribute6;
    }

    public int getAttribute7() {
        return attribute7;
    }

    public void setAttribute7(int attribute7) {
        this.attribute7 = attribute7;
    }

    public int getAttribute8() {
        return attribute8;
    }

    public void setAttribute8(int attribute8) {
        this.attribute8 = attribute8;
    }

    public int getAttribute9() {
        return attribute9;
    }

    public void setAttribute9(int attribute9) {
        this.attribute9 = attribute9;
    }

    public int getAttribute10() {
        return attribute10;
    }

    public void setAttribute10(int attribute10) {
        this.attribute10 = attribute10;
    }

    public int getAttribute11() {
        return attribute11;
    }

    public void setAttribute11(int attribute11) {
        this.attribute11 = attribute11;
    }

    public int getAttribute12() {
        return attribute12;
    }

    public void setAttribute12(int attribute12) {
        this.attribute12 = attribute12;
    }

    public int getAttribute13() {
        return attribute13;
    }

    public void setAttribute13(int attribute13) {
        this.attribute13 = attribute13;
    }

    public int getAttribute14() {
        return attribute14;
    }

    public void setAttribute14(int attribute14) {
        this.attribute14 = attribute14;
    }

    public int getAttribute15() {
        return attribute15;
    }

    public void setAttribute15(int attribute15) {
        this.attribute15 = attribute15;
    }

    public String getComment1() {
        return comment1;
    }

    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    public String getComment2() {
        return comment2;
    }

    public void setComment2(String comment2) {
        this.comment2 = comment2;
    }

    public String getComment3() {
        return comment3;
    }

    public void setComment3(String comment3) {
        this.comment3 = comment3;
    }

    public CsfBean getCsfId() {
        return csfId;
    }

    public void setCsfId(CsfBean csfId) {
        this.csfId = csfId;
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
        if (!(object instanceof FeedbackBean)) {
            return false;
        }
        FeedbackBean other = (FeedbackBean) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beans.FeedbackBean[ id=" + id + " ]";
    }

    public boolean persist(Object object) {
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
        LogManager.log("New feedback added to db successfully!");
        return true;
    }

    public boolean validateFields() {
        if (attribute1 < 1 || attribute1 > 5) {
            return false;
        }
        if (attribute2 < 1 || attribute2 > 5) {
            return false;
        }
        if (attribute3 < 1 || attribute3 > 5) {
            return false;
        }
        if (attribute4 < 1 || attribute4 > 5) {
            return false;
        }
        if (attribute5 < 1 || attribute5 > 5) {
            return false;
        }
        if (attribute6 < 1 || attribute6 > 5) {
            return false;
        }
        if (attribute7 < 1 || attribute7 > 5) {
            return false;
        }
        if (attribute8 < 1 || attribute8 > 5) {
            return false;
        }
        if (attribute9 < 1 || attribute9 > 5) {
            return false;
        }
        if (attribute10 < 1 || attribute10 > 5) {
            return false;
        }
        if (attribute11 < 1 || attribute11 > 3) {
            return false;
        }
        if (attribute12 < 1 || attribute12 > 3) {
            return false;
        }
        if (attribute13 < 1 || attribute13 > 3) {
            return false;
        }
        if (attribute14 < 1 || attribute14 > 3) {
            return false;
        }
        if (attribute15 < 1 || attribute15 > 3) {
            return false;
        }
        if (comment1.length() > 300) {
            return false;
        }
        if (comment2.length() > 300) {
            return false;
        }
        if (comment3.length() > 300) {
            return false;
        }
        LogManager.log("Feedback validated successfully!");
        return true;
    }

}
