#!/bin/bash
conf_file=${APP_HOME}/configuration/configuration.properties

typeset -A config # init array

while read line
do
    if echo $line | grep -F = &>/dev/null
    then
        varname=$(echo "$line" | cut -d '=' -f 1)
        config[$varname]=$(echo "$line" | cut -d '=' -f 2-)
    fi
done < $conf_file
dbPassword=$(java -jar  ${APP_HOME}/utility/pass_dypt/pass_dypt.jar spring.datasource.password)
conn="mysql -h${config[dbIp]} -P${config[dbPort]} -u${config[dbUsername]} -p${dbPassword} ${config[appdbName]}"

`${conn} <<EOFMYSQL
SET foreign_key_checks = 0;

INSERT INTO  role (created_on, modified_on, access, description, role_name, status) VALUES
(now(),now(),'all','superrole','superrole','3');

INSERT INTO  group_entity (created_on, modified_on, description, group_name, status) VALUES
(now(),now(),'supergroup','supergroup','3');


INSERT INTO module_tag_bkp (created_on, modified_on, description, module_tag_name, status) VALUES (now(),now(),'List','list','3'),(now(),now(),'add','add','3'),(now(),now(),'visibility','visibility','3'),(now(),now(),'group','group','3'),(now(),now(),'attachment','attachment','3'),(now(),now(),'ssysadmin','ssysadmin','3'),(now(),now(),'resetpass','resetpass','3'),(now(),now(),'export','export','3'),(now(),now(),'filter','filter','3'),(now(),now(),'delete','delete','3'),(now(),now(),'edit','edit','3'),(now(),now(),'view','view','3');

INSERT INTO module (created_on, modified_on, description, module_name, status, module_tag_id) VALUES (now(),now(),'List','List','3',1),(now(),now(),'View','View','3',12),(now(),now(),'Edit','Edit','3',11),(now(),now(),'Add','Add','3',2),(now(),now(),'Delete','Delete','3',10),(now(),now(),'Reset Password','Reset Password','3',7),(now(),now(),'Filter','Filter','3',9),(now(),now(),'Export','Export','3',8),(now(),now(),'Support For Ticket','ssysadmin','3',6),(now(),now(),'Attachment','attachment','3',5),(now(),now(),'Assign Group','group','3',4),(now(),now(),'visibility','visibility','3',3);

INSERT INTO  feature (created_on, modified_on, category, description, feature_name, link, logo, display_order, status) VALUES
(now(),now(),'WEB','User Management','User Management','user','',7,'1'),
(now(),now(),'WEB','Group Management','Group Management','group','',3,'1'),
(now(),now(),'WEB','Dashboard','Dashboard','dashboard','',9,'1'),
(now(),now(),'WEB','Tag','Tag Management','tag',NULL,3,'1'),
(now(),now(),'WEB','Module','Module Management','module',NULL,4,'1'),
(now(),now(),'WEB','Feature Management','Feature Management','feature',NULL,5,'1'),
(now(),now(),'WEB','Feature Module','Feature Module Management','feature-module','dashboard',6,'1'),
(now(),now(),'WEB','Group Feature','Group Feature Management','group-feature',NULL,11,'1'),
(now(),now(),'WEB','USER FEATURE','User Feature Management','user-feature',NULL,12,'1'),
(now(),now(),'WEB','User Group','User Group Management','user-group',NULL,13,'1'),
(now(),now(),'WEB','Role','Role Management','role',NULL,7,'1'),
(now(),now(),'WEB','User Role','User Role Management','user-role',NULL,14,'1'),
(now(),now(),'WEB','Group Role','Group Role Management','group-role',NULL,15,'1'),
(now(),now(),'WEB','Acl','Access Control Management','acl',NULL,8,'1');


insert into feature_module(feature_id,module_id,created_on,status,modified_on) select a.id,b.id,now(),'3',now() from feature a, module b;

insert into group_feature(group_id,feature_id,created_on,modified_on,status) select a.id,b.id,now(),now(),'3' from group_entity a, feature b;

insert into group_role(created_on,modified_on,group_id,role_id,status) select now(),now(),a.id,b.id,'3' from group_entity a, role b;


INSERT INTO  link (created_on, modified_on, link_name, url, iframe_url, url_type, icon) VALUES
(now(),now(),'user','user','/eirs_support/user','URL','cog'),
(now(),now(),'acl','acl','/eirs_support/acl','URL','no-access'),
(now(),now(),'group-role','group-role','/eirs_support/group-role','URL','node-group'),
(now(),now(),'user-role','user-role','/eirs_support/user-role','URL','library'),
(now(),now(),'role','role','/eirs_support/role','URL','users'),
(now(),now(),'user-group','user-group','/eirs_support/user-group','URL','form'),
(now(),now(),'user-feature','user-feature','/eirs_support/user-feature','URL','form'),
(now(),now(),'group-feature','group-feature','/eirs_support/group-feature','URL','host-group'),
(now(),now(),'feature-module','feature-module','/eirs_support/feature-module','URL','user'),
(now(),now(),'feature','feature','/eirs_support/feature','URL','dashboard'),
(now(),now(),'module','module','/eirs_support/module','URL','tag'),
(now(),now(),'tag','tag','/eirs_support/tag','URL','tags'),
(now(),now(),'group','group','/eirs_support/group','URL','cog'),
(now(),now(),'dashboard','dashboard','/eirs_support/dashboard','URL','dashboard'),
(now(),now(),'Dashboard Mdr','./Home',NULL,'URL','dashboard'),
(now(),now(),'ticket','ticket','/eirs_support/ticket','URL','cog'),
(now(),now(),'check-ticket-status','check-ticket-status','/eirs_support/check-ticket-status','URL','cog'),
(now(),now(),'register-ticket','register-ticket','/eirs_support/register-ticket','URL','dashboard'),
(now(),now(),'device-mgmt','./deviceManagement',NULL,'URL','cog'),
(now(),now(),'type_approved','./typeApprove',NULL,'URL','cog'),
(now(),now(),'qualified_agents','./qualifiedAgent',NULL,'URL','cog'),
(now(),now(),'approve_device_TAC','./approveTac',NULL,'URL','cog'),
(now(),now(),'local_manufacturer_IMEI','./localManufacturerIMEI',NULL,'URL','cog'),
(now(),now(),'policeTrackLostDevice','./policeTrackLostDevice',NULL,'URL','cog'),
(now(),now(),'trackLostDevice','./trackLostDevice',NULL,'URL','cog'),
(now(),now(),'imeiStatusCheck','./imeiStatusCheck',NULL,'URL','cog');

insert into role_feature_module_access(created_on,modified_on,role_id,feature_id,module_id,status) select now(),now(),a.id,b.id,c.id,'3' from role a, feature b, module c;

INSERT INTO  user (created_on, current_status, modified_on, parent_id, password, password_date, previous_status, reference_id, remark, user_language, username, user_type_id, approved_by,failed_attempt,last_login_date, approved_date) VALUES (now(),3,now(),NULL,'\\$2a\\$10\\$UAyMzPkZgJOxqrkjbeLMg.ptv2EIkq7JtZno2vqpJYV4yAUmpwcny','2030-10-11 08:07:26',0,NULL,NULL,NULL,'smartadm',NULL,'system',0,now(),now());

INSERT INTO  user_profile (port_address,arrival_port, authority_email, authority_name, authority_phone_no, commune, company_name, country, created_on, designation, display_name, district, email, email_otp, employee_id, expiry_date, first_name, id_card_filename, last_name, locality, middle_name, modified_on, nature_of_employment, nid_file_name, operator_type_id, operator_type_name, passport_no, phone_no, phone_otp, photo_file_name, postal_code, property_location, province, source, source_user_name, street, type, vat_file_name, vat_no, vat_status, village, user_id, national_id, address) VALUES (0,0,'','','','1','Goldi','Cambodia',now(),'S/E',NULL,'1','smart1@gmail.com',NULL,'1231231231',NULL,'Smart',NULL,'One','Crossings',NULL,now(),NULL,NULL,NULL,NULL,NULL,'NULL',NULL,NULL,'201016',NULL,'1',NULL,NULL,'1331321',NULL,NULL,NULL,0,'Chhuk Roadth',-1,'13123123123','jfnvjngkvmkmggkfk');

insert into user_group_membership(created_on,modified_on,group_id,user_id,status) select now(),now(),a.id,b.id,'3' from user a, group_entity b;




update role set status=3;
update group_entity set status=3;
update module_tag set status=3;
update module set status=3;
update feature set status=3;
update feature_module set status=3;
update group_feature set status=3;
update group_role set status=3;
update role_feature_module_access set status=3;
update user set current_status=3;


alter table feature drop FOREIGN KEY FKnhxx75sbjr0gyn8kql7m97gnk;
alter table feature drop FOREIGN KEY FK5t1y1jou2h01hn7tedqyj763n;
alter table feature drop column default_module_id;
alter table feature drop column module_tag_id;
alter table user_password_history drop FOREIGN KEY FK5p57sn7crshs9l6jyl524ihg7;
drop table user_security_question;
drop table security_question;


ALTER TABLE user_password_history MODIFY id bigint NOT NULL AUTO_INCREMENT;
ALTER TABLE user_profile MODIFY id bigint NOT NULL AUTO_INCREMENT;
ALTER TABLE user_feature_ip_access_list MODIFY id bigint NOT NULL AUTO_INCREMENT;
ALTER TABLE user_deactivation_log MODIFY id bigint NOT NULL AUTO_INCREMENT;
ALTER TABLE user MODIFY id bigint NOT NULL AUTO_INCREMENT;
ALTER TABLE role MODIFY id bigint NOT NULL AUTO_INCREMENT;
ALTER TABLE module_tag MODIFY id bigint NOT NULL AUTO_INCREMENT;
ALTER TABLE module MODIFY id bigint NOT NULL AUTO_INCREMENT;
ALTER TABLE link MODIFY id bigint NOT NULL AUTO_INCREMENT;
ALTER TABLE group_entity MODIFY id bigint NOT NULL AUTO_INCREMENT;
ALTER TABLE feature MODIFY id bigint NOT NULL AUTO_INCREMENT;

SET foreign_key_checks = 1;


alter table user_group_membership add column display_order int not null default 0;
update user_profile set user_id=(select id from user where username='smartadm') where user_id=-1;
UPDATE user u INNER JOIN user_profile up ON up.user_id=u.id SET u.profile_id= up.id WHERE u.username='smartadm';

EOFMYSQL`

echo "DB Script Execution Completed"
