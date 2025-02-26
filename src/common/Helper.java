package common;

public class Helper {

    /**
     * unionStrings - Объединение строк через разделитель |
     * @param strings
     * @return
     */
    public static String unionStrings(String ... strings){

        String unionStr = "";

        for (var str : strings){
            unionStr = unionStr + str + "|";
        }
        if (!unionStr.isEmpty()){
            unionStr = unionStr.substring(0,unionStr.length()-1);
        }

        return unionStr;
    }

}
