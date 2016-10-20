public int commonTwo(String[] a, String[] b) {
  HashMap<String, Boolean> map = new HashMap<String, Boolean>();
  
  for (int i = 0; i < a.length; i++){
    map.put(a[i], false);
  }
  
  for (int i = 0; i < b.length; i++){
    if (map.containsKey(b[i])){
      map.put(b[i], true);
    }
  }
  
  int count = 0;
  
  for (Boolean i : map.values()){
    if (i){
      count++;
    }
  }
  
  return count;
}
