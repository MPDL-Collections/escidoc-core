/**
 * User initialization
 */
    /**
     * PubMan Depositor
     */   
INSERT INTO aa.user_account
    (id, active, name, loginName, password, creator_id, creation_date, modified_by_id, last_modification_date)
    VALUES
    ('escidoc:pubman-depositor',
    ${SqlTrue},
    'PubMan Depositor',
    'pubman_depositor',
    'test',
    'escidoc:exuser1',
    CURRENT_TIMESTAMP,
    'escidoc:exuser1',
    CURRENT_TIMESTAMP);
    


    /**
     * PubMan Moderator
     */   
INSERT INTO aa.user_account
    (id, active, name, loginName, password, creator_id, creation_date, modified_by_id, last_modification_date)
    VALUES
    ('escidoc:pubman-moderator',
    ${SqlTrue},
    'PubMan Moderator',
    'pubman_moderator',
    'test',
    'escidoc:exuser1',
    CURRENT_TIMESTAMP,
    'escidoc:exuser1',
    CURRENT_TIMESTAMP);
    
