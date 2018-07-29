package com.ssp.table.controller;

import com.ssp.table.WebSecurityConfig;
import com.ssp.table.questions.QuestionInfo;
import com.ssp.table.questions.DataBaseDAO;

import com.ssp.table.questions.QuestionResultInfo;
import com.ssp.table.questions.ResultToCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;
import javax.servlet.ServletRequest;


@Controller
public class MainController {

    @Autowired
    private DataBaseDAO dataBaseDAO;

    @Autowired
    private ServletRequest servletRequest;

    private ResultToCount resultToCount = new ResultToCount();

    private String firstURL,secondURL,thirdURL;
    private int flag = 0;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String logggg(Model model) {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(Model model) {
        model.addAttribute("name",servletRequest.getServerName()+":"+servletRequest.getServerPort());
        if (flag == 1) {
            model.addAttribute("first", firstURL);
            model.addAttribute("second",secondURL);
            model.addAttribute("third",thirdURL);
        }
        if(flag==0)
            model.addAttribute("butt","Начать");
        else model.addAttribute("butt","Завершить");
        return "admin";
    }

    @RequestMapping(value="{id}", method = RequestMethod.GET)
    public String method(@PathVariable("id") String id, Model model) {

        List<QuestionInfo> list = dataBaseDAO.getQuestion();
        model.addAttribute("allQuestion",list);

        if(id.equals(firstURL)){
        System.out.println("the url value : "+id );
        return "table1";
        }
        if(id.equals(secondURL)){
            System.out.println("the url value : "+id );
            return "table2";
        }
        if(id.equals(thirdURL)){
            System.out.println("the url value : "+id );
            return "table3";
        }
        return "notfound";
    }

    //Добавление вопроса форма
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public String test(Model model){
        firstURL = resultToCount.RandomGen("1");
        secondURL = resultToCount.RandomGen("2");
        thirdURL = resultToCount.RandomGen("3");
        if(flag==0)
        flag = 1;
        else flag=0;
        model.addAttribute("name",servletRequest.getServerName()+":"+servletRequest.getServerPort());
        if(flag==1){
        model.addAttribute("first",firstURL);
        model.addAttribute("second",secondURL);
        model.addAttribute("third",thirdURL);
        model.addAttribute("butt","Завершить");}
        else
            model.addAttribute("butt","Начать");
        return "admin";
    }
    //работа с вопросами в бд(изменение удаление добавление)
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addQuestiontToDBget(Model model ) {

        List<QuestionInfo> list = dataBaseDAO.getQuestion();
        model.addAttribute("allQuestion",list);

        return "form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addQuestiontToDB(Model model,
                                   @RequestParam("change") String change,
                                   @RequestParam("idQue") String idQue,
                                   @RequestParam("question") String question) {

         //  dataBaseDAO.WriteDBQuestion(addd.getId(),addd.getQuestion());
       // model.addAttribute("addd",addd);
        //Добавить
        if(Integer.parseInt(change) == 1) {
            dataBaseDAO.WriteDBQuestion(Long.parseLong(idQue),question);
        }
        //Изменить
        if(Integer.parseInt(change) == 2) {
            dataBaseDAO.ChangeDBQuestion(Long.parseLong(idQue),question);
        }
        //Delete
        if(Integer.parseInt(change) == 3) {
            dataBaseDAO.DeleteDBQuestion(Long.parseLong(idQue));
        }
        System.out.println(change+" "+idQue+" "+question);
        List<QuestionInfo> list = dataBaseDAO.getQuestion();
        model.addAttribute("allQuestion",list);

        return "form";
    }

//тестовый вариант сенда с жижей
    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String resendDataDB(Model model) {
       // List<QuestionResultInfo> list = dataBaseDAO.getQuestionResult();
       // for (QuestionResultInfo questionResultInfo : list) {
        //    System.out.println(questionResultInfo.getIdDepartment() + " " + questionResultInfo.getDate() + " "
        //            + questionResultInfo.getQuestion1()+ " " + questionResultInfo.getQuestion2());
      //  }
        List<QuestionInfo> list = dataBaseDAO.getQuestion();
        model.addAttribute("allQuestion",list);
        model.addAttribute("igm","3");

        return "send";
    }

// прием жижи
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String sendDataDB(Model model,@RequestParam("selectdepartment") String selectDepartment,
                             @RequestParam("date1") String date1,
                             @RequestParam("date2") String date2) throws ParseException {
        ResultToCount resultToCount = new ResultToCount();
        List<QuestionResultInfo> listResult = dataBaseDAO.getQuestionResult(selectDepartment,date1,date2);
        List<QuestionInfo> listQuestion = dataBaseDAO.getQuestion();

        Double[] ResultArray = resultToCount.Summary(listResult);
        System.out.println("Параметры от админа:"+selectDepartment + " " + date1 + " " + date2);

//          int i=0;
//        if(ResultArray[0].isNaN()){
//
//            for (QuestionInfo questionInfo : listQuestion) {
//                questionInfo.setAverage("Таких записей нет");
//                i++;
//            }
//        }
//        else {
//
//            for (QuestionInfo questionInfo : listQuestion) {
//                questionInfo.setAverage(String.format("%.2f", ResultArray[i]));
//                i++;
//            }
//        }
        model.addAttribute("date1",date1);
        model.addAttribute("date2",date2);
        model.addAttribute("selectdepartment",selectDepartment);
        model.addAttribute("allQuestion",listQuestion);
        model.addAttribute("resultQuestion",listResult);

        return "send";
    }

    @RequestMapping(value = "/table", method = RequestMethod.POST)
    public String tableDBSand(@RequestParam("variable") String allResult,
                              @RequestParam("department") String department) throws ParseException {

        dataBaseDAO.WriteResultTest(allResult,Long.parseLong(department));
        System.out.println(allResult+" "+ department);
        return "table1";
    }

}
