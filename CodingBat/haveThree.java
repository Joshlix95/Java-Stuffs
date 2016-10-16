public boolean haveThree(int[] nums) {
  int len = nums.length;
  boolean ist = false;
  int count = 0;
  
  if (len < 5)
    return false;
    
  if (nums[0] == 3){
    ist = true;
    count++;
  }
  
  for (int i = 1; i < len; i++){
    if (ist){
      if (nums[i] == 3)
        return false;
      else
        ist = false;
    }else{
      if (nums[i] == 3){
        ist = true;
        ++count;
      }
    }
  }
  
  return (count == 3);
}
