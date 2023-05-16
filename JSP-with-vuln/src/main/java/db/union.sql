SELECT * FROM users WHERE user_id='inmo' union select '1','1','1','1',to_date('01/01/2023', 'MM/DD/YYYY') FROM dual --
SELECT * FROM users WHERE user_id='inmo' union select '1','1','1','1',sysdate FROM dual -- 
/*
sysdate 사용하기 더 간단하므로 sysdate를 사용하자!
*/