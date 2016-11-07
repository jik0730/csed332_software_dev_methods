UC: Request Room Change
======================================================

Preconditions
-------------

An HCP is a registered user of the iTrust Medical Records system [[UC2](http://agile.csc.ncsu.edu/iTrust/wiki/doku.php?id=requirements:uc2)]. 
The patient must be a registered patient in the iTrust Medical Records system. 
The iTrust user has authenticated himself or herself in the iTrust Medical Records system.
The HCP is the designated HCP of the patient.

Main Flow
---------

A patient may request [S1] a room change. 
The designated HCP may then either accept [S2] or reject [S3] the appointment request after seeing it on the room change request [S4]. 
A patient can see if the HCP accepted or rejected their proposed appointment [S5].

Sub Flows
---------

* [S1] A registered patient may request the designated HCP a room change by 
  specifying a room and some optional comments. The patient can choose only the rooms whose wards have the same specialties. 
* [S2] The designated HCP may accept an room change request.
* [S3] The designated HCP may decline an room change request.
* [S4] The designated HCP can see a list of all room change requests for them.
* [S5] A patient can see all requests for room change they created along with their status.

Alternative Flows
-----------------

* [E1] If the patient is not in a room, then he cannot access this "room change" request.
