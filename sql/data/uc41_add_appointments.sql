INSERT INTO appointment (doctor_id, patient_id, sched_date, appt_type, comment)
VALUES (
	9000000000,
	100,
	now() + INTERVAL 6 DAY,
	"General Checkup",
	"uc41lt"
);

INSERT INTO appointment (doctor_id, patient_id, sched_date, appt_type, comment)
VALUES (
	9000000000,
	100,
	now() + INTERVAL 7 DAY,
	"General Checkup",
	"uc41eq"
);

INSERT INTO appointment (doctor_id, patient_id, sched_date, appt_type, comment)
VALUES (
	9000000000,
	100,
	now() + INTERVAL 8 DAY,
	"General Checkup",
	"uc41gt"
);