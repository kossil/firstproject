package com.ssp.table.questions;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ResultToCount {

    private String finalURL;
    public Double[] Summary(List<QuestionResultInfo> list) {

        Double[] ResultArray = new Double[15];
        Arrays.fill(ResultArray,0.0);
        int i = 0;
        for (QuestionResultInfo questionResultInfo : list) {
            i++;
        }
        for(int k = 0; k < ResultArray.length; k++){
            ResultArray[k] = ResultArray[k]/i;
        }
        return ResultArray;
    }


    public String RandomGen(String department){
        Random random = new Random();
        int num = 1000000000 + random.nextInt(2000000000 - 1000000000);
        finalURL = Integer.toString(num) + "dep" + department;
        return finalURL;
    }

    public String getFinalURL() {
        return finalURL;
    }

    public void setFinalURL(String finalURL) {
        this.finalURL = finalURL;
    }
}
