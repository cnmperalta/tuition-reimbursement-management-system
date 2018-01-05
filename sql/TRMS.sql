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
	AssignTo number not null,
	Description varchar2(500),
	WorkRelatedJustification varchar2(500),
	ReimbursementAmountRequested number(10, 2),
	AdditionalInformation varchar2(500),
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
create sequence SQ_ReimbursementStatusID;

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
	select SQ_ReimbursementStatusID.nextval into :new.ReimbursementStatusID from dual;
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
create or replace procedure SP_Get_Curr_EmployeeID (CurrEmpID out integer) is
begin
	select SQ_EmployeeID.currval into CurrEmpID from dual;
end;
/
create or replace procedure SP_Get_Curr_ReimbursementID (CurrReimID out integer) is
begin
	select SQ_ReimbursementID.currval into CurrReimID from dual;
end;
/
create or replace procedure SP_Get_Curr_DepartmentID (CurrDeptID out integer) is
begin
	select SQ_DepartmentID.currval into CurrDeptID from dual;
end;
/
create or replace procedure SP_Get_Curr_EmpTypeID (CurrEmpTypeID out integer) is
begin
	select SQ_EmployeeTypeID.currval into CurrEmpTypeID from dual;
end;
/
create or replace procedure SP_Get_Curr_EventID (CurrEvtID out integer) is
begin
	select SQ_EventID.currval into CurrEvtID from dual;
end;
/
create or replace procedure SP_Get_Curr_AttachmentID (CurrAttID out integer) is
begin
	select SQ_AttachmentID.currval into CurrAttID from dual;
end;
/
create or replace procedure SP_Get_Curr_ReimResponseID (CurrReimResponseID out integer) is
begin
	select SQ_ReimResponseID.currval into CurrReimResponseID from dual;
end;
/
create or replace procedure SP_Get_Curr_LocID (CurrLocID out integer) is
begin
	select SQ_LocationID.currval into CurrLocID from dual;
end;
/
create or replace procedure SP_Get_Curr_InfoRequestID (CurrInfoReqID out integer) is
begin
	select SQ_InformationRequestID.currval into CurrInfoReqID from dual;
end;
/
create or replace procedure SP_Get_Curr_GradeFormID (CurrGradeFormID out integer) is
begin
	select SQ_GradingFormatID.currval into CurrGradeFormID from dual;
end;
/
create or replace procedure SP_Get_Curr_EvtTypeID (CurrEvtTypeID out integer) is
begin
	select SQ_EventTypeID.currval into CurrEvtTypeID from dual;
end;
/
create or replace procedure SP_Get_Curr_ReimStatusID (CurrReimStatusID out integer) is
begin
	select SQ_ReimbursementStatusID.currval into CurrReimStatusID from dual;
end;
/

