package businesslogic;

public interface Translator {
    String doGetTranslatedWord(String wordForTranslate) throws Exception;
    String doTranslate(String wordForTranslate) throws Exception;

}
