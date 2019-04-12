package com.zkyong.demo.util.excel;

public class College {
    private Integer collegeId;
    private String collegeName;
    private School school;
    public Integer getCollegeId() {
        return collegeId;
    }
    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }
    public String getCollegeName() {
        return collegeName;
    }
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
    
    public School getSchool() {
        return school;
    }
    public void setSchool(School school) {
        this.school = school;
    }
    @Override
    public String toString() {
        return "College [collegeId=" + collegeId + ", collegeName=" + collegeName + ", school=" + school + "]";
    }
}
