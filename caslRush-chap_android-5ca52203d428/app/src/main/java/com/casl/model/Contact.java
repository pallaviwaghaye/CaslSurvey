package com.casl.model;

public class Contact {

    /*"participantId":"3857292",
            "interviewId": 12,
            "langSpeakCan": "gd",
            "langSpeakPre": "gd",
            "langReadCan": "tw",
            "langReadPre": "tw",
            "langWriteCan": "tw",
            "langWritePre": "tw",
            "recruitment": "fg",
            "otherRecruit": "",
            "residence": "pr",
            "otherResidence": "",
            "socialGroup": "none",
            "otherGroup": null,
            "bornCountry": "chn",
            "otherCountry": "",
            "contactDate": "06/27/2019",
            "contactTime": "15:30",
            "contactType": "inper",
            "otherType": "",
            "purpose": "03",
            "otherPurpose": "",
            "schDate": "",
            "schTime": "",
            "partcont": "yes",
            "othrcontact": "no",
            "nopartcont": null,
            "othrincl": null,
            "othrinclresp": null,
            "death": "no",
            "dieDate": "",
            "dieCause": null,
            "nursHome": null,
            "diePlace": null,
            "otherDiePlace": "",
            "dieState": "IL",
            "reluctance": "no",
            "partrel1": null,
            "partrel2": null,
            "partrel3": "",
            "famrel1": null,
            "famrel2": null,
            "famrel3": "",
            "famrel4": null,
            "dieWhom": null,
            "dieComm": null,
            "formComm": null,
            "partcontadd": null,
            "intMethod": null,
            "tran_date": 1311359330143,
            "tran_user": "staff3"
    */
//set spinner data......................
//contact.setContactType(spinnerTypeofContact.);
//contact.setNopartcont(spinnerNoContact);
//contact.setPurpose(spinnerPurposeofContact);
//contact.setIntMethod(spinnerInterviewMethod);
//contact.setDieCause(spinnerCauseofDeath);
//contact.setDiePlace(spinnerWhereWhenDied);
//contact.setDieState(spinnerState);
//contact.setDieWhom(spinnerWithWhome);
//contact.setPartrel2(spinnerOpinion);
//contact.setFamrel2(spinnerExpressPerson);
    private String participantId = "";
    private int interviewId;
    private String contactDate = "";
    private String contactTime = "";
    private String contactType;//
    private String otherType = "";
    private String purpose;//
    private String otherPurpose = "";
    private String schDate = "";
    private String schTime = "";
    private String partcont = "";
    private String othrcontact = "";
    private String nopartcont;//
    private String othrincl = "";
    private String othrinclresp = "";
    private String death = "";
    private String dieDate = "";
    private String dieCause;//
    private String nursHome = "";
    private String diePlace;//
    private String otherDiePlace = "";
    private String dieState;//
    private String reluctance = "";
    private String partrel1 = "";
    private String partrel2;//
    private String partrel3 = "";
    private String famrel1 = "";
    private String famrel2;//
    private String famrel3 = "";
    private String famrel4 = "";
    private String dieWhom;//
    private String dieComm = "";
    private String formComm = "";
    private String partcontadd = "";
    private String intMethod;//
    private long tran_date;
    private String tran_user = "";



    boolean isUploaded = false;

    public Contact() {
    }

