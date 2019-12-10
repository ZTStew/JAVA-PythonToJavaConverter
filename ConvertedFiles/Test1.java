/* File Automaticly Generated */
/* Time: 12/09/2019 21:52:47 */

public class Test1 {
    public static void main(String[] args) {
        /* # The Purpose of 'Test1.py' is to provide a senario where comments and simple */
        /* # print statements are tested and converted into java */
        
        /* # */
        
        
        
        
        
        
        
        
        /* # Generic print statement */
        System.out.println("Line 1");
        
        /* # Print statement using concatenation */
        System.out.println("Line " + "2 " + " concatenation test (plus)");/* # */
        
        /* # Print statement to test how the convertor treats casting statements */
        /* # print("Line " + str(3) + "(casting)") ### */
        System.out.println("Line 3");/* ### */
        
        System.out.println("Line 4");/* # Tests comment reader for if it can detect comments after code. */
        
        /* # Tests converter when the comment notator is found in actual code. */
        System.out.println("Line #5");
        
        /* # Tests print statements with 'print' inside */
        System.out.println("Line 6: print");
        
        /* # Print statement to test the presence of the start of the word 'print' */
        System.out.println("Line 7: pr");
        
        /* # Print statement tests using commas for concatenation */
        System.out.println("Line" + " " +  "8");
        
        /* ################################################################################ */
        
        System.out.println("Expected Output: \nLine 1\nLine 2 concatenation test (plus)\nLine 3\nLine 4");
        System.out.println("Line #5\nLine 6: print\nLine 7: pr\nLine 8");   /* # */
        
        /* # Tests if statements */
        if(3 > 5){
            System.out.println("3 > 5");
        } else if(3 < 5){
            System.out.println("3 < 5");
        } else {
            System.out.println("3 == 5");
        }
        
        /* # if not(False): */
        /* #     print("not test") */
    }
}
