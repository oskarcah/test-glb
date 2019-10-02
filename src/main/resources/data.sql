-- Initial seed data for Celebrity problem

-- Problem 1
-- Problem
INSERT INTO problem (id, created_at, last_modified_at) VALUES (1,NOW(),NOW());
-- People
INSERT INTO person (id,problem_id,index,full_name) VALUES (1,1,0,'Jorge');
INSERT INTO person (id,problem_id,index,full_name) VALUES (2,1,1,'Carlos');
INSERT INTO person (id,problem_id,index,full_name) VALUES (3,1,2,'Juan');
INSERT INTO person (id,problem_id,index,full_name) VALUES (4,1,3,'Maria');
INSERT INTO person (id,problem_id,index,full_name) VALUES (5,1,4,'Ana');
INSERT INTO problem_people (problem_id, person_id) VALUES (1,1);
INSERT INTO problem_people (problem_id, person_id) VALUES (1,2);
INSERT INTO problem_people (problem_id, person_id) VALUES (1,3);
INSERT INTO problem_people (problem_id, person_id) VALUES (1,4);
INSERT INTO problem_people (problem_id, person_id) VALUES (1,5);
-- Relations
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (1,1,2);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (1,1,4);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (1,1,3);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (1,2,5);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (1,2,4);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (1,3,4);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (1,5,2);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (1,5,4);
-- celebity
INSERT INTO problem_celebrities (problem_id, celebrity_id) VALUES (1,4);


-- Problem 2
-- Problem
INSERT INTO problem (id, created_at, last_modified_at) VALUES (2,NOW(),NOW());
-- People
INSERT INTO person (id,problem_id,index,full_name) VALUES (6,1,0,'Joe');
INSERT INTO person (id,problem_id,index,full_name) VALUES (7,1,1,'Peter');
INSERT INTO person (id,problem_id,index,full_name) VALUES (8,1,2,'Stephen');
INSERT INTO person (id,problem_id,index,full_name) VALUES (9,1,3,'Angela');
INSERT INTO person (id,problem_id,index,full_name) VALUES (10,1,4,'Myra');
INSERT INTO person (id,problem_id,index,full_name) VALUES (11,1,5,'Frank');
INSERT INTO person (id,problem_id,index,full_name) VALUES (12,1,6,'Lenka');
INSERT INTO problem_people (problem_id, person_id) VALUES (2,6);
INSERT INTO problem_people (problem_id, person_id) VALUES (2,7);
INSERT INTO problem_people (problem_id, person_id) VALUES (2,8);
INSERT INTO problem_people (problem_id, person_id) VALUES (2,9);
INSERT INTO problem_people (problem_id, person_id) VALUES (2,10);
INSERT INTO problem_people (problem_id, person_id) VALUES (2,11);
INSERT INTO problem_people (problem_id, person_id) VALUES (2,12);
-- Relations
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (2,6,7);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (2,6,8);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (2,6,10);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (2,6,11);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (2,6,12);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (2,7,9);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (2,7,11);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (2,8,10);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (2,8,11);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (2,8,12);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (2,8,6);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (2,10,11);
INSERT INTO known_person (problem_id, person1_id, person2_id) VALUES (2,12,11);





