package com.ssp.table.questions;

import java.util.Arrays;
import java.util.List;

public class ResultToCount {

    public Double[] Summary(List<QuestionResultInfo> list) {

        Double[] ResultArray = new Double[15];
        Arrays.fill(ResultArray,0.0);
        int i=0;
        for (QuestionResultInfo questionResultInfo : list) {
            ResultArray[0]+=questionResultInfo.getQuestion1();
            ResultArray[1]+=questionResultInfo.getQuestion2();
            ResultArray[2]+=questionResultInfo.getQuestion3();
            ResultArray[3]+=questionResultInfo.getQuestion4();
            ResultArray[4]+=questionResultInfo.getQuestion5();
            ResultArray[5]+=questionResultInfo.getQuestion6();
            ResultArray[6]+=questionResultInfo.getQuestion7();
            ResultArray[7]+=questionResultInfo.getQuestion8();
            ResultArray[8]+=questionResultInfo.getQuestion9();
            ResultArray[9]+=questionResultInfo.getQuestion10();
            ResultArray[10]+=questionResultInfo.getQuestion11();
            ResultArray[11]+=questionResultInfo.getQuestion12();
            ResultArray[12]+=questionResultInfo.getQuestion13();
            ResultArray[13]+=questionResultInfo.getQuestion14();
            ResultArray[14]+=questionResultInfo.getQuestion15();
            i++;
        }
        for(int k=0;k<ResultArray.length;k++){
            ResultArray[k]=ResultArray[k]/i;
        }
        return ResultArray;
    }

}
