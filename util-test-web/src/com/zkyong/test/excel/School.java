package com.zkyong.test.excel;

public class School {
    private String schoolId;
    private String schoolName;
    public String getSchoolId() {
        return schoolId;
    }
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
    public String getSchoolName() {
        return schoolName;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
    @Override
    public String toString() {
        return "School [schoolId=" + schoolId + ", schoolName=" + schoolName + "]";
    }
}
