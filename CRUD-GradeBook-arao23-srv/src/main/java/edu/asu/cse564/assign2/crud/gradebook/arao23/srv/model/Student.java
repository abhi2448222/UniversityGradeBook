/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.assign2.crud.gradebook.arao23.srv.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Abhishek
 */

@XmlRootElement(name = "Student")
@XmlAccessorType(XmlAccessType.FIELD)
public class Student {

  @XmlElement(name = "id")
  private int id;
  @XmlElement(name = "gradeitems")
  private List<GradeItem> gradeitems;

    public Student() {
    }

    public Student(int id, List<GradeItem> gradeitems) {
        this.id = id;
        this.gradeitems = gradeitems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<GradeItem> getGradeitems() {
        return gradeitems;
    }

    public void setGradeitems(List<GradeItem> gradeitems) {
        this.gradeitems = gradeitems;
    }
   
}
