/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Sapan
 */
public class SectionCFeedback implements Serializable {

    private ArrayList<String> comment1;
    private ArrayList<String> comment2;
    private ArrayList<String> comment3;

    public SectionCFeedback() {
        comment1 = new ArrayList<>();
        comment2 = new ArrayList<>();
        comment3 = new ArrayList<>();
    }

    public SectionCFeedback(ArrayList<String> comment1, ArrayList<String> comment2, ArrayList<String> comment3) {
        this.comment1 = comment1;
        this.comment2 = comment2;
        this.comment3 = comment3;
    }

    @Override
    public String toString() {
        return "SectionCFeedback{" + "comment1=" + comment1 + ", comment2=" + comment2 + ", comment3=" + comment3 + '}';
    }

    public ArrayList<String> getComment1() {
        return comment1;
    }

    public void setComment1(ArrayList<String> comment1) {
        this.comment1 = comment1;
    }

    public void setComment1(String comment1) {
        this.comment1.add(comment1);
    }

    public ArrayList<String> getComment2() {
        return comment2;
    }

    public void setComment2(ArrayList<String> comment2) {
        this.comment2 = comment2;
    }

    public void setComment2(String comment2) {
        this.comment2.add(comment2);
    }

    public ArrayList<String> getComment3() {
        return comment3;
    }

    public void setComment3(ArrayList<String> comment3) {
        this.comment3 = comment3;
    }

    public void setComment3(String comment3) {
        this.comment3.add(comment3);
    }

}
