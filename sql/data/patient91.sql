INSERT INTO patients
(MID, 
firstName,
lastName, 
email,
address1,
address2,
city,
state,
zip,
phone,
eName,
ePhone,
iCName,
iCAddress1,
iCAddress2,
iCCity, 
ICState,
iCZip,
iCPhone,
iCID,
DateOfBirth,
DateOfDeath,
CauseOfDeath,
MotherMID,
FatherMID,
BloodType,
Ethnicity,
Gender,
TopicalNotes
)
VALUES (
91,
'UC',
'91',
'UC91@gmail.com',
'344 Bob Street',
'',
'Raleigh',
'NC',
'27607',
'555-555-3434',
'Mr patient',
'555-555-3434',
'IC',
'Street1',
'Street2',
'City',
'PA',
'19003-2711',
'555-555-3434',
'1',
'1984-05-19',
'2005-03-10',
'250.10',
1,
0,
'O-',
'Caucasian',
'Male',
'This person is absolutely crazy. Do not touch them.'
)  ON DUPLICATE KEY UPDATE MID = MID;

INSERT INTO users(MID, password, role, sQuestion, sAnswer, isDependent) 
			VALUES (91, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'how you doin?', 'good', 1)
 ON DUPLICATE KEY UPDATE MID = MID;
 /*password: pw*/

INSERT INTO representatives(RepresenterMID, RepresenteeMID) VALUES(2,91)
 ON DUPLICATE KEY UPDATE RepresenterMID = RepresenterMID;

