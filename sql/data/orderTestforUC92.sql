INSERT INTO orthopedic(OrthopedicVisitID, PatientID, HCPID, DateVisit, Injured, MRIReport)
VALUES(456456456, 407, 9220000000, '1995-12-12', 'Very Happy', 'Not So MRI');


INSERT INTO orderTable(VisitID, OrderHCPID, OrderedHCPID, PatientID, Completed)
VALUES(456456457, 9220000000, 9210000000, 407, false);
