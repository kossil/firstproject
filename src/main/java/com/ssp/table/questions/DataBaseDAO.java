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
import java.util.UUID;

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
        String sql = "Select inter.Summary From Interview inter Where  inter.Id_Department = " + Long.parseLong(selectDepartment) +
                " and inter.Date_Interview Between To_Date('"+date1+"','yyyy-mm-dd')"+" and To_Date('"+ date2 +"','yyyy-mm-dd')" ;

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

    public void ChangeDBQuestion(Long id, String query){
        String Sql = "update Questions set Question=? where id=?";
        this.getJdbcTemplate().update(Sql,query,id);
    }

    public void DeleteDBQuestion(Long id){
        String Sql = "delete from Questions where id=?";
        this.getJdbcTemplate().update(Sql,id);
    }

    public void WriteResultTest(String allResult, Long idDepartment) throws ParseException {
        String saveSql = "insert into Result_Test(Id,Id_Question,Answer)" +" values (?,?,?)";
        String saveSqlSecond = "insert into Interview(Id_Interview,Date_Interview,Id_Department,Link,Summary)" +" values (?,?,?,?,?)";
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        Date date = new Date();
        String dateTest = String.format("%tF", date);
        String[] subStr;
        String separator = ",";
        subStr = allResult.split(separator);
        Long[] question_result = new Long[subStr.length];

       for (int i = 0 ; i < subStr.length ; i++){
               question_result[i] = Long.parseLong(subStr[i]);

       }
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        Date myDate = format.parse(dateTest);
        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());


        Long sumResult=0L;

        for(int i = 0 ; i < question_result.length ; i++){
            if(i == 2||i == 6||i == 8||i == 10||i == 11||i == 14){
                if(question_result[i] == 1)
                    sumResult += 5;
                if(question_result[i] == 2)
                    sumResult += 4;
                if(question_result[i] == 3)
                    sumResult += 3;
                if(question_result[i] == 4)
                    sumResult += 2;
                if(question_result[i] == 5)
                    sumResult += 1;
            }
            else {
                sumResult += question_result[i];
            }
        }
        this.getJdbcTemplate().update(saveSqlSecond,randomUUIDString,sqlDate,idDepartment,13,sumResult);
        for(int i=0;i<question_result.length;i++){
            this.getJdbcTemplate().update(saveSql,randomUUIDString,i+1,question_result[i]);
        }


    }

}
