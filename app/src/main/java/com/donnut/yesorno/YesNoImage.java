package com.donnut.yesorno;

public class YesNoImage {

   private final String answer;
   private final String image;

   public YesNoImage(String answer, Boolean forced, String image) {
      this.answer = answer;
      this.image = image;
   }

   public String getAnswer() {
      return answer;
   }

   public String getImage() {
      return image;
   }
}
