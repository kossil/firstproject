package com.ssp.table.questions;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class QuestionResultMapper implements RowMapper<QuestionResultInfo> {
        @Override
        public QuestionResultInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long summary = rs.getLong("Summary");
            return new QuestionResultInfo(summary);
        }

    }


