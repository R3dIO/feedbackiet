/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Sapan
 */
public class SectionBFeedback implements Serializable {

    private Long attribute11Yes;
    private Long attribute12Yes;
    private Long attribute13Yes;
    private Long attribute14Yes;
    private Long attribute15Yes;
    private Long attribute11No;
    private Long attribute12No;
    private Long attribute13No;
    private Long attribute14No;
    private Long attribute15No;
    private Long attribute11NoComment;
    private Long attribute12NoComment;
    private Long attribute13NoComment;
    private Long attribute14NoComment;
    private Long attribute15NoComment;

    public Long getAttribute11Yes() {
        return attribute11Yes;
    }

    public SectionBFeedback() {
    }

    public SectionBFeedback(Long attribute11Yes, Long attribute12Yes, Long attribute13Yes, Long attribute14Yes, Long attribute15Yes, Long attribute11No, Long attribute12No, Long attribute13No, Long attribute14No, Long attribute15No, Long attribute11NoComment, Long attribute12NoComment, Long attribute13NoComment, Long attribute14NoComment, Long attribute15NoComment) {
        this.attribute11Yes = attribute11Yes;
        this.attribute12Yes = attribute12Yes;
        this.attribute13Yes = attribute13Yes;
        this.attribute14Yes = attribute14Yes;
        this.attribute15Yes = attribute15Yes;
        this.attribute11No = attribute11No;
        this.attribute12No = attribute12No;
        this.attribute13No = attribute13No;
        this.attribute14No = attribute14No;
        this.attribute15No = attribute15No;
        this.attribute11NoComment = attribute11NoComment;
        this.attribute12NoComment = attribute12NoComment;
        this.attribute13NoComment = attribute13NoComment;
        this.attribute14NoComment = attribute14NoComment;
        this.attribute15NoComment = attribute15NoComment;
    }

    @Override
    public String toString() {
        return "SectionBFeedback{" + "attribute11Yes=" + attribute11Yes + ", attribute12Yes=" + attribute12Yes + ", attribute13Yes=" + attribute13Yes + ", attribute14Yes=" + attribute14Yes + ", attribute15Yes=" + attribute15Yes + ", attribute11No=" + attribute11No + ", attribute12No=" + attribute12No + ", attribute13No=" + attribute13No + ", attribute14No=" + attribute14No + ", attribute15No=" + attribute15No + ", attribute11NoComment=" + attribute11NoComment + ", attribute12NoComment=" + attribute12NoComment + ", attribute13NoComment=" + attribute13NoComment + ", attribute14NoComment=" + attribute14NoComment + ", attribute15NoComment=" + attribute15NoComment + '}';
    }

    public void setAttribute11Yes(Long attribute11Yes) {
        this.attribute11Yes = attribute11Yes;
    }

    public Long getAttribute12Yes() {
        return attribute12Yes;
    }

    public void setAttribute12Yes(Long attribute12Yes) {
        this.attribute12Yes = attribute12Yes;
    }

    public Long getAttribute13Yes() {
        return attribute13Yes;
    }

    public void setAttribute13Yes(Long attribute13Yes) {
        this.attribute13Yes = attribute13Yes;
    }

    public Long getAttribute14Yes() {
        return attribute14Yes;
    }

    public void setAttribute14Yes(Long attribute14Yes) {
        this.attribute14Yes = attribute14Yes;
    }

    public Long getAttribute15Yes() {
        return attribute15Yes;
    }

    public void setAttribute15Yes(Long attribute15Yes) {
        this.attribute15Yes = attribute15Yes;
    }

    public Long getAttribute11No() {
        return attribute11No;
    }

    public void setAttribute11No(Long attribute11No) {
        this.attribute11No = attribute11No;
    }

    public Long getAttribute12No() {
        return attribute12No;
    }

    public void setAttribute12No(Long attribute12No) {
        this.attribute12No = attribute12No;
    }

    public Long getAttribute13No() {
        return attribute13No;
    }

    public void setAttribute13No(Long attribute13No) {
        this.attribute13No = attribute13No;
    }

    public Long getAttribute14No() {
        return attribute14No;
    }

    public void setAttribute14No(Long attribute14No) {
        this.attribute14No = attribute14No;
    }

    public Long getAttribute15No() {
        return attribute15No;
    }