    public Contact(String participantId, int interviewId, String contactDate, String contactTime, String contactType, String otherType, String purpose, String otherPurpose, String schDate, String schTime, String partcont, String othrcontact, String nopartcont, String othrincl, String othrinclresp, String death, String dieDate, String dieCause, String nursHome, String diePlace, String otherDiePlace, String dieState, String reluctance, String partrel1, String partrel2, String partrel3, String famrel1, String famrel2, String famrel3, String famrel4, String dieWhom, String dieComm, String formComm, String partcontadd, String intMethod, long tran_date, String tran_user) {
        this.participantId = participantId;
        this.interviewId = interviewId;
        this.contactDate = contactDate;
        this.contactTime = contactTime;
        this.contactType = contactType;
        this.otherType = otherType;
        this.purpose = purpose;
        this.otherPurpose = otherPurpose;
        this.schDate = schDate;
        this.schTime = schTime;
        this.partcont = partcont;
        this.othrcontact = othrcontact;
        this.nopartcont = nopartcont;
        this.othrincl = othrincl;
        this.othrinclresp = othrinclresp;
        this.death = death;
        this.dieDate = dieDate;
        this.dieCause = dieCause;
        this.nursHome = nursHome;
        this.diePlace = diePlace;
        this.otherDiePlace = otherDiePlace;
        this.dieState = dieState;
        this.reluctance = reluctance;
        this.partrel1 = partrel1;
        this.partrel2 = partrel2;
        this.partrel3 = partrel3;
        this.famrel1 = famrel1;
        this.famrel2 = famrel2;
        this.famrel3 = famrel3;
        this.famrel4 = famrel4;
        this.dieWhom = dieWhom;
        this.dieComm = dieComm;
        this.formComm = formComm;
        this.partcontadd = partcontadd;
        this.intMethod = intMethod;
        this.tran_date = tran_date;
        this.tran_user = tran_user;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    public String getContactDate() {
        return contactDate;
    }

    public void setContactDate(String contactDate) {
        this.contactDate = contactDate;
    }

    public String getContactTime() {
        return contactTime;
    }

    public void setContactTime(String contactTime) {
        this.contactTime = contactTime;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getOtherType() {
        return otherType;
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getOtherPurpose() {
        return otherPurpose;
    }

    public void setOtherPurpose(String otherPurpose) {
        this.otherPurpose = otherPurpose;
    }

    public String getSchDate() {
        return schDate;
    }

    public void setSchDate(String schDate) {
        this.schDate = schDate;
    }

    public String getSchTime() {
        return schTime;
    }

    public void setSchTime(String schTime) {
        this.schTime = schTime;
    }

    public String getPartcont() {
        return partcont;
    }

    public void setPartcont(String partcont) {
        this.partcont = partcont;
    }

    public String getOthrcontact() {
        return othrcontact;
    }

    public void setOthrcontact(String othrcontact) {
        this.othrcontact = othrcontact;
    }

    public String getNopartcont() {
        return nopartcont;
    }

    public void setNopartcont(String nopartcont) {
        this.nopartcont = nopartcont;
    }

    public String getOthrincl() {
        return othrincl;
    }

    public void setOthrincl(String othrincl) {
        this.othrincl = othrincl;
    }

    public String getOthrinclresp() {
        return othrinclresp;
    }

    public void setOthrinclresp(String othrinclresp) {
        this.othrinclresp = othrinclresp;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getDieDate() {
        return dieDate;
    }

    public void setDieDate(String dieDate) {
        this.dieDate = dieDate;
    }

    public String getDieCause() {
        return dieCause;
    }

    public void setDieCause(String dieCause) {
        this.dieCause = dieCause;
    }

    public String getNursHome() {
        return nursHome;
    }

    public void setNursHome(String nursHome) {
        this.nursHome = nursHome;
    }

    public String getDiePlace() {
        return diePlace;
    }

    public void setDiePlace(String diePlace) {
        this.diePlace = diePlace;
    }

    public String getOtherDiePlace() {
        return otherDiePlace;
    }

    public void setOtherDiePlace(String otherDiePlace) {
        this.otherDiePlace = otherDiePlace;
    }

    public String getDieState() {
        return dieState;
    }

    public void setDieState(String dieState) {
        this.dieState = dieState;
    }

    public String getReluctance() {
        return reluctance;
    }

    public void setReluctance(String reluctance) {
        this.reluctance = reluctance;
    }

    public String getPartrel1() {
        return partrel1;
    }

    public void setPartrel1(String partrel1) {
        this.partrel1 = partrel1;
    }

    public String getPartrel2() {
        return partrel2;
    }

    public void setPartrel2(String partrel2) {
        this.partrel2 = partrel2;
    }

    public String getPartrel3() {
        return partrel3;
    }

    public void setPartrel3(String partrel3) {
        this.partrel3 = partrel3;
    }

    public String getFamrel1() {
        return famrel1;
    }

    public void setFamrel1(String famrel1) {
        this.famrel1 = famrel1;
    }

    public String getFamrel2() {
        return famrel2;
    }

    public void setFamrel2(String famrel2) {
        this.famrel2 = famrel2;
    }

    public String getFamrel3() {
        return famrel3;
    }

    public void setFamrel3(String famrel3) {
        this.famrel3 = famrel3;
    }

    public String getFamrel4() {
        return famrel4;
    }

    public void setFamrel4(String famrel4) {
        this.famrel4 = famrel4;
    }

    public String getDieWhom() {
        return dieWhom;
    }

    public void setDieWhom(String dieWhom) {
        this.dieWhom = dieWhom;
    }

    public String getDieComm() {
        return dieComm;
    }

    public void setDieComm(String dieComm) {
        this.dieComm = dieComm;
    }

    public String getFormComm() {
        return formComm;
    }

    public void setFormComm(String formComm) {
        this.formComm = formComm;
    }

    public String getPartcontadd() {
        return partcontadd;
    }

    public void setPartcontadd(String partcontadd) {
        this.partcontadd = partcontadd;
    }

    public String getIntMethod() {
        return intMethod;
    }

    public void setIntMethod(String intMethod) {
        this.intMethod = intMethod;
    }

    public long getTran_date() {
        return tran_date;
    }

    public void setTran_date(long tran_date) {
        this.tran_date = tran_date;
    }

    public String getTran_user() {
        return tran_user;
    }

    public void setTran_user(String tran_user) {
        this.tran_user = tran_user;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }
}
