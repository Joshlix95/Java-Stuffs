public int blackjack(int a, int b) {
  if (a > 21 && b > 21)
    return 0;
  else if (a > 21)
    return b;
  else if (b > 21)
    return a;
  else{
    int rep = 21 - a >= 21 - b ? b : a; 
    return rep;
  }
}
