package businesslogic;

public interface Translator {
    //String doGetTranslatedWord(String wordForTranslate) throws Exception;
    String doGetTranslatedWord(Object... objects) throws Exception;
    String doTranslate(String wordForTranslate) throws Exception;

}
