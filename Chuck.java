import java.util.Arrays;
public class Chuck{
    private int currBal;
    private String currMsg;
    private boolean[] bet = new boolean[9];
    private GVdie[] dx = new GVdie[3];
    private int getSumDice(){
        int i = 0;
        int sum = 0;
        while(i < 3){
            sum = sum + dx[i].getValue();
            i++;
        }
        return sum;
    }

    private boolean isDoubles(int n){
        int i = 0;
        int dbl = 0;
        while(i < 3){
            if(dx[i].getValue() == n){
                dbl++;
            }
            i++;
        }
        if(dbl == 2){
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isTriplets(){
        if(dx[0].getValue() == dx[1].getValue() && dx[0].getValue() ==
        dx[2].getValue()){
            return true;
        }
        else{
            return false;
        }
    }

    private void checkLarge(){
        if(getSumDice() > 10 && isTriplets() == false){
            currBal = currBal + 2;
            currMsg = "You Won!";
        }
    }

    private void checkSmall(){
        if(getSumDice() < 11 && isTriplets() == false){
            currBal = currBal + 2;
            currMsg = "You Won!";
        }
    }

    private void checkField(){
        if(getSumDice() < 8 || getSumDice() > 12){
            currBal = currBal + 2;
            currMsg = "You Won!";
        }
    }

    private void checkNum(int n){
        int i = 0;
        boolean num = false;
        if(dx[0].getValue() == n && isTriplets() == true){
            currBal = currBal + 11;
            currMsg = "You Won!";
        }
        else if(isDoubles(n) == true){
            currBal = currBal + 3;
            currMsg = "You Won!";
        }
        else{
            while(i < 3){
                if(dx[i].getValue() == n){
                    num = true;
                }
                i++;
            }
            if(num == true){
                currBal = currBal + 2;
                currMsg = "You Won!";
            }
        }
    }

    private void checkAllBets(){
        currMsg = "You Lost!";
        int i = 0;
        while(i < 9){
            if(bet[i] == true){
                if(i < 6){
                    checkNum(i + 1);
                }
                else if(i == 6){
                    checkField();
                }
                else if(i == 7){
                    checkSmall();
                }
                else{
                    checkLarge();
                }
            }
            i++;
        }
    }

    public Chuck(){
        int i = 0;
        while(i < 3){
            dx[i] = new GVdie();
            dx[i].setBlank();
            i++;
        }
        Arrays.fill(bet, Boolean.FALSE);
        currBal = 10;
        currMsg = "Place your bets!";
    }

    public String getMessage(){
        return currMsg;
    }

    public int getCredits(){
        return currBal;
    }

    public GVdie[] getDice(){
        return dx;
    }

    public void addCredits(int x){
        if(x > 0){
            currBal = currBal + x;
        }
    }

    public void placeBet(int b){
        if(b > 0 && b < 10 && bet[b - 1] == false){
            bet[b - 1] = true;
        }
    }

    public void cancelBet(int b){
        if(b > 0 && b < 10 && bet[b - 1] == true){
            bet[b - 1] = false;
        }
    }

    public void clearAllBets(){
        Arrays.fill(bet, Boolean.FALSE);
    }

    public void roll(){
        int i = 0;
        int j = 0;
        int num = 0;
        while(j < 9){
            if(bet[j] == true){
                num++;
            }
            j++;
        }
        if(enoughCredits() == true){
            while(i < 3){
                dx[i].roll();
                i++;
            }
            currBal = currBal - num;
            checkAllBets();
            clearAllBets();
        }
        else{
            currMsg = "Not enough credits.";
        }
    }

    public void reset(){
        int i = 0;
        while(i < 3){
            dx[i].setBlank();
            i++;
        }
        currBal = 10;
        currMsg = "Place your bets!";
        clearAllBets();
    }

    public boolean enoughCredits(){
        int i = 0;
        int num = 0;
        while(i < 9){
            if(bet[i] == true){
                num++;
            }
            i++;
        }
        if(currBal >= num){
            return true;
        }
        else{
            return false;
        }
    }

    public void testRoll(int x, int y, int z){
        int i = 0;
        int j = 0;
        int num = 0;
        if(x > 0 && x < 7 && y > 0 && y < 7 && z > 0 && z < 7){
            while(i == 0){
                dx[i].roll();
                if(dx[i].getValue() != x && i == 0){
                    i = i - 1;
                }
                i++;
            }
            while(i == 1){
                dx[i].roll();
                if(dx[i].getValue() != y && i == 1){
                    i = i - 1;
                }
                i++;
            }
            while(i == 2){
                dx[i].roll();
                if(dx[i].getValue() != z && i == 2){
                    i = i - 1;
                }
                i++;
            }
        }
        while(j < 9){
            if(bet[j] == true){
                num++;
            }
            j++;
        }
        currBal = currBal - num;
        checkAllBets();
        clearAllBets();
    }
}
