drop user trmsuser cascade;

create user trmsuser
identified by p4ssw0rd
default tablespace users
temporary tablespace temp
quota 10M on users;

grant connect to trmsuser;
grant resource to trmsuser;
grant create session to trmsuser;
grant create table to trmsuser;
grant create view to trmsuser;

conn trmsuser/p4ssw0rd

-- define tables
create table Employee (
	EmployeeID number not null,
	FirstName varchar2(100),
	LastName varchar2(100),
	EmailAddress varchar2(100),
	Password varchar2(512),
	Salt varchar2(256),
	DirectSupervisorID number,
	DepartmentID number,
	EmployeeTypeID number,
	LastLogin timestamp with time zone,
	constraint PK_Employee primary key (EmployeeID)
);

create table Department (
	DepartmentID number not null,
	DepartmentHeadID number not null,
	DepartmentName varchar2(100),
	constraint PK_Department primary key (DepartmentID)
);

create table EmployeeType (
	EmployeeTypeID number  not null,
	EmployeeType varchar2(50),
	constraint PK_EmployeeType primary key (EmployeeTypeID)
);

-- create table Employee_EmployeeType (
-- 	EmployeeID number not null,
-- 	EmployeeTypeID number  not null,
-- 	constraint PK_Employee_EmployeeType primary key (EmployeeID, EmployeeTypeID)
-- );

create table Event (
	EventID number not null,
	EventName varchar2(100),
	EventStartTime timestamp with time zone,
	Cost number(10,2),
	LocationID number not null,
	EventTypeID number not null,
	GradingFormatID number not null,
	constraint PK_Event primary key (EventID)
);

create table EventType (
	EventTypeID number not null,
	EventType varchar2(50),
	ReimbursementCoveragePercent number,
	constraint PK_EventType primary key (EventTypeID)
);

create table Location (
	LocationID number not null,
	StreetAddress1 varchar2(100),
	StreetAddress2 varchar2(100),
	City varchar2(50),
	State varchar2(50),
	ZipCode varchar2(20),
	Country varchar2(50),
	constraint PK_Location primary key (LocationID)
);

create table GradingFormat (
	GradingFormatID number not null,
	GradingFormat varchar2(50),
	constraint PK_GradingFormat primary key (GradingFormatID)
);

create table Attachment (
	AttachmentID number not null,
	AttachmentLocation varchar2(200),
	ReimbursementID number not null,
	AttachmentDescription varchar2(200),
	constraint PK_Attachment primary key (AttachmentID)
);

create table ReimbursementStatus (
	ReimbursementStatusID number not null,
	ReimbursementStatus varchar2(50),
	constraint PK_ReimbursementStatus primary key (ReimbursementStatusID)
);

create table InformationRequest (
	InformationRequestID number not null,
	RequesterID number not null,
	RequesteeID number not null,
	ReimbursementID number not null,
	RequesterMessage varchar2(500),
	RequesteeResponse varchar2(500),
	RequestDate timestamp with time zone,
	ResponseDate timestamp with time zone,
	constraint PK_InformationRequest primary key (InformationRequestID)
);

create table Reimbursement (
	ReimbursementID number not null,
	RequesterId number not null,
	Description varchar2(500),
	WorkRelatedJustification varchar2(500),
	ReimbursementAmountRequested number(10, 2),
	ReimbursementStatusID number,
	DateSubmitted timestamp with time zone,
	DateCompleted timestamp with time zone,
	EventID number,
	ReimbursementResponseID number,
	constraint PK_Reimbursement primary key (ReimbursementID)
);

create table ReimbursementResponse (
	ReimbursementResponseID number not null,
	ResponderID number not null,
	ReimbursementAmountAwarded number(10, 2),
	ReimbursementAmountExceeded number(10, 2),
	ReimbursementExceedReason varchar2(500),
	DisapprovalReason varchar2(500),
	constraint PK_ReimbursementResponse primary key (ReimbursementResponseID)
);

-- create foreign key constraints
alter table Employee add constraint FK_Employee_DirectSupervisor foreign key (DirectSupervisorID) references Employee(EmployeeID);
alter table Employee add constraint FK_Employee_Department foreign key (DepartmentID) references Department(DepartmentID);
alter table Employee add constraint FK_Employee_EmployeeType foreign key (EmployeeTypeID) references EmployeeType(EmployeeTypeID);
alter table Department add constraint FK_Department_DepartmentHead foreign key (DepartmentHeadID) references Employee(EmployeeID);
alter table Event add constraint FK_Event_Location foreign key (LocationID) references Location(LocationID);
alter table Event add constraint FK_Event_EventType foreign key (EventTypeID) references EventType(EventTypeID);
alter table Event add constraint FK_Event_GradingFormat foreign key (GradingFormatID) references GradingFormat(GradingFormatID);
alter table Attachment add constraint FK_Attachment_Reimbursement foreign key (ReimbursementID) references Reimbursement(ReimbursementID);
alter table InformationRequest add constraint FK_InfoRequest_Requester foreign key (RequesterID) references Employee(EmployeeID);
alter table InformationRequest add constraint FK_InfoRequest_Requestee foreign key (RequesteeID) references Employee(EmployeeID);
alter table InformationRequest add constraint FK_InfoRequest_Reimbursement foreign key (ReimbursementID) references Reimbursement(ReimbursementID);
alter table Reimbursement add constraint FK_Reimbursement_RequesterID foreign key (RequesterID) references Employee(EmployeeID);
alter table Reimbursement add constraint FK_Reimbursement_Status foreign key (ReimbursementStatusID) references ReimbursementStatus(ReimbursementStatusID);
alter table Reimbursement add constraint FK_Reimbursement_Response foreign key (ReimbursementResponseID) references ReimbursementResponse(ReimbursementResponseID);
alter table ReimbursementResponse add constraint FK_ReimResponse_Responder foreign key (ResponderID) references Employee(EmployeeID);

