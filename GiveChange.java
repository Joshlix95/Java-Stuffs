public ArrayList<Currency> giveChange() throws Exception {
        ArrayList<Currency> changeList = new ArrayList<Currency>();
        
        /*
         * Compute change in int for accuracy
         */
        int change = (int) ((myPayment -  myPurchase) * 100);
        
        /*
         * Throws exception if payment is less than purchase 
         */
        if (change < 0){
            throw new Exception("The payment was less than the purchase amount.");
        }
        
        /*
         * Loop through the cash register from larger currency to smaller
         * find the right currency to use based on availability in cash register
         */
        int remainder = change;
        
        for (int j = myCurrencyList.size() - 1; j >= 0; j--){
            Currency c = myCurrencyList.get(j);
            int value = (int) (c.getValue() * 100); // value of a specific currency * 100
            int availAmount = 0;                    // holds the available amount of used currency from cash register
            int count = 0;                          // determines how many currencies are NEEDED to fulfill a change
            int available = myMoneyCount.get(c);    // holds the AVAILABLE count of currencies 
            
            if (remainder / value >= 1){
                count = remainder / value;
                
                int x = 0,                          // Counter for count -> currencies needed for the change
                        y = 0;                      // Counter for available currencies : in case there might be more than needed 
                
                while (x < count && y < available){
                    changeList.add(c);
                    ++x;
                    ++y;
                }
                availAmount = value * (x - 1);      // or y -1 since x has same value as y
                remainder -= availAmount;
                
            }
        }
        
        if (remainder != 0){
            throw new Exception("This cash register does not contain enough money to make change.");
        }
        
        return new ArrayList<Currency>();
    }
