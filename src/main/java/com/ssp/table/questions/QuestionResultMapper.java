package com.ssp.table.questions;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuestionResultMapper implements RowMapper<QuestionResultInfo> {




        @Override
        public QuestionResultInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

            Long idDepartment = rs.getLong("Department");
            String date = rs.getString("Date_Test");
            Long question1 = rs.getLong("Question1");
            Long question2 = rs.getLong("Question2");
            Long question3 = rs.getLong("Question3");
            Long question4 = rs.getLong("Question4");
            Long question5 = rs.getLong("Question5");
            Long question6 = rs.getLong("Question6");
            Long question7 = rs.getLong("Question7");
            Long question8 = rs.getLong("Question8");
            Long question9 = rs.getLong("Question9");
            Long question10 = rs.getLong("Question10");
            Long question11 = rs.getLong("Question11");
            Long question12 = rs.getLong("Question12");
            Long question13 = rs.getLong("Question13");
            Long question14 = rs.getLong("Question14");
            Long question15 = rs.getLong("Question15");
            Long sumResult = rs.getLong("SumResult");


            return new QuestionResultInfo(idDepartment, date,question1,question2,question3,question4,question5,
                    question6,question7,question8,question9,question10,question11,
                    question12,question13,question14,question15,sumResult);
        }

    }


