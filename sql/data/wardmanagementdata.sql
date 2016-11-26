
INSERT INTO `wards` (`InHospital`, `RequiredSpecialty`) VALUES('1','Pediatrics');
INSERT INTO `wards` (`InHospital`, `RequiredSpecialty`) VALUES('1','Elderly');
INSERT INTO `wards` (`InHospital`, `RequiredSpecialty`) VALUES('1','Cardiology');
INSERT INTO `wards` (`InHospital`, `RequiredSpecialty`) VALUES('1','Maternity');


INSERT INTO `hcpassignedtoward`(`HCP`,`Ward`) VALUES('9000000000','001');
INSERT INTO `hcpassignedtoward`(`HCP`,`Ward`) VALUES('9900000000','002');
INSERT INTO `hcpassignedtoward`(`HCP`,`Ward`) VALUES('9990000000','003');
INSERT INTO `hcpassignedtoward`(`HCP`,`Ward`) VALUES('9000000003','004');

INSERT INTO `hcpassignedhos`(`hosid`,`hcpid`) VALUES('1','9000000000');
INSERT INTO `hcpassignedhos`(`hosid`,`hcpid`) VALUES('1','9000000002');
INSERT INTO `hcpassignedhos`(`hosid`,`hcpid`) VALUES('1','9000000003');
INSERT INTO `hcpassignedhos`(`hosid`,`hcpid`) VALUES('1','9000000007');





INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`) VALUES('Lolita','1', '001','Clean', TRUE,  NULL);
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`) VALUES('Boneyard','100', '002','Clean', TRUE,  NULL);
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`) VALUES('Heartless','10', '003','Clean', TRUE,  NULL);
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`) VALUES('Berbeh','11', '004','Clean', TRUE,  NULL);
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`) VALUES('TEST',NULL, '001','Clean', FALSE,  '1');
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`) VALUES('TEST2',NULL, '001','Clean', FALSE,  '2');
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`) VALUES('TEST3',NULL, '001','Clean', FALSE,  '3');