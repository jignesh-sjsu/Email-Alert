package com.emailalert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class JobsDao {

    private static final Logger logger = LoggerFactory.getLogger(JobsDao.class);

    private static final String GET_ALL_SCHEDULED_JOBS_SQL = "select * from Schedule_Jobs";

    private static final String UPDATE_SCHEDULE_JOBS_SQL = "update Schedule_Jobs set EndTimeStamp = ?, Status = ? where JobId = ?";

    private static final String UPDATE_STS_SCHEDULE_JOBS_SQL = "update Schedule_Jobs set StartTimeStamp = ? where JobId = ?";

    public List<ScheduleJob> getAllJobs() {
        JDBCConnectionDao dao = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ScheduleJob> jobs = new ArrayList<>();
        try {
            dao = new JDBCConnectionDao();
            conn = dao.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(GET_ALL_SCHEDULED_JOBS_SQL);
                logger.info("Executing the getAllJobs query. ");
                rs = ps.executeQuery();
                logger.info("Execute get successful. ");
                while (rs.next()) {
                    logger.info("Found rs.next. Returning next job.");
                    ScheduleJob job = new ScheduleJob();
                    populateJobs(job, rs);
                    jobs.add(job);
                }
                return jobs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dao.close(rs, ps, conn);
        }
        return null;
    }

    public boolean updateJob(String EndTimeStamp, String Status, int JobId) {
        JDBCConnectionDao dao = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean success = false;
        try {
            dao = new JDBCConnectionDao();
            conn = dao.getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(UPDATE_SCHEDULE_JOBS_SQL, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, EndTimeStamp);
                stmt.setString(2, Status);
                stmt.setInt(3, JobId);
                logger.info("Executing the updateJob query. ");
                int rowsAffected = stmt.executeUpdate();
                success = rowsAffected > 0 ? true : false;
            }
        } catch (Exception e) {
            logger.error("Exception in JobsDao#updateJob", e);
        } finally {
            dao.close(rs, stmt, conn);
        }
        return success;
    }

    public boolean updateStartTimeStamp(String StartTimeStamp, int JobId) {
        JDBCConnectionDao dao = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean success = false;
        try {
            dao = new JDBCConnectionDao();
            conn = dao.getConnection();
            if (conn != null) {
                stmt = conn.prepareStatement(UPDATE_STS_SCHEDULE_JOBS_SQL, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, StartTimeStamp);
                stmt.setInt(2, JobId);
                logger.info("Executing the updateStartTimeStamp query. ");
                int rowsAffected = stmt.executeUpdate();
                success = rowsAffected > 0 ? true : false;
            }
        } catch (Exception e) {
            logger.error("Exception in JobsDao#updatStartTimeStamp", e);
        } finally {
            dao.close(rs, stmt, conn);
        }
        return success;
    }

    private void populateJobs(ScheduleJob job, ResultSet rs) throws Exception{
        job.setJobId(rs.getInt("JobId"));
        job.setJobName(rs.getString("JobName"));
        job.setUserName(rs.getString("UserName"));
        job.setStartTimeStamp(rs.getString("StartTimeStamp"));
        job.setEndTimeStamp(rs.getString("EndTimeStamp"));
        job.setStatus(JobStatus.getEnumByString(rs.getString("Status")));
    }
}
