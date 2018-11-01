package com.almybaties.parsing;

/**
 * Generic ${} attribute value parse
 * @author adonai
 */
public class AlGenericTokenParser {

    private final String openToken;
    private final String closeToken;
    private final AlTokenHandler handler;

    public AlGenericTokenParser(String openToken,String closeToken,AlTokenHandler handler){
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.handler = handler;
    }

    //parse ${} attribute
    public String parse(String text) {
      if(text == null || text.isEmpty()){
         return "";
      }
      //从0开始查找openToken : ${
      int start = text.indexOf(openToken, 0);
      //没有找到，返回原始text
      if(start == -1){
         return text;
      }
      //将text转换为char array
      char[] src = text.toCharArray();

     return "";
    }
}
