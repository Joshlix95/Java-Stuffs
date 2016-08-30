/*
Author: Josuel M.
Source: http://codingbat.com/prob/p196409
*/

/*
We'll say that a "mirror" section in an array is a group of contiguous elements such that somewhere in the array, the same group appears in reverse order. For example, the largest mirror section in {1, 2, 3, 8, 9, 3, 2, 1} is length 3 (the {1, 2, 3} part). Return the size of the largest mirror section found in the given array.

maxMirror([1, 2, 3, 8, 9, 3, 2, 1]) → 3
maxMirror([1, 2, 1, 4]) → 3
maxMirror([7, 1, 2, 9, 7, 2, 1]) → 2

ATTENTION - This below is an attempted not working solution
*/

public int maxMirror(int[] nums) {
  String mirror = "";
  String snums = "";
  String result = "";
  
  for (int i = 0; i < nums.length; i++){
    snums += Integer.toString(nums[i]);
    mirror += Integer.toString(nums[nums.length - 1 - i]);
  }
    
  for (int i = 0; i < nums.length; i++){
    String sub_mirror = mirror.substring(i);
    int size = sub_mirror.length();
    
    for (int j = 0; j < nums.length; i++){
      String sub = sub_mirror.substring(0, size-j);
      
      if (snums.contains(sub) && sub.length() > result.length()){
        result = sub;
      }
    }
  }
  return result.length();
}
