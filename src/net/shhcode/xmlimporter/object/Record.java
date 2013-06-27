package net.shhcode.xmlimporter.object;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Record {

    private String patientId;
    private String site;
    private String gender;
    private String age;
    private String vitalStatus;

    public String getPatientId() {
        return patientId;
    }

    @XmlAttribute
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getSite() {
        return site;
    }

    @XmlElement
    public void setSite(String site) {
        this.site = site;
    }

    public String getGender() {
        return gender;
    }

    @XmlElement
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    @XmlElement
    public void setAge(String age) {
        this.age = age;
    }

    public String getVitalStatus() {
        return vitalStatus;
    }

    @XmlElement
    public void setVitalStatus(String vitalStatus) {
        this.vitalStatus = vitalStatus;
    }

}

