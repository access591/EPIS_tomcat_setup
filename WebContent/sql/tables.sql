create table invest_trusttype( 
 trustcd varchar2(2) not null,
 trusttype varchar2(20) not null,
 description varchar2(100),
 CREATED_BY  VARCHAR2(10),
  CREATED_DT  DATE,
  UPDATED_BY  VARCHAR2(10),
  UPDATED_DT  DATE,
 CONSTRAINT trust_pk PRIMARY KEY (trustcd) 
); 

insert into invest_trusttype(TRUSTCD, TRUSTTYPE, DESCRIPTION) values ('01','AAI EPF','AAI EPF');
insert into invest_trusttype(TRUSTCD, TRUSTTYPE, DESCRIPTION) values ('02','IAAI ECPF','IAAI ECPF');
insert into invest_trusttype(TRUSTCD, TRUSTTYPE, DESCRIPTION) values ('03','NAA ECPF','NAA ECPF');

create table invest_sec_category( 
 categoryid varchar2(2) not null,
 categorycd varchar2(7) not null,
 description varchar2(100),
 openingbal  VARCHAR2(10),
 investinterest VARCHAR2(10),
 investbaseamt VARCHAR2(10),
 CREATED_BY     VARCHAR2(10),
 CREATED_DT     DATE,
 UPDATED_BY     VARCHAR2(10),
 UPDATED_DT     DATE,
 CONSTRAINT category_pk PRIMARY KEY (categoryid) 
); 


create table invest_arrangers(
arrangercd varchar2(5)not null,
arrangername varchar2(50)not null UNIQUE,
reg_offaddr varchar2(100),
reg_phoneno varchar2(14),
reg_faxno   varchar2(15),
delhi_off_addr varchar2(100),
delhi_off_phno  varchar2(14),
delhi_off_faxno varchar2(15),
delhi_headoff_name varchar2(50),
delhi_headoff_mobileno  varchar2(14),
delhi_headoff_phno  varchar2(14),
pramotor_name  varchar2(50),
pramotor_contactno varchar2(14),
pramotor_email   varchar2(50),
networth_amount  number(8,2),
networth_amountas_on date,
email           varchar2(50),
membershipno_with_sebi varchar2(20),
sebi_validdate         date,
membershipno_with_bse    varchar2(20),
bse_validdate          date,
membershipno_with_nse   varchar2(20),
nse_validdate          date,
membershipno_with_rbi   varchar2(20),
rbi_validdate          date,
remarks				varchar2(200),	
status             char(1),
CREATED_BY     VARCHAR2(10),
CREATED_DT     DATE,
UPDATED_BY     VARCHAR2(10),
UPDATED_DT     DATE,

CONSTRAINT arranger_pk PRIMARY KEY (arrangercd)
);

create table invest_Arr_category(
arrangercd varchar2(5)not null, constraint invest_arrcd_fk Foreign Key (arrangercd) references invest_arrangers(arrangercd),
categoryid varchar2(2) not null,
CONSTRAINT invest_arr_fk FOREIGN KEY (categoryid) REFERENCES invest_sec_category(categoryid) 
);
create table epis_modules (Code char(2) primary key ,name varchar2(50) not null,createdon date default sysdate);
insert into epis_modules(code,name) values('AM','Admin');
insert into epis_modules(code,name) values('RC','RPFC');
insert into epis_modules(code,name) values('LA','Loans & Advances');
insert into epis_modules(code,name) values('CB','Cash Book');
insert into epis_modules(code,name) values('IT','Investment');
insert into epis_modules(code,name) values('FA','Fund Accounting');

create table invest_ratio(
ratiocd  varchar2(2)not null,
validfrom date not null,
validto    date ,
CREATED_BY   VARCHAR2(10),
CREATED_DT    DATE,
UPDATED_BY    VARCHAR2(10),
UPDATED_DT    DATE,
CONSTRAINT ratio_pk PRIMARY KEY (ratiocd)
);

create table invest_ratio_category(
ratiocd  varchar2(2)not null,
categoryid varchar2(2) not null,
percentage number(4,2),
CONSTRAINT invest_cat_fk FOREIGN KEY (ratiocd) REFERENCES invest_ratio(ratiocd) 
);
create table INVEST_PROPOSAL
(
  PROPOSAL_CD VARCHAR2(5),
  REF_NO      VARCHAR2(25) not null,
  TRUSTCD     VARCHAR2(2)    NOT NULL,
  APPROVED    VARCHAR2(1),
  APPROVE_DT  DATE,
  CATEGORYCD  VARCHAR2(7),
  AMT_INV     NUMBER(10,2),
  REMARKS     VARCHAR2(1000),
  CREATED_BY  VARCHAR2(20),
  CREATED_DT  DATE,
  UPDATED_BY  VARCHAR2(20),
  UPDATED_DT  DATE,
  subject varchar2(100),
  proposaldate date,
  market_type varchar2(2),
  approval_remarks varchar2(1000),
  approval_created_by varchar2(20),
  approval_created_dt date,
  excel_path varchar2(20),
  
  CONSTRAINT INVEST_REFNO_pk PRIMARY KEY (REF_NO),
  CONSTRAINT invest_REFNO_fk FOREIGN KEY (TRUSTCD) REFERENCES INVEST_TRUSTTYPE(TRUSTCD) 
);

