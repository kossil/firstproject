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


//Получение вопросов
    public List<QuestionInfo> getQuestion() {
        // Select ba.Id, ba.Question, ba.Meaning From Questions ba Order By ba.Id
        String sql = QuestionMapper.BASE_SQL;

        Object[] params = new Object[] {};
        QuestionMapper mapper = new QuestionMapper();
        List<QuestionInfo> list = this.getJdbcTemplate().query(sql, params, mapper);

        return list;
    }

    //Получение средних баллов по каждому вопросу
    public List<AVGinfo> getAVGResult(String selectDepartment , String date1 , String date2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        String sql = "Select rt.ID_QUESTION,AVG(rt.ANSWER) From RESULT_TEST rt,Interview inter WHERE inter.ID_INTERVIEW=rt.ID and " +
                " inter.Id_Department = " + Long.parseLong(selectDepartment) + " and inter.Date_Interview Between To_Date('"+date1+"','yyyy-mm-dd')"+
                " and To_Date('"+ date2 +"','yyyy-mm-dd')  GROUP BY rt.ID_QUESTION ORDER BY rt.ID_QUESTION" ;

        Object[] params = new Object[] {};
        avgMapper mapper = new avgMapper();
        List<AVGinfo> list = this.getJdbcTemplate().query(sql, params, mapper);

        return list;
    }

//Результаты тестирования(сумма каждого участника)
    public List<QuestionResultInfo> getQuestionResult(String selectDepartment , String date1 , String date2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
        String sql = "Select inter.Summary From Interview inter Where  inter.Id_Department = " + Long.parseLong(selectDepartment) +
                " and inter.Date_Interview Between To_Date('"+date1+"','yyyy-mm-dd')"+" and To_Date('"+ date2 +"','yyyy-mm-dd')" ;

        Object[] params = new Object[] {};
        QuestionResultMapper mapper = new QuestionResultMapper();
        List<QuestionResultInfo> list = this.getJdbcTemplate().query(sql, params, mapper);

        return list;
    }

    //Добавление вопроса в бд Questions
    public void WriteDBQuestion(Long id, String query){
        String saveSql = "insert into Questions(Id,Question)" +" values (?,?)";
        this.getJdbcTemplate().update(saveSql,id,query);
    }
    //Изменение вопроса в бд Questions
    public void ChangeDBQuestion(Long id, String query){
        String Sql = "update Questions set Question=? where id=?";
        this.getJdbcTemplate().update(Sql,query,id);
    }

    //Удаление вопроса с бд Questions
    public void DeleteDBQuestion(Long id){
        String Sql = "delete from Questions where id=?";
        this.getJdbcTemplate().update(Sql,id);
    }

    //Запись результатов в две табл. Interview and Result_Test
    public void WriteResultTest(String allResult, Long idDepartment,List<QuestionInfo> questionInfoList) throws ParseException {
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
        int count = 0;
        for (QuestionInfo questionInfo : questionInfoList) {
            if(questionInfo.getMeaning() == 1){
                if(question_result[count] == 1)
                    sumResult += 5;
                if(question_result[count] == 2)
                    sumResult += 4;
                if(question_result[count] == 3)
                    sumResult += 3;
                if(question_result[count] == 4)
                    sumResult += 2;
                if(question_result[count] == 5)
                    sumResult += 1;
            }
            else{
                sumResult += question_result[count];
            }
            count++;
        }

        this.getJdbcTemplate().update(saveSqlSecond,randomUUIDString,sqlDate,idDepartment,13,sumResult);
        for(int i=0;i<question_result.length;i++){
            this.getJdbcTemplate().update(saveSql,randomUUIDString,i+1,question_result[i]);
        }
    }

}
