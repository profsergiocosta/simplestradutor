
class Scanner {

    private byte[] input;
    private int current; 


    public Scanner (byte[] input) {
        this.input = input;
    }


    private char peek () {
        if (current < input.length)
           return (char)input[current];
       return 0;
    }


    public char nextToken () {
        char ch = peek();

        if (Character.isDigit(ch)) {
            current++;
            return ch;
        }

        switch (ch) {
            case '+':
            case '-':
                current++;
                return ch;
            default:
                break;
        }

        return 0;
    }

    

}

 class Parser {
    
    private Scanner scan;
    private char currentToken;

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
        } else if (currentToken == 0) {
            // vazio
        } else {
            throw new Error("syntax error");
        }
    }

}




public class Tradutor {
    public static void main(String[] args) {
        String input = "8+5-7+9";
        Parser p = new Parser (input.getBytes());
        p.parse();

    }
}
