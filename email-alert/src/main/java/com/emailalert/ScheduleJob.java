package com.emailalert;

public class ScheduleJob {

    int jobId;
    String jobName;
    String userName;
    String startTimeStamp;
    String endTimeStamp;
    JobStatus status;

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(String startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("JobID: ");
        stringBuilder.append(this.getJobId());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append("JobName: ");
        stringBuilder.append(this.getJobName());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append("UserName: ");
        stringBuilder.append(this.getUserName());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append("StartTimeStamp: ");
        stringBuilder.append(this.getStartTimeStamp());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append("EndTimeStamp: ");
        stringBuilder.append(this.getEndTimeStamp());
        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append("Status: ");
        stringBuilder.append(this.getStatus());
        return stringBuilder.toString();
    }
}
