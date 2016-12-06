/*Creates the Orthopedic Office Visit Request for UC88 Acceptance Test 1*/
INSERT INTO orthopedicSchedule(
   PATIENTMID,
   DOCTORMID,
   OID,
   dateTime,
   docLastName,
   docFirstName,
   comments,
   pending,
   accepted
)
VALUES (407,9220000000,102102,'2016-12-20 15:00:00','doctor','Momsen','My arms hurt',1,0);