create table invest_quotationrequest(quotationrequestcd varchar2(5),proposal_ref_no varchar2(20) not null,constraint invest_proposal_ref_no_fk Foreign Key(proposal_ref_no) references invest_proposal(REF_NO),trusttype varchar2(5),CATEGORYID varchar2(2) NOT NULL,surplus_fund number(14,2),Market_Type varchar2(2),approved varchar2(2),remarks varchar2(100),send_qutotationcall_letter varchar2(2),letter_no varchar2(50) constraint invest_letter_no_pk primary key,CONSTRAINT INVEST_CATEGORYID_FK FOREIGN KEY(CATEGORYID) REFERENCES invest_sec_category(CATEGORYID),minimum_quantum number(14,2),quotation_address varchar2(500),quotation_date date,quotation_time varchar2(4),valid_date date,valid_time varchar2(4),open_date date,open_time varchar2(4),CREATED_BY  VARCHAR2(20),CREATED_DT  DATE,UPDATED_BY  VARCHAR2(20),UPDATED_DT  DATE );

create table invest_quotationrequest_dt(letter_no varchar2(50) not null, constraint invest_letter_no_FK FOREIGN KEY (letter_no) references invest_quotationrequest (letter_no),arrangercd varchar2(5) not null, constraint invest_arrangercd_fk FOREIGN KEY(arrangercd) references invest_arrangers(arrangercd) );
create table invest_quotationnotes(letter_no varchar2(50) not null,quotation_notes varchar2(500),constraint invest_letterno_notes_FK FOREIGN KEY (letter_no) references invest_quotationrequest (letter_no));

create table invest_formfilling
(
formCd varchar2(5),
CONSTRAINT invest_formcd_pk primary key (formCd),
proposal_ref_no varchar2(25) not null,
constraint invest_forfill_ref_no_fk Foreign Key(proposal_ref_no) references invest_proposal(REF_NO),
trusttype varchar2(20),
categorycd varchar2(7) NOT NULL,
Market_type varchar2(2) NOT NULL,
Security_name varchar2(100),
no_of_bonds number(12),
amt_inv number(15,4),
statue_of_taxoption char(1),
Name_of_application varchar2(100),
panno varchar2(20),
mailing_address varchar2(300),
contact_person  varchar2(50),
contact_no varchar2(14),
CREATED_BY  VARCHAR2(20),
 CREATED_DT  DATE,
 UPDATED_BY  VARCHAR2(20),
  UPDATED_DT  DATE
);







create table invest_generate_bankletter
(
 bankCd varchar2(5),
 bankName varchar2(50),
 ACCOUNTNO varchar2(20) NOT NULL,
 LETTER_NO varchar2(50),
 bank_letter_no varchar2(50),
 deal_date date,
 settlement_date date,
 QUOTATIONCD varchar2(10) NOT NULL,
 CREATED_BY  VARCHAR2(20),
 CREATED_DT  DATE,
 UPDATED_BY  VARCHAR2(20),
 UPDATED_DT  DATE,
 rate number(5,2),
 noof_days varchar2(2),
 accured_amount number(14,2),
 CONSTRAINT INVEST_ACCOUNTNO_FK FOREIGN KEY(ACCOUNTNO) REFERENCES cb_bank_info,
 CONSTRAINT INVEST_BANKLETTER_NO PRIMARY KEY(bank_letter_no),
 CONSTRAINT INVEST_QUOTATIONCD_FK FOREIGN KEY(QUOTATIONCD) REFERENCES INVEST_QUOTAION_DATA
 );

 
 
 create table EPIS_PROFILE_OPTIONS 
(
optioncode varchar2(5) primary key,
optionname varchar2(200) unique ,
description varchar2(500)
);
alter table EPIS_PROFILE_OPTIONS add path varchar2(100);
alter table EPIS_PROFILE_OPTIONS add optiontype char(1);
create table EPIS_APPROVALS 
(
approvalcode varchar2(5) primary key,
approvalname varchar2(200) unique ,
description varchar2(500)
);
create table EPIS_PROFILE_OPTIONS_MAPPING 
(
optioncode varchar2(5) references EPIS_PROFILE_OPTIONS(optioncode) ,
member char(1) default 'N',
Unit char(1) default 'N',
Region char(1) default 'N',
chq char(1) default 'N'
);

