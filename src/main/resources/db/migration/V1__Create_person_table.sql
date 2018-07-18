CREATE TABLE TEST.PERSON (
  ID       NUMBER(10, 0),
  QUESTION NVARCHAR2(50),
  CONSTRAINT PK_QUESTIONS_ID PRIMARY KEY (ID) USING INDEX TABLESPACE USERS STORAGE (INITIAL 64 K
                                                                                    MAXEXTENTS UNLIMITED)
)