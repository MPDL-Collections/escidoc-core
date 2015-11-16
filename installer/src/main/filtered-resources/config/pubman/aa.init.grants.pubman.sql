/**
 * Role Grant initialization.
 */

        /**
         * The PubMan depositor gets a depositor grant on context with simple workflow
         */
INSERT INTO aa.role_grant 
    (id, user_id, role_id, object_id, object_title, object_href, creator_id, creation_date)
     VALUES
    ('escidoc:grant3', 'escidoc:pubman-depositor', 'escidoc:role-depositor', 'escidoc:pubman-context-simple', 'PubMan Default Context Simple Workflow', 'ir/context/escidoc_pubman-context-simple', 'escidoc:exuser1', CURRENT_TIMESTAMP);

    
  
        /**
         * The PubMan depositor gets a depositor grant on context with standard workflow
         */
INSERT INTO aa.role_grant 
    (id, user_id, role_id, object_id, object_title, object_href, creator_id, creation_date)
     VALUES
    ('escidoc:grant4', 'escidoc:pubman-depositor', 'escidoc:role-depositor', 'escidoc:pubman-context-standard', 'PubMan Default Context Standard Workflow', 'ir/context/escidoc_pubman-context-standard', 'escidoc:exuser1', CURRENT_TIMESTAMP);

    
    

        /**
         * The PubMan moderator gets a depositor grant on context with simple workflow
         */
INSERT INTO aa.role_grant 
    (id, user_id, role_id, object_id, object_title, object_href, creator_id, creation_date)
     VALUES
    ('escidoc:grant5', 'escidoc:pubman-moderator', 'escidoc:role-depositor', 'escidoc:pubman-context-simple', 'PubMan Default Context Simple Workflow', 'ir/context/escidoc_pubman-context-simple', 'escidoc:exuser1', CURRENT_TIMESTAMP);

    
  
        /**
         * The PubMan moderator gets a depositor grant on context with standard workflow
         */
INSERT INTO aa.role_grant 
    (id, user_id, role_id, object_id, object_title, object_href, creator_id, creation_date)
     VALUES
    ('escidoc:grant6', 'escidoc:pubman-moderator', 'escidoc:role-depositor', 'escidoc:pubman-context-standard', 'PubMan Default Context Standard Workflow', 'ir/context/escidoc_pubman-context-standard', 'escidoc:exuser1', CURRENT_TIMESTAMP);

    
    

        /**
         * The PubMan moderator gets a moderator grant on context with simple workflow
         */
INSERT INTO aa.role_grant 
    (id, user_id, role_id, object_id, object_title, object_href, creator_id, creation_date)
     VALUES
    ('escidoc:grant7', 'escidoc:pubman-moderator', 'escidoc:role-moderator', 'escidoc:pubman-context-simple', 'PubMan Default Context Simple Workflow', 'ir/context/escidoc_pubman-context-simple', 'escidoc:exuser1', CURRENT_TIMESTAMP);

    
  
        /**
         * The PubMan moderator gets a moderator grant on context with standard workflow
         */
INSERT INTO aa.role_grant 
    (id, user_id, role_id, object_id, object_title, object_href, creator_id, creation_date)
     VALUES
    ('escidoc:grant8', 'escidoc:pubman-moderator', 'escidoc:role-moderator', 'escidoc:pubman-context-standard', 'PubMan Default Context Standard Workflow', 'ir/context/escidoc_pubman-context-standard', 'escidoc:exuser1', CURRENT_TIMESTAMP);

    
    
