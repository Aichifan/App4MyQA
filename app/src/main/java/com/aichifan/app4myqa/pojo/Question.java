package com.aichifan.app4myqa.pojo;

import java.util.Date;
import java.util.List;

public class Question {
    private Integer id;
    
    private Date created;

    private Date modified;
    
    private String number;

    private String category;

    private String project;

    private String type;

    private String emailto;

    private Group group;

    private City city;

    private Date startdate;

    private Date promisedate;

    private String description;

    private String suggestion;

    private String feedback;

    private Boolean closed;
    
    private User creator;
    
    private User modifier;
    
    private User handler;
    
    private Integer handleStatus;
    
    private Date handlePromisedate;
    
    private String handleResult;
    
    private Integer toTop;
    
    private String supplier;
    
    private Date issueDate;
    
    private String teammates;
    
    private Boolean isCFeedback;
    
    private Date containmentPlanDate;
    
    private Date actionPlanDate;
    
    private String severity;
    
    private String beginStorehouse;
    
    private String spc;
    
    private String orderNo;
    
    private String hawb;
    
    private String partInformation;
    
    private Date scheduledTime;
    
    private Date actualTime;
    
    private String problemStatement;
    
    private String issueDescription;
    
    private String recoveryDescription;
    
    private String rootCause;
    
    private String correctiveAction;
    
    private String attachmentPath;
    
    private String suggest;
    
    //为配合周报，新增一条表示问题状态的属性，有效值：新建｜更新｜关闭
    private String status;
    
    private List<QuestionAttachment> attachmentList;
    
    public Question() {
        
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project == null ? null : project.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getEmailto() {
        return emailto;
    }

    public void setEmailto(String emailto) {
        this.emailto = emailto == null ? null : emailto.trim();
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getPromisedate() {
        return promisedate;
    }

    public void setPromisedate(Date promisedate) {
        this.promisedate = promisedate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion == null ? null : suggestion.trim();
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback == null ? null : feedback.trim();
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getHandler() {
        return handler;
    }

    public void setHandler(User handler) {
        this.handler = handler;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public Date getHandlePromisedate() {
        return handlePromisedate;
    }

    public void setHandlePromisedate(Date handlePromisedate) {
        this.handlePromisedate = handlePromisedate;
    }

    public Integer getToTop() {
        return toTop;
    }

    public void setToTop(Integer toTop) {
        this.toTop = toTop;
    }

    public User getModifier() {
        return modifier;
    }

    public void setModifier(User modifier) {
        this.modifier = modifier;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getTeammates() {
        return teammates;
    }

    public void setTeammates(String teammates) {
        this.teammates = teammates;
    }

    public Boolean getIsCFeedback() {
        return isCFeedback;
    }

    public void setIsCFeedback(Boolean isCFeedback) {
        this.isCFeedback = isCFeedback;
    }

    public Date getContainmentPlanDate() {
        return containmentPlanDate;
    }

    public void setContainmentPlanDate(Date containmentPlanDate) {
        this.containmentPlanDate = containmentPlanDate;
    }

    public Date getActionPlanDate() {
        return actionPlanDate;
    }

    public void setActionPlanDate(Date actionPlanDate) {
        this.actionPlanDate = actionPlanDate;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getBeginStorehouse() {
        return beginStorehouse;
    }

    public void setBeginStorehouse(String beginStorehouse) {
        this.beginStorehouse = beginStorehouse;
    }

    public String getSpc() {
        return spc;
    }

    public void setSpc(String spc) {
        this.spc = spc;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getHawb() {
        return hawb;
    }

    public void setHawb(String hawb) {
        this.hawb = hawb;
    }

    public String getPartInformation() {
        return partInformation;
    }

    public void setPartInformation(String partInformation) {
        this.partInformation = partInformation;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Date getActualTime() {
        return actualTime;
    }

    public void setActualTime(Date actualTime) {
        this.actualTime = actualTime;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getRecoveryDescription() {
        return recoveryDescription;
    }

    public void setRecoveryDescription(String recoveryDescription) {
        this.recoveryDescription = recoveryDescription;
    }

    public String getRootCause() {
        return rootCause;
    }

    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }

    public String getCorrectiveAction() {
        return correctiveAction;
    }

    public void setCorrectiveAction(String correctiveAction) {
        this.correctiveAction = correctiveAction;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<QuestionAttachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<QuestionAttachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

}