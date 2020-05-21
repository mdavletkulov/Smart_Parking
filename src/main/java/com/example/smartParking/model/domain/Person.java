package com.example.smartParking.model.domain;

import com.example.smartParking.repos.AutomobileRepo;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "Поле имени не может быть пустым")
    @Length(max = 60, message = "Значение имени слишком длинное")
    private String firstName;
    @NotBlank(message = "Поле фамилии не может быть пустым")
    @Length(max = 60, message = "Значение фамилии слишком длинное")
    private String secondName;
    @Length(max = 60, message = "Значение отчества слишком длинное")
    private String middleName;
    @ManyToOne
    @JoinColumn(name = "job_position")
    private JobPosition jobPosition;
    @ManyToOne
    @JoinColumn(name = "subdivision")
    private Subdivision subdivision;
    @ManyToOne
    @JoinColumn(name = "division")
    private Division division;
    @NotBlank
    private boolean employee;
    @NotBlank
    private boolean student;
    private Integer course;
    @Length(max = 10, message = "Значение группы слишком длинное")
    private String groupName;
    private Integer passNum;
    private Date passEndDate;
    @NotBlank
    private boolean specialStatus;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public JobPosition getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(JobPosition jobPosition) {
        this.jobPosition = jobPosition;
    }

    public Subdivision getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Subdivision subdivision) {
        this.subdivision = subdivision;
    }

    public boolean isEmployee() {
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getPassNum() {
        if (getPassEndDate() == null || getPassEndDate().getTime() < new Date().getTime()) {
            passNum = null;
            return null;
        }
        return passNum;
    }

    public void setPassNum(Integer passNum) {
        this.passNum = passNum;
    }

    public Date getPassEndDate() {
        return passEndDate;
    }

    public void setPassEndDate(Date passEndDate) {
        this.passEndDate = passEndDate;
    }

    public boolean isSpecialStatus() {
        return specialStatus;
    }

    public void setSpecialStatus(boolean specialStatus) {
        this.specialStatus = specialStatus;
    }

    public String getFullName() {
        return secondName + " " + firstName + " " + middleName;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }
}
