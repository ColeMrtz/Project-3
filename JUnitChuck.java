import static org.junit.Assert.*;
import org.junit.*;
/*******************************************
 * The test class for Chuck
 *
 * @author Professor Grissom
 * @version Sept 4, 2013
 ******************************************/
public class JUnitChuck
{
    private Chuck game;

    /** allow for round off error */
    private double TOLERANCE = 0.1; 

    private static final int ONES = 1;
    private static final int TWOS = 2;
    private static final int THREES= 3;
    private static final int FOURS = 4;
    private static final int FIVES = 5;
    private static final int SIXES = 6;  
    private static final int FIELD = 7;
    private static final int SMALL = 8;
    private static final int LARGE = 9;

    /******************************************************
     * Instantiate a Kiosk object.
     *
     * Called before every test case method.
     *****************************************************/
    @Before
    public void setUp()
    {
        game = new Chuck();  
    }

    /******************************************************
     * Test initial values of the constructor
     *****************************************************/
    @Test 
    public void testConstructor()
    {
        int credits = game.getCredits();

        // confirm there is only one copy of Title 1
        Assert.assertEquals("ChuckALuck(): game should start with 10 credits", 
            credits, 10);                  
    } 

    /******************************************************
     * Test add credits
     *****************************************************/
    @Test 
    public void testAddCredits()
    {
        int credits = game.getCredits();
        game.addCredits(10);
        int newCredits = game.getCredits();
        // confirm new credits
        Assert.assertEquals("ChuckALuck(): game should start with 10 credits", 
            newCredits, credits+10);                  
    }     

    /******************************************************
     * Test cancel bets
     *****************************************************/
    @Test 
    public void testCancelBets()
    {
        int credits = game.getCredits();

        game.placeBet(LARGE);
        game.cancelBet(LARGE);
        game.testRoll(6,3,3);

        // confirm credits did not change
        Assert.assertEquals("Cancel Bet: Attempted to cancel a bet", 
            credits, game.getCredits());                  
    }   

    /******************************************************
     * Test enough credits
     *****************************************************/
    @Test 
    public void testEnoughCredits()
    {

        // play until two credits remain
        while (game.getCredits() > 2){
            game.placeBet(LARGE);
            game.roll();
        }
        game.placeBet(ONES);
        // confirm there is enough credits
        Assert.assertEquals("Enough Credits: should be enough", 
            true, game.enoughCredits());  

        game.placeBet(TWOS);                
        // confirm there is enough credits  
        Assert.assertEquals("Enough Credits: should be enough", 
            true, game.enoughCredits());          

        game.placeBet(THREES);                
        // confirm there is NOT enough credits  
        Assert.assertEquals("Enough Credits: should not be enough", 
            false, game.enoughCredits());    
    } 

    /******************************************************
     * Test Large
     *****************************************************/
    @Test 
    public void testLarge()
    {
        // confirm large is rolled
        int credits = game.getCredits();
        game.placeBet(LARGE);
        game.testRoll(6,3,3);
        Assert.assertEquals("bet LARGE(6,3,3): credits should increase by one", 
            game.getCredits(), credits+1);  

        // confirm large is not rolled
        credits = game.getCredits();
        game.placeBet(LARGE);
        game.testRoll(2,3,3);
        Assert.assertEquals("bet LARGE(6,3,3): credits should decrease by one", 
            game.getCredits(), credits-1);   

        // confirm large is not rolled with three of kind
        credits = game.getCredits();
        game.placeBet(LARGE);
        game.testRoll(3,3,3);
        Assert.assertEquals("bet LARGE(3,3,3): credits should decrease by one", 
            game.getCredits(), credits-1);  
    }

    /******************************************************
     * Test Small
     *****************************************************/
    @Test 
    public void testSmall()
    {
        // confirm small is rolled
        int credits = game.getCredits();
        game.placeBet(SMALL);
        game.testRoll(1,2,2);
        Assert.assertEquals("bet SMALL(1,2,2): credits should increase by one", 
            game.getCredits(), credits+1);  

        // confirm small is not rolled
        credits = game.getCredits();
        game.placeBet(SMALL);
        game.testRoll(6,4,1);
        Assert.assertEquals("bet SMALL(6,4,1): credits should decrease by one", 
            game.getCredits(), credits-1);   

        // confirm small is not rolled with three of kind
        credits = game.getCredits();
        game.placeBet(SMALL);
        game.testRoll(2,2,2);
        Assert.assertEquals("bet SMALL(2,2,2): credits should decrease by one", 
            game.getCredits(), credits-1);  
    }

