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

A patient may see [S1] the list of the rooms and their status which the system recommends and choose [S2] to view the list of the rooms available and request [S3] a room change.
A designated HCP may then either accept [S4] or reject [S5] the room change request after seeing it in the room change request list [S6]. 
The patient can check the status of the room requested if the HCP accepted or rejected the request [S7].

Sub Flows
---------

* [S1] The patient sees the list of the rooms and their status which the system recommends where prices of the rooms are similar to the room which the patient currenly lives.
* [S2] The patient chooses to view the list of the rooms available with the preference of the price or the room story.
* [S3] The patient [E1] may request the designated HCP for a room change [E2] by 
  specifying a room and some optional comments. The patient can choose only the rooms whose wards have the same specialties. 
* [S4] The designated HCP may accept an room change request.
* [S5] The designated HCP may decline an room change request.
* [S6] The designated HCP can see a list of all room change requests for them.
* [S7] The patient can see all requests for room change they created along with their status.

Alternative Flows
-----------------

* [E1] If the patient is not in a room, then he cannot access this "room change" request.
* [E2] If a room is in pending(being requested), other patients cannot choose the room.
