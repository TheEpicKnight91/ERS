create table ERS_REIMBUSEMENT(
	REIMB_ID serial primary key,
	REIMB_AMOUNT int not null,
	REIMB_SUBMITTED timestamp not null,
	REIMB_RESOLVED timestamp,
	REIMB_DESCRIPTION varchar(250),
	REIMB_RECEIPT bytea,
	REIMB_AUTHOR int references ERS_USERS(ERS_USERS_ID) not null,
	REIMB_RESOLVER int references ERS_USERS(ERS_USERS_ID),
	REIMB_STATUS_ID int references ERS_REIMNURSEMENT_STATUS(REIMB_STATUS_ID) not null,
	REIMB_TYPE_ID int references ERS_REIMBUSEMENT_TYPE(REIMB_TYPE_ID) not null
);

select * from ERS_REIMBUSEMENT;

create table ERS_USERS(
	ERS_USERS_ID serial primary key,
	ERS_USERNAME varchar(50) not null unique,
	ERS_PASSWORD varchar(50) not null,
	USER_FIRST_NAME varchar(100) not null,
	USER_LAST_NAME varchar(100) not null,
	USER_EMAIL varchar(150) not null unique,
	USER_ROLE_ID int references ERS_USER_ROLES(ERS_USER_ROLE_ID) not null
);

select * from ERS_USERS;

create table ERS_USER_ROLES(
	ERS_USER_ROLE_ID serial primary key,
	USER_ROLE varchar(10) not null
);

insert into ERS_USER_ROLES values (default, 'Employee');
insert into ERS_USER_ROLES values (default, 'Manager');

select * from ERS_USER_ROLES;

create table ERS_REIMNURSEMENT_STATUS(
	REIMB_STATUS_ID serial primary key,
	REIMB_STATUS varchar(10) not null
);

insert into ERS_REIMNURSEMENT_STATUS values (default, 'Pending');
insert into ERS_REIMNURSEMENT_STATUS values (default, 'Approved');
insert into ERS_REIMNURSEMENT_STATUS values (default, 'Denied');

select * from ERS_REIMNURSEMENT_STATUS;

create table ERS_REIMBUSEMENT_TYPE(
	REIMB_TYPE_ID serial primary key,
	REIMB_TYPE varchar(10) not null
);

insert into ERS_REIMBUSEMENT_TYPE values (default, 'LODGING');
insert into ERS_REIMBUSEMENT_TYPE values (default, 'FOOD');
insert into ERS_REIMBUSEMENT_TYPE values (default, 'TRAVEL');

select * from ERS_REIMBUSEMENT_TYPE;