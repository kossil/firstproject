package com.ssp.table.controller;

import com.ssp.table.questions.QuestionInfo;
import com.ssp.table.questions.DataBaseDAO;

import com.ssp.table.questions.QuestionResultInfo;
import com.ssp.table.questions.ResultToCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.List;


@Controller
public class MainController {

    @Autowired
    private DataBaseDAO dataBaseDAO;

    //Добавление вопроса форма
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addQuestiont(Model model) {

        QuestionInfo addd = new QuestionInfo(1L,"igm?","");
          model.addAttribute("addd",addd);

        return "form";
    }
    //Добавление вопроса в бд
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addQuestiontToDB(Model model, QuestionInfo addd ) {

            dataBaseDAO.WriteDBQuestion(addd.getId(),addd.getQuestion());
        model.addAttribute("addd",addd);

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
        List<QuestionResultInfo> list = dataBaseDAO.getQuestionResult(selectDepartment,date1,date2);
        List<QuestionInfo> listQuestion = dataBaseDAO.getQuestion();

        Double[] ResultArray = resultToCount.Summary(list);
        System.out.println("Параметры от админа:"+selectDepartment + " " + date1 + " " + date2);
        int i=0;
        if(ResultArray[0].isNaN()){

            for (QuestionInfo questionInfo : listQuestion) {
                questionInfo.setAverage("Таких записей нет");
                i++;
            }
        }
        else {

            for (QuestionInfo questionInfo : listQuestion) {
                questionInfo.setAverage(String.format("%.2f", ResultArray[i]));
                i++;
            }
        }
        model.addAttribute("date1",date1);
        model.addAttribute("date2",date2);
        model.addAttribute("selectdepartment",selectDepartment);
        model.addAttribute("allQuestion",listQuestion);

        return "send";
    }

// вывод данных с бд с вопросами
    @RequestMapping(value = "/table1", method = RequestMethod.GET)
    public String tableDB1(Model model) {

        List<QuestionInfo> list = dataBaseDAO.getQuestion();
        model.addAttribute("allQuestion",list);


        return "table1";
    }

    @RequestMapping(value = "/table2", method = RequestMethod.GET)
    public String tableDB2(Model model) {

        List<QuestionInfo> list = dataBaseDAO.getQuestion();
        model.addAttribute("allQuestion",list);

        return "table2";
    }

    @RequestMapping(value = "/table3", method = RequestMethod.GET)
    public String tableDB3(Model model) {

        List<QuestionInfo> list = dataBaseDAO.getQuestion();

        model.addAttribute("allQuestion",list);

        return "table3";
    }

    @RequestMapping(value = "/table", method = RequestMethod.POST)
    public String tableDBSand(@RequestParam("variable") String formInput,
                              @RequestParam("department") String department) throws ParseException {

        dataBaseDAO.WriteResultTest(formInput,Long.parseLong(department));
        System.out.println(formInput+" "+ department);

        return "table1";
    }



}
