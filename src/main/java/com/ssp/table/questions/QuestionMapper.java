package com.ssp.table.questions;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionMapper implements RowMapper<QuestionInfo> {

    public static final String BASE_SQL
            = "Select ba.Id, ba.Question From Questions ba Order By ba.Id ";

    @Override
    public QuestionInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long id = rs.getLong("Id");
        String question = rs.getString("Question");

        return new QuestionInfo(id, question,"");
    }

}