create table INVEST_QUOTAION_DATA
(
  QUOTATIONCD          VARCHAR2(10) not null,
  LETTER_NO            VARCHAR2(50) not null,
  ARRANGERCD           VARCHAR2(5) not null,
  CATEGORYID           VARCHAR2(2) not null,
  SECURITY_NAME        VARCHAR2(100) ,
  DISNICNO             VARCHAR2(20),
  RATING               VARCHAR2(10),
  FACEVALUE            NUMBER(14,2),
  INTEREST_RATE        NUMBER(5,2),
  INTEREST_DATE        DATE,
  YTMPERCENTAGE        NUMBER(5,2),
  DIRECT_INCENTIVE     NUMBER(14,2),
  BROKER_INCENTIVE     NUMBER(14,2),
  MATURITYDATE         DATE,
  CALLOPTION           CHAR(1),
  REDEMPTIONDATE       DATE,
  INVESTMENTTYPE       CHAR(1),
  INVESTMENTMODE       CHAR(1),
  GUARENTEETYPE        CHAR(1),
  PURCHAEPRICE         NUMBER(14,2),
  BROKERNAME           VARCHAR2(50),
  BROKERADDRESS        VARCHAR2(200),
  REMARKS              VARCHAR2(200),
  NUMBER_OF_UNITS      NUMBER(14,2),
  PREMIUM_PAID         NUMBER(14,2),
  INVESTMENT_FACEVALUE NUMBER(14,2),
  TRUSTTYPE            VARCHAR2(20),
  YTMVERIFIED          CHAR(1) default 'N',
  OPENDATE             DATE,
  STATUS               CHAR(1) default 'N',
  SHORTLISTED          CHAR(1),
  APPROVALSTATUS       CHAR(1),
  CREATED_BY           VARCHAR2(10),
  CREATED_DT           DATE,
  UPDATED_BY           VARCHAR2(10),
  UPDATED_DT           DATE,
  YTM_UPDATED_BY       VARCHAR2(10),
  YTM_UPDATED_DT       DATE,
  COMP_UPDATED_BY      VARCHAR2(10),
  COMP_UPDATED_DT      DATE,
  SLA_UPDATED_BY       VARCHAR2(10),
  SLA_UPDATED_DT       DATE,
  APP_UPDATED_BY       VARCHAR2(10),
  APP_UPDATED_DT       DATE,
  DEMAT_NO             VARCHAR2(20),
  REGFLAG              CHAR(1),
  SHORTLISTEDSTATUS    CHAR(1) default 'N',
  APPSTATUS            CHAR(1) default 'N',
  CONSTRAINT quotation_pk PRIMARY KEY (QUOTATIONCD),
  CONSTRAINT letter_no_fk FOREIGN KEY (letter_no) REFERENCES invest_quotationrequest(letter_no),
  CONSTRAINT INVEST_SUBMIT_CATEGORYID_FK FOREIGN KEY(CATEGORYID) REFERENCES invest_sec_category(CATEGORYID)
  
);

create table invest_register(
registercd VARCHAR2(5),
LETTER_NO   VARCHAR2(50),
SECURITY_NAME VARCHAR2(100),
DISNICNO VARCHAR2(20),
RATING VARCHAR2(10),
FACEVALUE NUMBER(14,2),
INTEREST_RATE NUMBER(5,2),
INTEREST_DATE DATE,
YTMPERCENTAGE NUMBER(5,2),
DIRECT_INCENTIVE NUMBER(14,2),
BROKER_INCENTIVE NUMBER(14,2),
MATURITYDATE DATE,
CALLOPTION CHAR(1),
REDEMPTIONDATE DATE,
INVESTMENTTYPE CHAR(1),
INVESTMENTMODE CHAR(1),
GUARENTEETYPE CHAR(1),
PURCHAEPRICE NUMBER(14,2),
BROKERNAME VARCHAR2(50),
BROKERADDRESS VARCHAR2(200),
REMARKS VARCHAR2(200),
NUMBER_OF_UNITS NUMBER(14,2),
PREMIUM_PAID NUMBER(14,2),
INVESTMENT_FACEVALUE NUMBER(14,2),
TRUSTTYPE VARCHAR2(20),
categoryid varchar2(2),
INVEST_DATE DATE,
DMATNO  VARCHAR2(20),
registrar_details  VARCHAR2(1000),
delstatus char(1) default 'N'
);
create table invest_amounttype_details(
amounttype_cd varchar2(12) not null,
amount number(15,4),
amount_type varchar2(1),
amount_date date,
entereddt date,
enteredby varchar2(20),
SECURITY_NAME varchar2(100) unique,
CONSTRAINT amounttypecd_pk PRIMARY KEY (amounttype_cd) 
);

