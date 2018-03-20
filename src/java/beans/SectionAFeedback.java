/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;

/**
 *
 * @author Sapan
 */
public class SectionAFeedback implements Serializable {

    private Double attribute1;
    private Double attribute2;
    private Double attribute3;
    private Double attribute4;
    private Double attribute5;
    private Double attribute6;
    private Double attribute7;
    private Double attribute8;
    private Double attribute9;

    public SectionAFeedback(Double attribute1, Double attribute2, Double attribute3, Double attribute4, Double attribute5, Double attribute6, Double attribute7, Double attribute8, Double attribute9, Double attribute10) {
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
        final SectionAFeedback other = (SectionAFeedback) obj;
        if (this.attribute1 != other.attribute1) {
            return false;
        }
        if (this.attribute2 != other.attribute2) {
            return false;
        }
        if (this.attribute3 != other.attribute3) {
            return false;
        }
        if (this.attribute4 != other.attribute4) {
            return false;
        }
        if (this.attribute5 != other.attribute5) {
            return false;
        }
        if (this.attribute6 != other.attribute6) {
            return false;
        }
        if (this.attribute7 != other.attribute7) {
            return false;
        }
        if (this.attribute8 != other.attribute8) {
            return false;
        }
        if (this.attribute9 != other.attribute9) {
            return false;
        }
        if (this.attribute10 != other.attribute10) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.attribute1);
        hash = 37 * hash + Objects.hashCode(this.attribute2);
        hash = 37 * hash + Objects.hashCode(this.attribute3);
        hash = 37 * hash + Objects.hashCode(this.attribute4);
        hash = 37 * hash + Objects.hashCode(this.attribute5);
        hash = 37 * hash + Objects.hashCode(this.attribute6);
        hash = 37 * hash + Objects.hashCode(this.attribute7);
        hash = 37 * hash + Objects.hashCode(this.attribute8);
        hash = 37 * hash + Objects.hashCode(this.attribute9);
        hash = 37 * hash + Objects.hashCode(this.attribute10);
        return hash;
    }

    @Override
    public String toString() {
        return "SectionAFeedback{" + "attribute1=" + attribute1 + ", attribute2=" + attribute2 + ", attribute3=" + attribute3 + ", attribute4=" + attribute4 + ", attribute5=" + attribute5 + ", attribute6=" + attribute6 + ", attribute7=" + attribute7 + ", attribute8=" + attribute8 + ", attribute9=" + attribute9 + ", attribute10=" + attribute10 + '}';
    }

    public Double getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(Double attribute1) {
        this.attribute1 = attribute1;
    }

    public Double getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(Double attribute2) {
        this.attribute2 = attribute2;
    }

    public Double getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(Double attribute3) {
        this.attribute3 = attribute3;
    }

    public Double getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(Double attribute4) {
        this.attribute4 = attribute4;
    }

    public Double getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(Double attribute5) {
        this.attribute5 = attribute5;
    }

    public Double getAttribute6() {
        return attribute6;
    }

    public void setAttribute6(Double attribute6) {
        this.attribute6 = attribute6;
    }

    public Double getAttribute7() {
        return attribute7;
    }

    public void setAttribute7(Double attribute7) {
        this.attribute7 = attribute7;
    }

    public Double getAttribute8() {
        return attribute8;
    }

    public void setAttribute8(Double attribute8) {
        this.attribute8 = attribute8;
    }

    public Double getAttribute9() {
        return attribute9;
    }

    public void setAttribute9(Double attribute9) {
        this.attribute9 = attribute9;
    }

    public Double getAttribute10() {
        return attribute10;
    }

    public void setAttribute10(Double attribute10) {
        this.attribute10 = attribute10;
    }
    private Double attribute10;
    
    public Double[] getAttributesAsArray(){
        Double[] array = new Double[10];
        int i=0;
        array[i++]= attribute1;
        array[i++]= attribute2;
        array[i++]= attribute3;
        array[i++]= attribute4;
        array[i++]= attribute5;
        array[i++]= attribute6;
        array[i++]= attribute7;
        array[i++]= attribute8;
        array[i++]= attribute9;
        array[i++]= attribute10;
        return array;
    }
    
}
