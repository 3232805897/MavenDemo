package untils;

public class WebUntils {
    public static int change(String n1 ,String n2){
        int a,b;
        //预防传一个空串过来
        if ("".equals(n1)||"".equals(n2)){
            return 0;
        }
        try {
            a = Integer.parseInt(n1);
            b = Integer.parseInt(n2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
        return a+b;
    }
}
