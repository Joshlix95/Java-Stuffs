public String sameEnds(String string) {
  int len = string.length();
  int mid = len % 2 == 0 ? len / 2 : len / 2 + 1;
  
  for (int i = mid; i < len; i++){
    String ss1 = string.substring(0, len - i);
    String ss2 = string.substring(i);
    
    if (ss1.equals(ss2))
      return ss1;
  }
  
  return "";
}
