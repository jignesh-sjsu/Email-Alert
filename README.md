
#Email-Alert
==========================

## Prerequisites

To run this project, you should have the following prerequisites on your machine:

Dependency | Version
-----------|-------------
JAVA | 8 or Higher
Maven | 3.3+
Git | 2.20+


## Steps to setup and compile the application
```shell
# Clone the repo on your machine.
$ git clone https://github.com/jignesh-sjsu/Email-Alert.git

# Open command line interface and go to the folder where you cloned the repo.
$ cd Email-Alert

# Inside this folder, go inside the 'email-alert' folder.
$ cd email-alert

# Next, run the following maven command to 'compile' the application.
$ mvn clean compile install
```

## Steps to run the application
```shell
# Run the application using the following command.
$ ./mvnw -e spring-boot:run
   
```

## APIs to test the application
```shell
1) Trigger an email with custom message and subject, received on the request from User Interface. The timestamp has to be appended to Subject of the email.

Here is the sample curl command:
$ curl --location --request POST 'http://localhost:8080/emailAlert?=' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'body={"subject":"JSON Test","message":"Hello World!"}'

Pass the subject and message as part of the JSON body. You will see a Boolean response in JSON and a corresponding email-alert on the recipient list.

2) Trigger an email at a scheduled frequency or time, which has a pre-determined template with email body, subject and signature

Job has been configured to run every 30 seconds, with an initial delay of 10 seconds. There are 2 jobs in the database table currently. Once the application starts, an email will be sent for both the jobs if the status is set to either Completed or Failed. You can test the scenarios described in the assignment through the following APIs.
	
- API to get all jobs currently in DB.
$ curl --location --request GET 'http://localhost:8080/getAllJobs'

- API to modify the start time stamp.
$ curl --location --request POST 'http://localhost:8080/updateStartTimeStamp' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'jobId=1' \
--data-urlencode 'startTimeStamp=2020-05-24 17:00:00'

Once the start time has been modified, the endtime and the status is immediatly cleared during the next job run.

-API to set the end time stamp and status after they have been cleared.
$ curl --location --request POST 'http://localhost:8080/updateEndTimeStampAndStatus' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'jobId=1' \
--data-urlencode 'endTimeStamp=2020-05-24 18:00:00' \
--data-urlencode 'status=Completed'

This will set the end time stamp and status, and an email will be triggered with an appropriate email template on the next job run. 
```

## Database Configuration
```shell
I have used MySQL on RDS, on my AWS Account. You can continue to use it for testing. Or if you wish to setup your own DB, please update 'JDBCConnectionDao.java'.
```

## Recipient list
```shell
To add multiple email ids as recipient, please update 'CommonConstant.java'.
```