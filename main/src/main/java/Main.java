

import businesslogic.YandexApiExe;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        String asdf = "town";
        main.doSomeTranslate(asdf);

    }

    void doSomeTranslate(String asdf) {

        YandexApiExe ya = YandexApiExe.getInstance();
        try {
            System.out.println(ya.doGetTranslatedWord(asdf));
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
