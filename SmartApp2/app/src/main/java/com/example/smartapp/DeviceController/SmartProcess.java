package com.example.smartapp.DeviceController;

public class SmartProcess {




    public String SendMesseger(String command_type, String device_name){

        String type = "";
        String device = "";
        String mes = "";

        int value = 255;
        String string_value = Integer.toString(value);

        if(command_type.contains("ON")){

            type = string_value + string_value + string_value;

        }

        if(command_type.contains("OFF")){

            type = "000000000";

        }

        if(command_type.contains("RED")){

            type = string_value + "000000";

        }

        if(command_type.contains("GREEN")){

            type = "000"+string_value+"000";

        }

        if(command_type.contains("AQUA")){

            type = "000" + string_value + string_value;

        }

        if(command_type.contains("BLUE")){

            type = "000000" + string_value;

        }

        if(command_type.contains("VIOLET")){

            type = string_value + "000" + string_value;

        }


        if(command_type.contains("UP")){

            type = string_value + string_value + string_value;

        }


        if(device_name.contains("đèn phòng ngủ")){
            device = "2";
        }

        if(device_name.contains("đèn nhà tắm")){
            device = "1";
        }

        if(device_name.contains("đèn phòng khách")){
            device = "3";
        }

        mes = type + device;

        return mes;

    }

}
