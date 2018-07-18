package com.ssp.table.questions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class DataBaseDAO extends JdbcDaoSupport {

    DataSource dataSource;

    @Autowired
    public DataBaseDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
        this.dataSource=dataSource;
    }


    public List<QuestionInfo> getQuestion() {
        // Select ba.Id, ba.Question From Questions ba Order By ba.Id
        String sql = QuestionMapper.BASE_SQL;

        Object[] params = new Object[] {};
        QuestionMapper mapper = new QuestionMapper();
        List<QuestionInfo> list = this.getJdbcTemplate().query(sql, params, mapper);

        return list;
    }

    public List<QuestionResultInfo> getQuestionResult(String selectDepartment , String date1 , String date2) throws ParseException {
        // Select ba.Id, ba.Question From Questions ba Order By ba.Id

        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        String sql = "Select * From Result_Test rt Where rt.Department = " + Long.parseLong(selectDepartment) +
                " and rt.Date_Test Between To_Date('"+date1+"','yyyy-mm-dd')"+" and To_Date('"+ date2 +"','yyyy-mm-dd')" ;

        Object[] params = new Object[] {};
        QuestionResultMapper mapper = new QuestionResultMapper();
        List<QuestionResultInfo> list = this.getJdbcTemplate().query(sql, params, mapper);

        return list;
    }

    public void WriteDBQuestion(Long id, String query){

       // JdbcTemplate template = new JdbcTemplate(dataSource);
        //this.getJdbcTemplate().batchUpdate(saveSql,) //forMore
        //template.update(saveSql,query);
        // String sqlUpdate = "Update Bank_Account set Balance = ? where Id = ?";
        // this.getJdbcTemplate().update(sqlUpdate,200, 1);

        String saveSql = "insert into Questions(Id,Question)" +" values (?,?)";
        this.getJdbcTemplate().update(saveSql,id,query);
    }

    public void WriteResultTest(String allResult, Long idDepartment) throws ParseException {
        String saveSql = "insert into Result_Test(Department,Date_Test,Question1,Question2," +
                "Question3,Question4,Question5,Question6,Question7,Question8,Question9,Question10," +
                "Question11,Question12,Question13,Question14,Question15)" +" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String[] subStr;
        String separator = ",";
        Date date = new Date();
        String dateTest = String.format("%tF", date);
        subStr = allResult.split(separator);
        Long[] question_result = new Long[subStr.length];

       for (int i = 0 ; i < subStr.length ; i++){
               question_result[i] = Long.parseLong(subStr[i]);

       }
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        Date myDate = format.parse(dateTest);
        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());

        this.getJdbcTemplate().update(saveSql,idDepartment,sqlDate,question_result[0],question_result[1],question_result[2],question_result[3],
                question_result[4],question_result[5],question_result[6],question_result[7],question_result[8],question_result[9],question_result[10],
                question_result[11],question_result[12],question_result[13],question_result[14]);



    }

}
