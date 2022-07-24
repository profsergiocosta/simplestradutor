
class Scanner {

    private byte[] input;
    private int current; 


    private char peek () {
        if (current < input.length)
           return (char)input[current];
       return 0;
    }

    private void match (char c) {
        if (c == peek()) {
            current++;
        } else {
            throw new Error("syntax error");
        }
    }

    public char nextToken () {
        char ch = peek();

        if (Character.isDigit(ch)) {
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
    private byte[] input;
    private int current; 

	public Parser (byte[] input) {
        this.input = input;
    }

    public void parse () {
        expr();
    }


    private char peek () {
        if (current < input.length)
           return (char)input[current];
       return 0;
    }

    private void match (char c) {
        if (c == peek()) {
            current++;
        } else {
            throw new Error("syntax error");
        }
    }


    void expr() {
        digit();
        oper();
     }

     void digit () {
        if (Character.isDigit(peek())) {
            System.out.println(peek());
            match(peek());
        } else {
           throw new Error("syntax error");
        }
    }

    void oper () {
        if (peek() == '+') {
            match('+');
            digit();
            System.out.println("add");
            oper();
        } else if (peek() == '-') {
            match('-');
            digit();
            System.out.println("sub");
            oper();
        } else if (peek() == 0) {
            // vazio
        } else {
            throw new Error("syntax error");
        }
    }

}




public class Tradutor {
    public static void main(String[] args) throws Exception {
        String input = "42+20";
        Parser p = new Parser (input.getBytes());
        p.parse();

    }
}