-- insert Mockaroo data for Employees
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (1, 'Sarajane', 'Rubee', 'srubee0@networksolutions.com', 'p32960IH');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (2, 'Florian', 'Marcu', 'fmarcu1@cbc.ca', 'Nwusarvy');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (3, 'Kellen', 'Flisher', 'kflisher2@domainmarket.com', '9Ek9bj53');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (4, 'Fionnula', 'Bails', 'fbails3@disqus.com', 'n0DcxM0l');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (5, 'Rhianon', 'Cordel', 'rcordel4@ft.com', 'Pqd59qr4');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (6, 'Lurette', 'Turn', 'lturn5@accuweather.com', 'x73WLX7A');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (7, 'Anabella', 'Houseman', 'ahouseman6@myspace.com', '5556h93T');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (8, 'Arthur', 'Claricoates', 'aclaricoates7@scribd.com', 'pKq6459m');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (9, 'Kitty', 'Burlingham', 'kburlingham8@nifty.com', '9nHn23g6');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (10, 'Humfrey', 'Dalgardno', 'hdalgardno9@cyberchimps.com', '78T19MNY');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (11, 'Rosemary', 'Ondra', 'rondraa@bloglines.com', '546660aD');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (12, 'Branden', 'Plail', 'bplailb@stanford.edu', 'q80aMz9X');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (13, 'Coleman', 'Reyson', 'creysonc@biglobe.ne.jp', 'Zko4T460');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (14, 'Sigismondo', 'Leppard', 'sleppardd@1688.com', 'q2EU04l1');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (15, 'Mayor', 'Vala', 'mvalae@netvibes.com', '6iAGF575');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (16, 'Raimundo', 'Liepina', 'rliepinaf@independent.co.uk', 'HT1G9TTt');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (17, 'Kylie', 'Swanson', 'kswansong@mtv.com', '3i9F41dJ');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (18, 'Joelly', 'Ossulton', 'jossultonh@a8.net', '624YJ7fF');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (19, 'Mallorie', 'Pettisall', 'mpettisalli@ask.com', 'Hc098rp9');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (20, 'Corly', 'Pilipyak', 'cpilipyakj@msn.com', '8hPuwQ7A');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (21, 'Miguelita', 'Shellshear', 'mshellsheark@slideshare.net', 'A54leA1w');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (22, 'Adrianna', 'Petkov', 'apetkovl@tripadvisor.com', 'pnpq392f');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (23, 'Si', 'Tweedie', 'stweediem@histats.com', '7fqbi2Y1');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (24, 'Marissa', 'Belfrage', 'mbelfragen@alexa.com', '746xUc1e');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (25, 'Philbert', 'Quare', 'pquareo@sakura.ne.jp', 'jq4qtB2o');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (26, 'Kim', 'Major', 'kmajorp@blogtalkradio.com', 'N91C1wJO');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (27, 'Eleen', 'Mordan', 'emordanq@youtu.be', '5iW5422E');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (28, 'Madge', 'Aimson', 'maimsonr@is.gd', 'Qz23E321');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (29, 'Tommi', 'O''Mahoney', 'tomahoneys@behance.net', 'c273HW6y');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (30, 'Pauly', 'Colquhoun', 'pcolquhount@si.edu', '2Rz082de');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (31, 'Veradis', 'Stollsteimer', 'vstollsteimeru@stumbleupon.com', '1jfe3p6U');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (32, 'Donavon', 'Goodisson', 'dgoodissonv@sphinn.com', 'h07VwNd3');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (33, 'Skip', 'Storre', 'sstorrew@nps.gov', 'yN7j1e88');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (34, 'Geneva', 'Catherine', 'gcatherinex@psu.edu', '5k9H5Nfx');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (35, 'Kirstin', 'Vanni', 'kvanniy@vinaora.com', 'mb3M1z55');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (36, 'Odelinda', 'Balassi', 'obalassiz@businessweek.com', '0j942S34');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (37, 'Guillema', 'Harnwell', 'gharnwell10@goo.ne.jp', '8Kp42pX7');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (38, 'Thain', 'Beharrell', 'tbeharrell11@mozilla.com', '416CexS1');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (39, 'Maddie', 'Buttery', 'mbuttery12@freewebs.com', 'w040137W');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (40, 'Emmalynne', 'Maeer', 'emaeer13@hc360.com', '8vzj913K');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (41, 'Alis', 'Arnaldy', 'aarnaldy14@so-net.ne.jp', '4Ij2S876');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (42, 'Ayn', 'Walworth', 'awalworth15@harvard.edu', 'O44tK6RH');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (43, 'Hazel', 'Galletley', 'hgalletley16@jalbum.net', 'v5W37u54');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (44, 'Lothario', 'Careswell', 'lcareswell17@flavors.me', 'O33a0m12');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (45, 'Sayres', 'Antuk', 'santuk18@tamu.edu', 'NjA13h89');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (46, 'Charlot', 'Keslake', 'ckeslake19@loc.gov', 'h5d69cH0');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (47, 'Allyn', 'Kinze', 'akinze1a@csmonitor.com', '2o7a820V');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (48, 'Anita', 'Sevitt', 'asevitt1b@g.co', 'UA6FZ24Y');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (49, 'Anthe', 'Melloi', 'amelloi1c@topsy.com', 'R5gj8H4c');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (50, 'Marlon', 'Gumey', 'mgumey1d@businessweek.com', '2o507j6X');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (51, 'Boone', 'Shimoni', 'bshimoni1e@shutterfly.com', 'bZ15H76Q');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (52, 'Emmott', 'Vint', 'evint1f@scientificamerican.com', '455QM4j5');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (53, 'Ginger', 'Vreede', 'gvreede1g@angelfire.com', '070YrTVc');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (54, 'Brien', 'Ormiston', 'bormiston1h@google.de', 'dr523fXC');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (55, 'Juieta', 'Lochrie', 'jlochrie1i@weather.com', 'QMu9o7bZ');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (56, 'Errol', 'Reveley', 'ereveley1j@gravatar.com', '3f5o98Jl');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (57, 'Willyt', 'Whiten', 'wwhiten1k@blogspot.com', 'e1F33833');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (58, 'Janette', 'Lindl', 'jlindl1l@eepurl.com', '266j2632');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (59, 'Willi', 'Bleasby', 'wbleasby1m@flickr.com', '3O2o1Mv2');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (60, 'Abbie', 'Raubenheimer', 'araubenheimer1n@a8.net', 'M219QP9p');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (61, 'Delmar', 'Forsythe', 'dforsythe1o@omniture.com', 'iNmZ3A2S');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (62, 'Leif', 'Verlinde', 'lverlinde1p@webs.com', 'iH04x529');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (63, 'Rosalinda', 'Vaar', 'rvaar1q@blinklist.com', '93ssgy8q');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (64, 'Gaspard', 'Gonthier', 'ggonthier1r@plala.or.jp', '5rT502S5');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (65, 'Brod', 'Streat', 'bstreat1s@toplist.cz', '79Oj37N9');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (66, 'Magda', 'Mailes', 'mmailes1t@liveinternet.ru', '6308VH5E');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (67, 'Angelina', 'Walisiak', 'awalisiak1u@blogspot.com', '6tsV2720');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (68, 'Gil', 'Baddeley', 'gbaddeley1v@ameblo.jp', 'EA1J1h7d');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (69, 'Leonelle', 'Rosbottom', 'lrosbottom1w@independent.co.uk', '995RF5Y0');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (70, 'Phip', 'Beebe', 'pbeebe1x@dailymail.co.uk', '86G86DPB');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (71, 'Skipton', 'Portch', 'sportch1y@yahoo.com', '9QQ3huA8');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (72, 'Kerrie', 'Mattiuzzi', 'kmattiuzzi1z@google.it', 'htqoFlw3');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (73, 'Lorie', 'Dreinan', 'ldreinan20@go.com', '28br1149');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (74, 'Clarke', 'Robotham', 'crobotham21@blog.com', '38j94ET8');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (75, 'Candace', 'Manssuer', 'cmanssuer22@123-reg.co.uk', 'Dg1O6t21');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (76, 'Jacintha', 'Shakesbye', 'jshakesbye23@si.edu', 'i3gAMS8C');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (77, 'Anjanette', 'Dogg', 'adogg24@ibm.com', 'Z4MMbP1r');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (78, 'Charlie', 'McGonigle', 'cmcgonigle25@cocolog-nifty.com', 'KIYC8742');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (79, 'Ingemar', 'Scini', 'iscini26@businessinsider.com', 'Xr53Fb7Q');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (80, 'Vincenz', 'Benzing', 'vbenzing27@ox.ac.uk', '279V1c73');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (81, 'Jinny', 'Knappe', 'jknappe28@multiply.com', 'U60ND44A');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (82, 'Cammie', 'Carefull', 'ccarefull29@fc2.com', '9yJGH6qf');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (83, 'Mal', 'Aucock', 'maucock2a@youtube.com', 'oHl3E4Py');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (84, 'Ashely', 'Kapelhof', 'akapelhof2b@paginegialle.it', 'jouo1O1c');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (85, 'Vince', 'Balshaw', 'vbalshaw2c@wunderground.com', 'X78q1Buj');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (86, 'Bastien', 'Helm', 'bhelm2d@latimes.com', 'kmB98426');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (87, 'Cecilia', 'Braidman', 'cbraidman2e@ca.gov', '0w2o075j');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (88, 'Wilie', 'Kibbel', 'wkibbel2f@rakuten.co.jp', 'Cm52Xh88');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (89, 'Husein', 'Szwandt', 'hszwandt2g@flickr.com', '5pljV04V');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (90, 'Lazarus', 'Widdop', 'lwiddop2h@guardian.co.uk', 'd4A162En');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (91, 'Rachelle', 'Muino', 'rmuino2i@biglobe.ne.jp', '41lHt5jB');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (92, 'Nickolaus', 'Staning', 'nstaning2j@ustream.tv', '39uN882l');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (93, 'Carlie', 'Hammarberg', 'chammarberg2k@homestead.com', 'O1Y6Mvr1');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (94, 'Ernie', 'Ascough', 'eascough2l@skype.com', '9o263809');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (95, 'Harri', 'Till', 'htill2m@zdnet.com', 'b5E4873r');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (96, 'Carmela', 'Avey', 'cavey2n@so-net.ne.jp', '6tW13553');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (97, 'Colette', 'Clemett', 'cclemett2o@usnews.com', '9yG121c4');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (98, 'Lurleen', 'Cordel', 'lcordel2p@e-recht24.de', 'B5135kZp');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (99, 'Arlyne', 'Showler', 'ashowler2q@nationalgeographic.com', '7wjm0tHR');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (100, 'Eldridge', 'Moylan', 'emoylan2r@illinois.edu', 'poI578XK');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (101, 'Othello', 'Physick', 'ophysick2s@dailymotion.com', '2Zx4KVpe');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (102, 'Edmon', 'Harvatt', 'eharvatt2t@boston.com', 'zAk7422p');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (103, 'Kiley', 'Batchellor', 'kbatchellor2u@reverbnation.com', 'A9HbD420');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (104, 'Penny', 'Trundell', 'ptrundell2v@columbia.edu', 'z9D172p0');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (105, 'Osbert', 'Skough', 'oskough2w@europa.eu', '5o3r0ae1');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (106, 'Todd', 'Kenrack', 'tkenrack2x@yahoo.co.jp', '58278r54');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (107, 'Fiona', 'Gonnelly', 'fgonnelly2y@webnode.com', 'faq4387k');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (108, 'Daloris', 'Jeannaud', 'djeannaud2z@earthlink.net', 'p7420kv6');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (109, 'Kary', 'McBrier', 'kmcbrier30@constantcontact.com', 'ZB7b5J9R');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (110, 'Atalanta', 'McTear', 'amctear31@state.gov', 'k274EN38');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (111, 'Chelsie', 'Nansom', 'cnansom32@macromedia.com', '9SUI3t87');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (112, 'Jasen', 'Nicolson', 'jnicolson33@wikipedia.org', 'y5Elt1Qa');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (113, 'Brooke', 'Bunny', 'bbunny34@cbc.ca', 'XY1Bq2x9');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (114, 'Dan', 'Whale', 'dwhale35@123-reg.co.uk', 'RS5B7KD9');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (115, 'Rhodia', 'Cowle', 'rcowle36@gnu.org', '67Hu320Y');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (116, 'Nelli', 'Ishchenko', 'nishchenko37@etsy.com', 'Y6wTGi2a');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (117, 'Doria', 'Keaton', 'dkeaton38@imdb.com', '1X734hVh');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (118, 'Paula', 'Whiles', 'pwhiles39@icio.us', '7AP6X0xZ');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (119, 'Anya', 'Fauning', 'afauning3a@reuters.com', 'xd64371M');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (120, 'Simon', 'Elcoate', 'selcoate3b@imgur.com', '3a8t46YJ');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (121, 'Joe', 'Rustadge', 'jrustadge3c@twitpic.com', 'kD3FR0S0');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (122, 'Earle', 'Leiden', 'eleiden3d@accuweather.com', 'blx792nE');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (123, 'Howey', 'Samwayes', 'hsamwayes3e@walmart.com', '208x8NPD');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (124, 'Tami', 'Schoular', 'tschoular3f@harvard.edu', '88926y5F');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (125, 'Orel', 'Kneller', 'okneller3g@miitbeian.gov.cn', 'W57uIl6k');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (126, 'Ali', 'Laidlaw', 'alaidlaw3h@flickr.com', 'XI09KVUn');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (127, 'Yankee', 'Duchan', 'yduchan3i@arizona.edu', '57T8MwY8');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (128, 'Paxon', 'Castagno', 'pcastagno3j@qq.com', 'tga7n8RO');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (129, 'Amaleta', 'Golly', 'agolly3k@themeforest.net', 'a98g8j98');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (130, 'Zacharias', 'Thunderman', 'zthunderman3l@nydailynews.com', 'VJ1m7YEh');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (131, 'Camey', 'Poore', 'cpoore3m@odnoklassniki.ru', 'A40M7796');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (132, 'Tanner', 'Shorrock', 'tshorrock3n@bizjournals.com', '3l50o3Vy');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (133, 'Nathan', 'Blacker', 'nblacker3o@kickstarter.com', '6Q6q4OY1');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (134, 'Tristam', 'Sopper', 'tsopper3p@slate.com', '06UtX29b');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (135, 'Lauraine', 'Bartolomucci', 'lbartolomucci3q@delicious.com', '6p5ML4xC');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (136, 'Shepperd', 'Lindsey', 'slindsey3r@mtv.com', 'qovy4tk3');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (137, 'Guglielma', 'Minchinton', 'gminchinton3s@4shared.com', '90FO0J5X');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (138, 'Moshe', 'Maughan', 'mmaughan3t@merriam-webster.com', 'c8Yp5S4t');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (139, 'Sandor', 'Withrop', 'swithrop3u@jiathis.com', '6US6F55V');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (140, 'Eimile', 'Ayliff', 'eayliff3v@cbslocal.com', 'DV4W9sq4');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (141, 'Jessamine', 'Hakonsson', 'jhakonsson3w@ucsd.edu', 'VHFH52H5');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (142, 'Royal', 'Tomeo', 'rtomeo3x@altervista.org', 'lVx6w8mq');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (143, 'Timi', 'Marrow', 'tmarrow3y@mashable.com', 'd1ZQa79Y');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (144, 'Sherie', 'Gouldthorpe', 'sgouldthorpe3z@slashdot.org', 'P4I457r7');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (145, 'Jud', 'Wrighton', 'jwrighton40@google.com.hk', '0yPNvv40');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (146, 'Christian', 'Fulleylove', 'cfulleylove41@tripadvisor.com', 'Kdb05n2U');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (147, 'Janis', 'Dearing', 'jdearing42@ow.ly', 'Y58227v4');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (148, 'Dur', 'Uvedale', 'duvedale43@google.nl', '9cU91B81');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (149, 'Fleur', 'Kobiera', 'fkobiera44@fotki.com', '691Nr9Vm');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (150, 'Cornelius', 'Reen', 'creen45@reuters.com', '0Aty0wa4');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (151, 'Farica', 'Haverty', 'fhaverty46@infoseek.co.jp', 'C2Q564x4');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (152, 'Shirl', 'Barth', 'sbarth47@cpanel.net', 'R4S476Qy');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (153, 'Bradly', 'Coleford', 'bcoleford48@latimes.com', 's7dLAEgo');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (154, 'Leanna', 'Paddie', 'lpaddie49@buzzfeed.com', 'p6EC0iEk');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (155, 'Sandor', 'Aireton', 'saireton4a@fda.gov', '1cUliXou');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (156, 'Jonathon', 'Jobb', 'jjobb4b@mashable.com', '328ejQ94');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (157, 'Tami', 'Houldcroft', 'thouldcroft4c@mlb.com', '7024b7MX');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (158, 'Timoteo', 'McGawn', 'tmcgawn4d@vimeo.com', '088DKFoJ');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (159, 'Ailee', 'Saldler', 'asaldler4e@dagondesign.com', 'RNfdm76w');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (160, 'Sharon', 'Sheircliffe', 'ssheircliffe4f@typepad.com', 'N1L38844');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (161, 'Lulu', 'Hurleston', 'lhurleston4g@cnbc.com', '6YIbI1Zm');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (162, 'Stevana', 'Trevarthen', 'strevarthen4h@comcast.net', '0551617Z');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (163, 'Dedra', 'Giacobillo', 'dgiacobillo4i@umn.edu', 'tJWlA551');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (164, 'Eolanda', 'Arntzen', 'earntzen4j@census.gov', 'nC3899Uq');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (165, 'Geoffrey', 'Cufflin', 'gcufflin4k@issuu.com', 'L6Y62tON');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (166, 'Raddie', 'Ulyat', 'rulyat4l@sitemeter.com', '7RJ099T0');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (167, 'Stan', 'Vere', 'svere4m@php.net', '33ID5R5r');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (168, 'Sigfried', 'Dodle', 'sdodle4n@google.nl', '88b2V49R');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (169, 'Everard', 'Wigginton', 'ewigginton4o@example.com', '3FuYt441');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (170, 'Bernardina', 'Broddle', 'bbroddle4p@telegraph.co.uk', '34E10f03');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (171, 'Tonnie', 'Crittal', 'tcrittal4q@twitter.com', 'DX4MiZZ2');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (172, 'Stern', 'Idenden', 'sidenden4r@mediafire.com', '71Ao4j7Z');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (173, 'Shawn', 'Cloonan', 'scloonan4s@youtube.com', '9oht0zC1');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (174, 'Welch', 'Wilmore', 'wwilmore4t@unblog.fr', 'qZ8K98Tt');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (175, 'Goldia', 'Hause', 'ghause4u@ameblo.jp', '4k6PVhY2');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (176, 'Tobi', 'Sodo', 'tsodo4v@dot.gov', '57h3Mc3c');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (177, 'Alwin', 'Hargerie', 'ahargerie4w@4shared.com', '7yn1g3Zc');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (178, 'Dov', 'MacKimm', 'dmackimm4x@npr.org', '41i43w10');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (179, 'John', 'Wagon', 'jwagon4y@biglobe.ne.jp', '7m6T35UY');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (180, 'Elayne', 'Life', 'elife4z@issuu.com', 'W1c526XP');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (181, 'Sheree', 'Clancy', 'sclancy50@mozilla.org', 'yI4Eo754');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (182, 'Korella', 'Strangman', 'kstrangman51@ow.ly', '6m4326fD');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (183, 'Ashton', 'Epton', 'aepton52@theguardian.com', 'n5197dgk');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (184, 'Tera', 'Steggles', 'tsteggles53@squidoo.com', '1Ly547pb');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (185, 'Rahel', 'Frances', 'rfrances54@360.cn', '64rM82h8');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (186, 'Traver', 'Ethersey', 'tethersey55@engadget.com', '26634wQ5');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (187, 'Leontyne', 'Klimashevich', 'lklimashevich56@rediff.com', '6F6745xv');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (188, 'Cob', 'Franchyonok', 'cfranchyonok57@zimbio.com', '2MtQ81Gv');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (189, 'Ray', 'Bessey', 'rbessey58@youtu.be', 'nY77081r');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (190, 'Janeczka', 'Adriani', 'jadriani59@simplemachines.org', '3vi3a05d');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (191, 'Lexy', 'Croshaw', 'lcroshaw5a@va.gov', 'ggv31fp1');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (192, 'Twila', 'Eldred', 'teldred5b@mashable.com', 'vr7Z4q91');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (193, 'Kyla', 'Birt', 'kbirt5c@bbc.co.uk', 'Rz1PLM9Y');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (194, 'Currey', 'Pettegree', 'cpettegree5d@state.gov', '9wU5I6s6');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (195, 'Gibb', 'Holyland', 'gholyland5e@ucoz.com', '4fe5Y8WK');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (196, 'Jorie', 'Keller', 'jkeller5f@paginegialle.it', 'ICax59hW');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (197, 'Delmore', 'Cutten', 'dcutten5g@uiuc.edu', '8r68qN90');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (198, 'Dulsea', 'Chaplain', 'dchaplain5h@ibm.com', '0ve1F0wu');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (199, 'Brittni', 'Annice', 'bannice5i@vk.com', 'eQ376242');
insert into Employee (EmployeeID, FirstName, LastName, EmailAddress, Password) values (200, 'Ardeen', 'Grzegorzewicz', 'agrzegorzewicz5j@psu.edu', '6d4i93R3');

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
insert into ReimbursementStatus (ReimbursementStatus) values ('Pending Grade Submission');
insert into ReimbursementStatus (ReimbursementStatus) values ('Grade Submitted');
insert into ReimbursementStatus (ReimbursementStatus) values ('Complete');

-- set Employee Types
-- set department heads
update Employee set EmployeeTypeID=3 where EmployeeID between 1 and 5;
-- set direct supervisors
update Employee set EmployeeTypeID=2 where EmployeeID between 6 and 55;
-- set bencos
update Employee set EmployeeTypeID=4 where EmployeeID between 56 and 100;
-- set normal employees
update Employee set EmployeeTypeID=1 where EmployeeID between 101 and 1000;

-- set supervisors
update Employee
	 set DirectSupervisorID=6
 where EmployeeID between 101 and 120;
update Employee
	 set DirectSupervisorID=7
 where EmployeeID between 121 and 140;
update Employee
	 set DirectSupervisorID=8
 where EmployeeID between 141 and 160;
update Employee
	 set DirectSupervisorID=9
 where EmployeeID between 161 and 180;
update Employee
	 set DirectSupervisorID=10
 where EmployeeID between 181 and 200;

-- set department heads
update Employee
	 set DepartmentID=1
 where EmployeeID=6  OR EmployeeID=7 OR EmployeeID between 101 and 140;

update Employee
	 set DepartmentID=2
 where EmployeeID=8  OR EmployeeID=9 OR EmployeeID between 141 and 180;