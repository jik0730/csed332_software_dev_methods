
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




/* What if I change the value of price and size??? */
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`, `Price`, `Size`) VALUES('Lolita','1', '001','Clean', TRUE,  NULL, '50', '4');
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`, `Price`, `Size`) VALUES('Boneyard','100', '002','Clean', TRUE,  NULL, '50', '4');
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`, `Price`, `Size`) VALUES('Heartless','10', '003','Clean', TRUE,  NULL, '50', '4');
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`, `Price`, `Size`) VALUES('Berbeh','11', '004','Clean', TRUE,  NULL, '50', '4');
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`, `Price`, `Size`) VALUES('TEST', NULL, '001','Clean', FALSE,  '1', '50', '4');
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`, `Price`, `Size`) VALUES('TEST2', NULL, '001','Clean', FALSE,  '2', '50', '4');
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`, `Price`, `Size`) VALUES('TEST3', NULL, '001','Clean', FALSE,  '3', '50', '4');



INSERT INTO `wardroomsshared`(`OccupiedBy`,`InWard`, `InWardRoom`) VALUES('1', '001','1');
INSERT INTO `wardroomsshared`(`OccupiedBy`,`InWard`, `InWardRoom`) VALUES('100', '002','2');
INSERT INTO `wardroomsshared`(`OccupiedBy`,`InWard`, `InWardRoom`) VALUES('10', '003','3');
INSERT INTO `wardroomsshared`(`OccupiedBy`,`InWard`, `InWardRoom`) VALUES('11', '004','4');
INSERT INTO `wardroomsshared`(`OccupiedBy`,`InWard`, `InWardRoom`) VALUES(NULL, '001','5');
INSERT INTO `wardroomsshared`(`OccupiedBy`,`InWard`, `InWardRoom`) VALUES(NULL, '001','6');
INSERT INTO `wardroomsshared`(`OccupiedBy`,`InWard`, `InWardRoom`) VALUES(NULL, '001','7');

