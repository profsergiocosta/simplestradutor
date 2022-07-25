import java.util.HashMap;
import java.util.Map;

class Scanner {

    private byte[] input;
    private int current; 

    private static final Map<String, TokenType> keywords;
 
    static {
        keywords = new HashMap<>();
        keywords.put("let",    TokenType.LET);
    }

    public Scanner (byte[] input) {
        this.input = input;
    }

    

    private void skipWhitespace() {
        char ch = peek();
        while (ch == ' ' || ch == '\r' || ch == '\t' || ch == '\n') {
            advance();
            ch = peek();
        }
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

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z') ||
                c == '_';
}
    
private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || Character.isDigit((c));
}

    private Token number() {
        int start = current ;
        while (Character.isDigit(peek())) {
            advance();
        }
        
        String n = new String(input, start, current-start)  ;
        return new Token(TokenType.NUMBER, n);
    }

    private Token identifier() {
        int start = current;
        while (isAlphaNumeric(peek())) advance();
    
        String id = new String(input, start, current-start)  ;
        TokenType type = keywords.get(id);
        if (type == null) type = TokenType.IDENT;
        return new Token(type, id);
    }


    public Token nextToken () {

        skipWhitespace();

        char ch = peek();

        if (isAlpha(ch)) {
            return identifier();
        }

        if (ch == '0') {
            advance();
            return new Token (TokenType.NUMBER, Character.toString(ch));
        }  else if (Character.isDigit(ch))
            return number();
            
        else if (Character.isDigit(ch))
            return number();
        

        switch (ch) {
                case '+':
                    advance();
                    return new Token (TokenType.PLUS,"+");
                case '-':
                    advance();
                    return new Token (TokenType.MINUS,"-");

                case '=':
                    advance();
                    return new Token (TokenType.EQ,"=");

                case ';':
                    advance();
                    return new Token (TokenType.SEMICOLON,";");

                case '\0':
                    return new Token (TokenType.EOF,"EOF");
                default:
                    throw new Error("lexical error at " + ch);
        }
    }

    

}

 class Parser {
    
    private Scanner scan;
    private Token currentToken;

    public Parser(byte[] input) {
        scan = new Scanner(input);
        currentToken = scan.nextToken();
    }

    private void nextToken () {
        currentToken = scan.nextToken();
    }


    public void parse () {
        //expr();
        letStatement();
    }
 

    private void match(TokenType t) {
        if (currentToken.type == t) {
            nextToken();
        }else {
            throw new Error("Syntax error at "+currentToken);
        }
    }

    void number () {
        System.out.println("push " + currentToken.lexeme);
        match(TokenType.NUMBER);
    }


    void expr() {
        term ();
        oper();
    }

    void letStatement () {
        match(TokenType.LET);
        match(TokenType.IDENT);
        match(TokenType.EQ);
        expr();
        match(TokenType.SEMICOLON);
    }

    void term () {
        if (currentToken.type == TokenType.NUMBER)
            number();
        else if (currentToken.type == TokenType.IDENT) {
            System.out.println("push "+currentToken.lexeme);
            match(TokenType.IDENT);
        }
        else
            throw new Error("syntax error");
    }


    void oper () {
        if (currentToken.type == TokenType.PLUS) {
            match(TokenType.PLUS);
            term();
            System.out.println("add");
            oper();
        } else if (currentToken.type == TokenType.MINUS) {
            match(TokenType.MINUS);
            term();
            System.out.println("sub");
            oper();
        } 
    }

}
 



public class Tradutor {
    public static void main(String[] args) {
        
        String input = "let a = 42 + 5 ;";
/*
        Scanner scan = new Scanner (input.getBytes());
        for (Token tk = scan.nextToken(); tk.type != TokenType.EOF; tk = scan.nextToken()) {
            System.out.println(tk);
        }
 */
        Parser p = new Parser (input.getBytes());
        p.parse();

   
    }
}
