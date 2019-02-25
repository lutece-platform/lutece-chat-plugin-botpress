
--
-- Structure for table botpress_bots
--

DROP TABLE IF EXISTS botpress_bots;
CREATE TABLE botpress_bots (
id_b_p_bot int AUTO_INCREMENT,
bot_key varchar(50) default '' NOT NULL,
name varchar(50) default '' NOT NULL,
description varchar(255) default '' NOT NULL,
avatar_url varchar(255) default '' NOT NULL,
language varchar(50) default '' NOT NULL,
bot_status int default '0' NOT NULL,
is_standalone int default '0' NOT NULL,
welcome_message varchar(255) default '',
server_url varchar(255) default '' NOT NULL,
api_version int default '0',
PRIMARY KEY (id_b_p_bot)
);
