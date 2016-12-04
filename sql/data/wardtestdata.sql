/* wardroom ward hcp assign ward*/
INSERT INTO `hcpassignedtoward`(`HCP`,`Ward`) VALUES('9230000000','002');

INSERT INTO `hcpassignedhos`(`hosid`,`hcpid`) VALUES('1','9230000000');



/* What if I change the value of price and size??? */
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`, `Price`, `Story`) VALUES('WardTest1','2', '002','Clean', TRUE,  NULL, '50', '1');
/* Now patient2 asigned WardRoomTest1*/
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`, `Price`, `Story`) VALUES('WardTest2',NULL, '002','Clean', TRUE,  NULL, '40', '2');
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`, `Price`, `Story`) VALUES('WardTest3',NULL, '002','Clean', TRUE,  NULL, '50', '3');
INSERT INTO `wardrooms`(`RoomName`,`OccupiedBy`,`InWard`, `Status`, `State`, `Waiting`, `Price`, `Story`) VALUES('WardTest4',NULL, '002','Clean', TRUE,  NULL, '50', '4');
/* CreateTable에서도 변경해야함 */