    /******************************************************
     * Test Field
     *****************************************************/
    @Test 
    public void testField()
    {
        // confirm Field is rolled
        int credits = game.getCredits();
        game.placeBet(FIELD);
        game.testRoll(6,3,4);
        Assert.assertEquals("bet FIELD(6,3,4): credits should increase by one", 
            game.getCredits(), credits+1);  

        // confirm Field is rolled
        credits = game.getCredits();
        game.placeBet(FIELD);
        game.testRoll(1,2,3);
        Assert.assertEquals("bet FIELD(1,2,3): credits should increase by one", 
            game.getCredits(), credits+1);  

        // confirm Field is not rolled
        credits = game.getCredits();
        game.placeBet(FIELD);
        game.testRoll(3,4,3);
        Assert.assertEquals("bet FIELD(3,4,3): credits should decrease by one", 
            game.getCredits(), credits-1);   

    }

    /******************************************************
     * Test Wrong Three of a Kind
     *****************************************************/
    @Test 
    public void testTriplets()
    {
        // confirm Field is rolled
        int credits = game.getCredits();
        game.placeBet(TWOS);
        game.testRoll(3,3,3);
        Assert.assertEquals("bet TWOS(3,3,3): credits should decrease by one", 
            game.getCredits(), credits-1);                  
    }    

    /******************************************************
     * Test Betting on 1
     *****************************************************/
    @Test 
    public void testOnes()
    {
        // test one of a kind
        int credits = game.getCredits();
        game.placeBet(ONES);
        game.testRoll(1,2,3);
        Assert.assertEquals("bet ONES(1,2,3): credits should have increased by one", 
            game.getCredits(), credits+1);                  

        // test for two of kind        
        credits = game.getCredits();
        game.placeBet(ONES);
        game.testRoll(1,3,1);
        Assert.assertEquals("bet ONES(1,3,1): credits should have increased by two", 
            game.getCredits(), credits+2);

        // test for three of kind        
        credits = game.getCredits();
        game.placeBet(ONES);
        game.testRoll(1,1,1);
        Assert.assertEquals("bet ONES(1,1,1): credits should have increased by ten", 
            game.getCredits(), credits+10);                 

        // test for none       
        credits = game.getCredits();
        game.placeBet(ONES);
        game.testRoll(2,3,4);
        Assert.assertEquals("bet ONES(2,3,4): credits should have decreased by one", 
            game.getCredits(), credits-1);                   
    }  

    /******************************************************
     * Test Betting on 2
     *****************************************************/
    @Test 
    public void testTwos()
    {
        // test one of a kind
        int credits = game.getCredits();
        game.placeBet(TWOS);
        game.testRoll(1,2,3);
        Assert.assertEquals("bet TWO(1,2,3): credits should have increased by one", 
            game.getCredits(), credits+1);                  

        // test for two of kind        
        credits = game.getCredits();
        game.placeBet(TWOS);
        game.testRoll(2,3,2);
        Assert.assertEquals("bet TWO(2,3,2): credits should have increased by two", 
            game.getCredits(), credits+2);

        // test for three of kind        
        credits = game.getCredits();
        game.placeBet(TWOS);
        game.testRoll(2,2,2);
        Assert.assertEquals("bet TWO(2,2,2): credits should have increased by ten", 
            game.getCredits(), credits+10);                 

        // test for none       
        credits = game.getCredits();
        game.placeBet(TWOS);
        game.testRoll(1,3,4);
        Assert.assertEquals("bet TWOS(1,3,4): credits should have decreased by one", 
            game.getCredits(), credits-1);                   
    }  

    /******************************************************
     * Test Betting on 3
     *****************************************************/
    @Test 
    public void testThrees()
    {
        // test one of a kind
        int credits = game.getCredits();
        game.placeBet(THREES);
        game.testRoll(1,2,3);
        Assert.assertEquals("bet THREES(1,2,3): credits should have increased by one", 
            game.getCredits(), credits+1);                  

        // test for two of kind        
        credits = game.getCredits();
        game.placeBet(THREES);
        game.testRoll(2,3,3);
        Assert.assertEquals("bet THREES(2,3,3): credits should have increased by two", 
            game.getCredits(), credits+2);

        // test for three of kind        
        credits = game.getCredits();
        game.placeBet(THREES);
        game.testRoll(3,3,3);
        Assert.assertEquals("bet THREES(3,3,3): credits should have increased by ten", 
            game.getCredits(), credits+10);                 

        // test for none       
        credits = game.getCredits();
        game.placeBet(THREES);
        game.testRoll(2,5,4);
        Assert.assertEquals("bet THREES(2,5,4): credits should have decreased by one", 
            game.getCredits(), credits-1);                   
    }    

