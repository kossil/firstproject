package com.ssp.table.questions;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class avgMapper implements RowMapper<AVGinfo> {
    @Override
    public AVGinfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long idQuestion = rs.getLong("Id_Question");
        Double avg = rs.getDouble("AVG(rt.ANSWER)");
        return new AVGinfo(idQuestion,avg);
    }

}