    public void setAttribute15No(Long attribute15No) {
        this.attribute15No = attribute15No;
    }

    public Long getAttribute11NoComment() {
        return attribute11NoComment;
    }

    public void setAttribute11NoComment(Long attribute11NoComment) {
        this.attribute11NoComment = attribute11NoComment;
    }

    public Long getAttribute12NoComment() {
        return attribute12NoComment;
    }

    public void setAttribute12NoComment(Long attribute12NoComment) {
        this.attribute12NoComment = attribute12NoComment;
    }

    public Long getAttribute13NoComment() {
        return attribute13NoComment;
    }

    public void setAttribute13NoComment(Long attribute13NoComment) {
        this.attribute13NoComment = attribute13NoComment;
    }

    public Long getAttribute14NoComment() {
        return attribute14NoComment;
    }

    public void setAttribute14NoComment(Long attribute14NoComment) {
        this.attribute14NoComment = attribute14NoComment;
    }

    public Long getAttribute15NoComment() {
        return attribute15NoComment;
    }

//    public Long[] getAttributesAsArray() {
//        Long[] array = new Long[5];
//        int i = 0;
//        array[i++] = attribute11Yes;
//        array[i++] = attribute12Yes;
//        array[i++] = attribute13Yes;
//        array[i++] = attribute14Yes;
//        array[i++] = attribute15Yes;
//
//        return array;
//    }
    public void setAttribute15NoComment(Long attribute15NoComment) {
        this.attribute15NoComment = attribute15NoComment;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.attribute11Yes);
        hash = 67 * hash + Objects.hashCode(this.attribute12Yes);
        hash = 67 * hash + Objects.hashCode(this.attribute13Yes);
        hash = 67 * hash + Objects.hashCode(this.attribute14Yes);
        hash = 67 * hash + Objects.hashCode(this.attribute15Yes);
        hash = 67 * hash + Objects.hashCode(this.attribute11No);
        hash = 67 * hash + Objects.hashCode(this.attribute12No);
        hash = 67 * hash + Objects.hashCode(this.attribute13No);
        hash = 67 * hash + Objects.hashCode(this.attribute14No);
        hash = 67 * hash + Objects.hashCode(this.attribute15No);
        hash = 67 * hash + Objects.hashCode(this.attribute11NoComment);
        hash = 67 * hash + Objects.hashCode(this.attribute12NoComment);
        hash = 67 * hash + Objects.hashCode(this.attribute13NoComment);
        hash = 67 * hash + Objects.hashCode(this.attribute14NoComment);
        hash = 67 * hash + Objects.hashCode(this.attribute15NoComment);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SectionBFeedback other = (SectionBFeedback) obj;
        if (!Objects.equals(this.attribute11Yes, other.attribute11Yes)) {
            return false;
        }
        if (!Objects.equals(this.attribute12Yes, other.attribute12Yes)) {
            return false;
        }
        if (!Objects.equals(this.attribute13Yes, other.attribute13Yes)) {
            return false;
        }
        if (!Objects.equals(this.attribute14Yes, other.attribute14Yes)) {
            return false;
        }
        if (!Objects.equals(this.attribute15Yes, other.attribute15Yes)) {
            return false;
        }
        if (!Objects.equals(this.attribute11No, other.attribute11No)) {
            return false;
        }
        if (!Objects.equals(this.attribute12No, other.attribute12No)) {
            return false;
        }
        if (!Objects.equals(this.attribute13No, other.attribute13No)) {
            return false;
        }
        if (!Objects.equals(this.attribute14No, other.attribute14No)) {
            return false;
        }
        if (!Objects.equals(this.attribute15No, other.attribute15No)) {
            return false;
        }
        if (!Objects.equals(this.attribute11NoComment, other.attribute11NoComment)) {
            return false;
        }
        if (!Objects.equals(this.attribute12NoComment, other.attribute12NoComment)) {
            return false;
        }
        if (!Objects.equals(this.attribute13NoComment, other.attribute13NoComment)) {
            return false;
        }
        if (!Objects.equals(this.attribute14NoComment, other.attribute14NoComment)) {
            return false;
        }
        if (!Objects.equals(this.attribute15NoComment, other.attribute15NoComment)) {
            return false;
        }
        return true;
    }

}
