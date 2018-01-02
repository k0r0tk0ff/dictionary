import businesslogic.YandexApiExe;

public class MainIO {
    public static void main(String[] arg) {

        MainIO main = new MainIO();
        String asdf = "town";
        main.doSomeTranslate(asdf);

    }

    void doSomeTranslate(String asdf) {

        YandexApiExe ya = new YandexApiExe();
        try {
            System.out.println(ya.doGetTranslatedWord(asdf));
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