create table EPIS_SUBMODULES (
ModuleCode CHAR(2),
SubModuleCd CHAR(4) primary key,	
SubModName varchar2(50),	
CreatedOn date default sysdate,
constraint fk_modulecd FOREIGN KEY (ModuleCode) references epis_modules (code));

create table EPIS_ACCESSCODES_MT (
ScreenCode CHAR(6) primary key,
ScreenName varchar2(100) ,	
SubmoduleCd CHAR(4),	
Path varchar2(200),
CreatedOn date default sysdate,
constraint fk_submodulecd FOREIGN KEY (SubmoduleCd) references EPIS_SUBMODULES (SubModuleCd));

create table EPIS_role (
roleCd varchar2(20), 
rolename CHAR(6) ,
description varchar2(200),
CreatedOn date default sysdate,
primary key (roleCd)
);

create table EPIS_ACCESSRIGHTS (
userId varchar2(50), 
StageCd varchar2(20), 
ScreenCode CHAR(6) ,
newScreen char(1) default 'N',
editScreen char(1) default 'N',
deleteScreen char(1) default 'N',
viewScreen char(1) default 'N',	
reportScreen char(1) default 'N',
accessRight char(1) default 'N',
CreatedOn date default sysdate,
constraint fk_screencode FOREIGN KEY (ScreenCode) references EPIS_ACCESSCODES_MT (ScreenCode),
constraint fk_userId FOREIGN KEY (userId) references EPIS_user (USERNAME),
constraint fk_StageCd FOREIGN KEY (StageCd) references EPIS_role (roleCd));


alter table epis_user add PENSIONNO number(10);
alter table accountcode_info modify TYPE   CHAR(5);
alter table accountcode_details modify TRUSTTYPE varchar2(20);
alter table bank_info modify TRUSTTYPE VARCHAR2(20);
alter table voucher_info modify TRUSTTYPE varchar2(20);

 
create table AccountCodeType_INFO (
code char(5) primary key,
accountcodetype varchar2(50) unique not null,
description varchar2(150),
enteredby varchar2(10),
entereddt date default sysdate
);

 alter table accountcode_info  ADD CONSTRAINT type_fk FOREIGN KEY (TYPE) REFERENCES accountcodetype_info(CODE);
 alter table accountcodetype_info add (editedby varchar2(10),editedDt date default sysdate,UNITCODE VARCHAR2(10));
 
 alter table invest_trusttype add  unique (TRUSTTYPE);
  alter table epis_user add esignatory blob;
  alter table epis_user add esignName varchar2(100);
  
  create table cb_brs(
TransactionDate date,
ValueDate date,
Amount number(15,2),
creditAmount  number(15,2),
debitAmount number(15,2),
debit_credit char(1),
description varchar2(200),
chequeno varchar2(50),
closingBal number(18,2),
ACCOUNTNO varchar2(20),
keyno varchar2(11) primary key,
constraint FK_cbbrs_ACTNO foreign key (ACCOUNTNO)  references CB_BANK_INFO (ACCOUNTNO)
);

create table cb_brs_columns(
columnValue varchar2(50) primary key,
Description varchar2(100)
);
  
create table cb_brs_mapping(
ACCOUNTNO varchar2(20),
columnNo number(2),
columnValue varchar2(50),
primary key(ACCOUNTNO,columnValue),
 constraint FK_brs_ACTNO foreign key (ACCOUNTNO)  references CB_BANK_INFO (ACCOUNTNO),
 constraint FK_brs_columnValue foreign key (columnValue)  references cb_brs_columns (columnValue)
);

insert into cb_brs_columns(COLUMNVALUE,DESCRIPTION) values('TRANSACTIONDATE','Transaction Date');
insert into cb_brs_columns(COLUMNVALUE,DESCRIPTION) values('VALUEDATE','Value Date');
insert into cb_brs_columns(COLUMNVALUE,DESCRIPTION) values('AMOUNT','Transaction Amount (Credit/Debit)');
insert into cb_brs_columns(COLUMNVALUE,DESCRIPTION) values('CREDITAMOUNT','Transaction Amount (Credit)');
insert into cb_brs_columns(COLUMNVALUE,DESCRIPTION) values('DEBITAMOUNT','Transaction Amount (debit)');
insert into cb_brs_columns(COLUMNVALUE,DESCRIPTION) values('DEBIT_CREDIT','Amount Type - Credit(C)/Debit(D)');
insert into cb_brs_columns(COLUMNVALUE,DESCRIPTION) values('CHEQUENO','Cheque No./ Reference No.');
insert into cb_brs_columns(COLUMNVALUE,DESCRIPTION) values('DESCRIPTION','Transaction Desciprtion');
insert into cb_brs_columns(COLUMNVALUE,DESCRIPTION) values('CLOSINGBAL','Closing Balance');

alter table employee_adj_ob add keyno VARCHAR2(12);