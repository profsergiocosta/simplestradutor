
class Scanner {

    private byte[] input;
    private int current; 


    public Scanner (byte[] input) {
        this.input = input;
    }


    private char peek () {
        if (current < input.length)
           return (char)input[current];
       return '\0';
    }

    private void advance()  {
        char ch = peek();
        if (ch != '\0') {
            current++;
        }
    }

    private String number() {
        int start = current ;
        while (Character.isDigit(peek())) {
            advance();
        }
        
        String n = new String(input, start, current-start)  ;
        return n;
    }


    public String nextToken () {
        char ch = peek();
        if (ch == '0') {
            advance();
            return Character.toString(ch);
        }  else if (Character.isDigit(ch))
            return number();
            
        else if (Character.isDigit(ch))
            return number();
        

        switch (ch) {
            case '+':
            case '-':
                advance();
                return Character.toString(ch);
            default:
                break;
        }

        throw new Error("lexical error");
    }

    

}
/* 
 class Parser {
    
    private Scanner scan;
    private String currentToken;

    public Parser(byte[] input) {
        scan = new Scanner(input);
        currentToken = scan.nextToken();
    }

    private void nextToken () {
        currentToken = scan.nextToken();
    }


    public void parse () {
        expr();
    }
 

    private void match(char t) {
        if (currentToken == t) {
            nextToken();
        }else {
            throw new Error("syntax error");
        }
   }


    void expr() {
        digit();
        oper();
     }

     void digit () {
        if (Character.isDigit(currentToken)) {
            System.out.println("push " + currentToken);
            match(currentToken);
        } else {
           throw new Error("syntax error");
        }
    }

    void oper () {
        if (currentToken == '+') {
            match('+');
            digit();
            System.out.println("add");
            oper();
        } else if (currentToken == '-') {
            match('-');
            digit();
            System.out.println("sub");
            oper();
        } else if (currentToken == '\0') {
            // vazio
        } else {
            throw new Error("syntax error");
        }
    }

}
 */



public class Tradutor {
    public static void main(String[] args) {
        //String input = "8+5-7+9";
       // Parser p = new Parser (input.getBytes());
        //p.parse();

        String input = "289-85+0+69";
        Scanner scan = new Scanner (input.getBytes());
        System.out.println(scan.nextToken());
        System.out.println(scan.nextToken());
        System.out.println(scan.nextToken());
        System.out.println(scan.nextToken());
        System.out.println(scan.nextToken());
        System.out.println(scan.nextToken());
        System.out.println(scan.nextToken());
    }
}
