package com.emailalert;

public enum JobStatus {
    COMPLETED("Completed"), FAILED("Failed");

    private String status;

    JobStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public static JobStatus getEnumByString(String code) {
        for (JobStatus jobStatus : JobStatus.values()) {
            if (jobStatus.getStatus().equals(code)) {
                return jobStatus;
            }
        }
        return null;
    }
}
