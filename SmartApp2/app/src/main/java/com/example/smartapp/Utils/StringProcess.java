package com.example.smartapp.Utils;

import android.util.Log;

public class StringProcess {


    public String DecoderRequest(String input){

        String[] input_cut = input.split(" ");
        String newString = "";

        for(int i=0;i<input_cut.length;i++){

            if(i==0){
                newString = input_cut[0];
            }

            if(i>0){
                newString = newString + "%20" + input_cut[i];
            }

        }



        return newString;
    }

    /*
    public String DecoderSms(String input){

        String[] lstWord = {"bật","đèn","phòng","khách"};

        String[] input_cut = input.split(" ");
        String newString = "";


        for(int i=0;i<input_cut.length;i++){

            for(int j=0; j<lstWord.length;j++){

                if(input_cut[i].contains(lstWord[j])) {
                    input_cut[i] = lstWord[j];
                    Log.d("wordxxx","x: "+input_cut[i]);
                    if (i == 0) {

                        newString = input_cut[0];

                    }

                    if (i > 0) {
                        newString = newString + "%20" + input_cut[i];
                    }
                }

            }



        }



        return newString;
    }
*/
    public String getContendInfo(String input){

        int index_start = input.indexOf("Contend");
        int index_end = input.indexOf("Type");

        String substr = input.substring(index_start+10, index_end-5);

        return substr;
    }

    public String getContend(String input){

        int index_start = input.indexOf("Contend");
        int index_end = input.indexOf("Type");

        String substr = input.substring(index_start+10, index_end-3);

        return substr;
    }


    public String getType(String input){

        int index_start = input.indexOf("Type");
        int index_end = input.indexOf("Timer");

        String substr = input.substring(index_start+7, index_end-3);

        return substr;
    }
    //Title
    public String getTimer(String input){

        int index_start = input.indexOf("Timer");
        int index_end = input.indexOf("Title");

        String substr = input.substring(index_start+8, index_end-3);

        return substr;
    }

    public String getTitle(String input){

        int index_start = input.indexOf("Title");
        int index_end = input.length();

        String substr = input.substring(index_start+8, index_end-3);

        return substr;
    }





}
