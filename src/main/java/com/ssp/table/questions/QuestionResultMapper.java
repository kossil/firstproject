package com.ssp.table.questions;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QuestionResultMapper implements RowMapper<QuestionResultInfo> {




        @Override
        public QuestionResultInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

          //  Long id_question = rs.getLong("Id_Question");
           // Long answer = rs.getLong("Answer");
            Long summary = rs.getLong("Summary");



            return new QuestionResultInfo(/*id_question,answer,*/summary);
        }

    }


