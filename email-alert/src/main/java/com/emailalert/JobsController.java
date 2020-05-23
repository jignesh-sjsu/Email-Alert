package com.emailalert;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class JobsController {

    private JobsDao dao;

    @Autowired
    private EmailService emailService;

    private static Map<Integer, String> previousState;

    private static Map<Integer, Boolean> emailSent;

    public JobsController() {
        dao = new JobsDao();
        previousState = new HashMap<>();
        emailSent = new HashMap<>();
        // Populate Cache
        List<ScheduleJob> jobs = dao.getAllJobs();
        if (jobs != null) {
            for (ScheduleJob job : jobs) {
                previousState.put(job.getJobId(), job.getStartTimeStamp());
                emailSent.put(job.getJobId(), false);
            }
        }
    }

    private final AtomicLong counter = new AtomicLong();

    //    @Scheduled(cron = "*/10 * * * * *")
    @Scheduled(initialDelay = 10000, fixedRate = 30000)
    public void run() {
        System.out.println(counter.incrementAndGet() + ". Current time is :: " + Calendar.getInstance().getTime());
        monitorScheduledJobs();
    }

    private void monitorScheduledJobs() {

        List<ScheduleJob> jobs = dao.getAllJobs();
        // Get all scheduled jobs
        // Loop over all jobs in the list
        // For each job, check in cache if start time has been updated from last time
        // IF yes, call dao to clear end time and status
        // IF no, then check end time and status
        // if status != null, then an email with appropriate email template
        for (ScheduleJob job : jobs) {
            if (!job.getStartTimeStamp().equals(previousState.get(job.getJobId()))) {
                // Call update dao
                dao.updateJob(null, null, job.getJobId());
                previousState.put(job.getJobId(), job.getStartTimeStamp());
                emailSent.put(job.getJobId(), false);
            } else if (!StringUtils.isEmpty(job.getEndTimeStamp()) && !StringUtils.isEmpty(job.getStatus()) && !emailSent.get(job.getJobId())) {
                emailService.sendJobMail(CommonConstant.SENDER, CommonConstant.RECEIVER, job);
                emailSent.put(job.getJobId(), true);
            }
        }
    }

    @PostMapping("/updateStartTimeStamp")
    public String updateStartTimeStamp(@RequestParam(value = "") int jobId,
                                     @RequestParam(value = "") String startTimeStamp) {
        boolean success = dao.updateStartTimeStamp(startTimeStamp, jobId);
        if(success) {
            return new JSONObject().put("success", Boolean.TRUE).toString();
        }
        return new JSONObject().put("success", Boolean.FALSE).toString();
    }

    @PostMapping("/updateEndTimeStampAndStatus")
    public String updateStartTimeStamp(@RequestParam(value = "") int jobId,
                                     @RequestParam(value = "") String endTimeStamp,
                                     @RequestParam(value = "") String status) {
        boolean success = dao.updateJob(endTimeStamp, status, jobId);
        if(success) {
            return new JSONObject().put("success", Boolean.TRUE).toString();
        }
        return new JSONObject().put("success", Boolean.FALSE).toString();
    }

    @GetMapping("/getAllJobs")
    public List<ScheduleJob> updateStartTimeStamp() {
        return dao.getAllJobs();
    }
}