public ArrayList<Currency> giveChange() throws Exception {
        ArrayList<Currency> changeList = new ArrayList<Currency>();

        /*
         * Compute change
         * NOTE: Use of int for accuracy. results are converted back to double
         */
        int change = (int) ((myPayment - myPurchase) * 100);

        /*
         * TO DO: Throw exception here if payment is less than purchase
         */
        if (change < 0){
            throw new Exception("The payment was less than the purchase amount.");
        }

        /* Loop through the cash register from larger currency to smaller,
         * divide change by each of the existing currency in myCurrencyList,
         * if the division is greater than or equal to 1, check if the register
         * has sufficient counts of the currency to cover the change
         */
        int remainder = 0, // if $4.25 and count = 4, then remainder = 0.25, all * 100
                count = 0;

        for (int j = myCurrencyList.size() - 1; j >= 0; j--){
            Currency c = myCurrencyList.get(j);
            int value = (int) (c.getValue() * 100);

            if (change / value >= 1){
                count = change / value;
                remainder = change % value;
                int available = 0;  // Available currency in cash register

                if (myMoneyCount.get(c) >= count) {
                    /*
                     * Update the cash register and the list to be returned
                     */

                    myMoneyCount.put(c, myMoneyCount.get(c) - count);
                    for (int i = 0; i < count; i++) {
                        changeList.add(c);
                    }

                    /*
                     * Update myPurchase and myPayment
                     */
                    myPurchase = 0.0;
                    myPayment = 0.0;
                    break;
                }else{
                    available = myMoneyCount.get(c);
                    remainder += (count - available) * value;
                }

            }
        }


        return changeList;
    }
