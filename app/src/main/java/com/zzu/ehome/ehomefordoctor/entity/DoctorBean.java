package com.zzu.ehome.ehomefordoctor.entity;

/**
 * Created by Mersens on 2016/10/10.
 */

public class DoctorBean {
    /**
     * DoctorID : 10
     * Mobile : 15238319621
     * ClientID :
     * DoctorName : 贾玲
     * HospitalName : 郑州大学第一附属医院
     * Title : 主任医师
     * ImageURL :
     * Department : 老年心血管科
     * GoodAt : 心血管
     * ApplyTo : 老年心血管疾病人群
     * Description :
     * Speciaty :
     */

    private String DoctorID;
    private String Mobile;
    private String ClientID;
    private String DoctorName;
    private String HospitalName;
    private String Title;
    private String ImageURL;
    private String Department;
    private String GoodAt;
    private String ApplyTo;
    private String Description;
    private String Speciaty;

    public ResultBean getResultBean() {
        return resultBean;
    }

    public void setResultBean(ResultBean resultBean) {
        this.resultBean = resultBean;
    }

    private ResultBean resultBean;

    public String getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(String DoctorID) {
        this.DoctorID = DoctorID;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String ClientID) {
        this.ClientID = ClientID;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String DoctorName) {
        this.DoctorName = DoctorName;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String HospitalName) {
        this.HospitalName = HospitalName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String ImageURL) {
        this.ImageURL = ImageURL;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public String getGoodAt() {
        return GoodAt;
    }

    public void setGoodAt(String GoodAt) {
        this.GoodAt = GoodAt;
    }

    public String getApplyTo() {
        return ApplyTo;
    }

    public void setApplyTo(String ApplyTo) {
        this.ApplyTo = ApplyTo;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getSpeciaty() {
        return Speciaty;
    }

    public void setSpeciaty(String Speciaty) {
        this.Speciaty = Speciaty;
    }
}
