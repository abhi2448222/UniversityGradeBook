/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.assign2.crud.gradebook.arao23.srv.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Abhishek
 */
@XmlRootElement(name = "GradeItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class GradeItem {
    @XmlElement(name = "gradeItemName")
    private String gradeItemName;
    @XmlElement(name = "percentageAllocation")
    private int percentageAllocation;
     @XmlElement(name = "marksObtained")
    private int marksObtained;
     @XmlElement(name = "feedback")
     private String feedback;
     @XmlElement(name = "appeal")
     private String appeal;

    public String getAppeal() {
        return appeal;
    }

    public void setAppeal(String appeal) {
        this.appeal = appeal;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }


    public GradeItem() {
    }

    public GradeItem(String gradeItemName, int percentageAllocation, int marksObtained,String feedback) {
        this.gradeItemName = gradeItemName;
        this.percentageAllocation = percentageAllocation;
        this.marksObtained = marksObtained;
        this.feedback=feedback;
    }
    

    public String getGradeItemName() {
        return gradeItemName;
    }

    public void setGradeItemName(String gradeItemName) {
        this.gradeItemName = gradeItemName;
    }

    public int getPercentageAllocation() {
        return percentageAllocation;
    }

    public void setPercentageAllocation(int percentageAllocation) {
        this.percentageAllocation = percentageAllocation;
    }

    public int getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(int marksObtained) {
        this.marksObtained = marksObtained;
    }
    
}