-- create sequences for all tables
create sequence SQ_EmployeeID;
create sequence SQ_ReimbursementID;
create sequence SQ_DepartmentID;
create sequence SQ_EmployeeTypeID;
create sequence SQ_EventID;
create sequence SQ_AttachmentID;
create sequence SQ_ReimResponseID;
create sequence SQ_LocationID;
create sequence SQ_InformationRequestID;
create sequence SQ_GradingFormatID;
create sequence SQ_EventTypeID;
create sequence SQ_ReimbursementStatus;

-- insert reference table values
insert into EmployeeType (EmployeeType) values ('Employee');
insert into EmployeeType (EmployeeType) values ('Supervisor');
insert into EmployeeType (EmployeeType) values ('Department Head');
insert into EmployeeType (EmployeeType) values ('Benefits Coordinator');

insert into Department (DepartmentName, DepartmentHeadID) values ('Human Resources', 1);
insert into Department (DepartmentName, DepartmentHeadID) values ('Dev-Alpha', 2);
insert into Department (DepartmentName, DepartmentHeadID) values ('Dev-Bravo', 3);
insert into Department (DepartmentName, DepartmentHeadID) values ('Test-Alpha', 4);
insert into Department (DepartmentName, DepartmentHeadID) values ('Test-Bravo', 5);

insert into EventType (EventType, ReimbursementCoveragePercent) values ('University Course', 80.00);
insert into EventType (EventType, ReimbursementCoveragePercent) values ('Seminar', 60.00);
insert into EventType (EventType, ReimbursementCoveragePercent) values ('Certification Preparation Class', 75.00);
insert into EventType (EventType, ReimbursementCoveragePercent) values ('Certification', 100.00);
insert into EventType (EventType, ReimbursementCoveragePercent) values ('Technical Training', 90.00);
insert into EventType (EventType, ReimbursementCoveragePercent) values ('Other', 30.00);

insert into GradingFormat (GradingFormat) values ('A-F');
insert into GradingFormat (GradingFormat) values ('PASS/FAIL');
insert into GradingFormat (GradingFormat) values ('Presentation');

insert into ReimbursementStatus (ReimbursementStatus) values ('Pending');
insert into ReimbursementStatus (ReimbursementStatus) values ('Approved by Supervisor');
insert into ReimbursementStatus (ReimbursementStatus) values ('Approved by Department Head');
insert into ReimbursementStatus (ReimbursementStatus) values ('Approved by Benefits Coordinator');
-- create before insertion triggers for all tables
create or replace trigger TR_InsertEmployee
before insert on Employee
for each row
begin
	select SQ_EmployeeID.nextval into :new.EmployeeID from dual;
end;
/
create or replace trigger TR_InsertDepartment
before insert on Department
for each row
begin
	select SQ_DepartmentID.nextval into :new.DepartmentID from dual;
end;
/
create or replace trigger TR_InsertEmployeeType
before insert on EmployeeType
for each row
begin
	select SQ_EmployeeTypeID.nextval into :new.EmployeeTypeID from dual;
end;
/
create or replace trigger TR_InsertEvent
before insert on Event
for each row
begin
	select SQ_EventID.nextval into :new.EventID from dual;
end;
/
create or replace trigger TR_InsertAttachment
before insert on Attachment
for each row
begin
	select SQ_AttachmentID.nextval into :new.AttachmentID from dual;
end;
/
create or replace trigger TR_InsertGradingFormat
before insert on GradingFormat
for each row
begin
	select SQ_GradingFormatID.nextval into :new.GradingFormatID from dual;
end;
/
create or replace trigger TR_InsertEventType
before insert on EventType
for each row
begin
	select SQ_EventTypeID.nextval into :new.EventTypeID from dual;
end;
/
create or replace trigger TR_InsertLocation
before insert on Location
for each row
begin
	select SQ_LocationID.nextval into :new.LocationID from dual;
end;
/
create or replace trigger TR_InsertReimbursement
before insert on Reimbursement
for each row
begin
	select SQ_ReimbursementID.nextval into :new.ReimbursementID from dual;
end;
/
create or replace trigger TR_InsertReimbursementStatus
before insert on ReimbursementStatus
for each row
begin
	select SQ_ReimbursementStatus.nextval into :new.ReimbursementStatusID from dual;
end;
/
create or replace trigger TR_InsertReimResponse
before insert on ReimbursementResponse
for each row
begin
	select SQ_ReimResponseID.nextval into :new.ReimbursementResponseID from dual;
end;
/
create or replace trigger TR_InsertInfoRequest
before insert on InformationRequest
for each row
begin
	select SQ_InformationRequestID.nextval into :new.InformationRequestID from dual;
end;
/

-- stored procedures
create or replace procedure SP_Get_Employee_Count (EmployeeCount out integer) is
begin
	select count(*) into EmployeeCount from Employee;
end;
/

create or replace procedure SP_Get_Reimbursement_Count (ReimbursementCount out integer) is
begin
	select count(*) into ReimbursementCount from Reimbursement;
end;
/

create or replace procedure SP_Get_Info_Request_Count (InfoRequestCount out integer) is
begin
	select count(*) into InfoRequestCount from InformationRequest;
end;
/

create or replace procedure SP_Get_Reim_Response_Count (ReimResponseCount out integer) is
begin
	select count(*) into ReimResponseCount from ReimbursementResponse;
end;
/