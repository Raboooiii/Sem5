grunt> data = LOAD '/user/hadoop/Sort.txt' USING PigStorage(',') AS (id:int,value:int);
grunt> sorted = ORDER data BY value ASC;
grunt> DUMP sorted;
grunt> DUMP data;