    /******************************************************
     * Test Betting on 4
     *****************************************************/
    @Test 
    public void testFours()
    {
        // test one of a kind
        int credits = game.getCredits();
        game.placeBet(FOURS);
        game.testRoll(1,4,3);
        Assert.assertEquals("bet FOURS(1,4,3): credits should have increased by one", 
            game.getCredits(), credits+1);                  

        // test for two of kind        
        credits = game.getCredits();
        game.placeBet(FOURS);
        game.testRoll(2,4,4);
        Assert.assertEquals("bet FOURS(2,4,4): credits should have increased by two", 
            game.getCredits(), credits+2);

        // test for three of kind        
        credits = game.getCredits();
        game.placeBet(FOURS);
        game.testRoll(4,4,4);
        Assert.assertEquals("bet FOURS(4,4,4): credits should have increased by ten", 
            game.getCredits(), credits+10);                 

        // test for none       
        credits = game.getCredits();
        game.placeBet(FOURS);
        game.testRoll(2,5,3);
        Assert.assertEquals("bet FOURS(2,5,3): credits should have decreased by one", 
            game.getCredits(), credits-1);                   
    }       

    /******************************************************
     * Test Betting on 5
     *****************************************************/
    @Test 
    public void testFives()
    {
        // test one of a kind
        int credits = game.getCredits();
        game.placeBet(FIVES);
        game.testRoll(5,4,3);
        Assert.assertEquals("bet FIVES(5,4,3): credits should have increased by one", 
            game.getCredits(), credits+1);                  

        // test for two of kind        
        credits = game.getCredits();
        game.placeBet(FIVES);
        game.testRoll(5,4,5);
        Assert.assertEquals("bet FIVES(5,4,5): credits should have increased by two", 
            game.getCredits(), credits+2);

        // test for three of kind        
        credits = game.getCredits();
        game.placeBet(FIVES);
        game.testRoll(5,5,5);
        Assert.assertEquals("bet FIVES(5,5,5): credits should have increased by ten", 
            game.getCredits(), credits+10);                 

        // test for none       
        credits = game.getCredits();
        game.placeBet(FIVES);
        game.testRoll(2,1,3);
        Assert.assertEquals("bet FIVES(2,1,3): credits should have decreased by one", 
            game.getCredits(), credits-1);                   
    }    

    /******************************************************
     * Test Betting on 6
     *****************************************************/
    @Test 
    public void testSixes()
    {
        // test one of a kind
        int credits = game.getCredits();
        game.placeBet(SIXES);
        game.testRoll(1,6,3);
        Assert.assertEquals("bet SIXES(1,6,3): credits should have increased by one", 
            game.getCredits(), credits+1);                  

        // test for two of kind        
        credits = game.getCredits();
        game.placeBet(SIXES);
        game.testRoll(6,6,4);
        Assert.assertEquals("bet SIXES(6,6,4): credits should have increased by two", 
            game.getCredits(), credits+2);

        // test for three of kind        
        credits = game.getCredits();
        game.placeBet(SIXES);
        game.testRoll(6,6,6);
        Assert.assertEquals("bet SIXES(6,6,6): credits should have increased by ten", 
            game.getCredits(), credits+10);                 

        // test for none       
        credits = game.getCredits();
        game.placeBet(SIXES);
        game.testRoll(2,5,3);
        Assert.assertEquals("bet SIXES(2,5,3): credits should have decreased by one", 
            game.getCredits(), credits-1);                   
    }    

    /******************************************************
     * Test Multiple Bets
     *****************************************************/
    @Test 
    public void testMultipleBets()
    {
        // test one win and one loss
        int credits = game.getCredits();
        game.placeBet(SIXES);
        game.placeBet(LARGE);
        game.testRoll(1,6,1);
        Assert.assertEquals("bet LARGE and SIXES(1,6,1): credits should not change", 
            game.getCredits(), credits);                  

        // test two wins
        credits = game.getCredits();
        game.placeBet(SIXES);
        game.placeBet(LARGE);
        game.testRoll(5,6,3);
        Assert.assertEquals("bet LARGE and SIXES(5,6,3): credits should increase by 2", 
            game.getCredits(), credits+2);                  

        // test two losses
        credits = game.getCredits();
        game.placeBet(SIXES);
        game.placeBet(LARGE);
        game.testRoll(1,2,3);
        Assert.assertEquals("bet LARGE and SIXES(1,2,3): credits should decrease by 2", 
            game.getCredits(), credits-2);                  

    }    

    /******************************************************
     * Test run out of credits
     *****************************************************/
    @Test 
    public void testNoCredits()
    {
        // pay game until out of credits
        int credits = game.getCredits();
        while(credits > 0){
            game.placeBet(LARGE);
            game.roll();
            credits = game.getCredits();
        }
        Assert.assertEquals("game over: game should eventually run out of credits", 
            game.getCredits(), 0);   
    }
}