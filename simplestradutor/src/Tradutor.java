import java.util.HashMap;
import java.util.Map;

class Scanner {

    private byte[] input;
    private int current; 

    private static final Map<String, TokenType> keywords;
 
    static {
        keywords = new HashMap<>();
        keywords.put("let",    TokenType.LET);
        keywords.put("print",    TokenType.PRINT);
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
    private StringBuilder code = new StringBuilder();

    public Parser(byte[] input) {
        scan = new Scanner(input);
        currentToken = scan.nextToken();
    }

    private void nextToken () {
        currentToken = scan.nextToken();
    }


    public void parse () {
        statements();
    }
 

    private void match(TokenType t) {
        if (currentToken.type == t) {
            nextToken();
        }else {
            throw new Error("Syntax error at "+currentToken);
        }
    }

    void number () {
        code.append(String.format("%s\n", "push " + currentToken.lexeme));
        match(TokenType.NUMBER);
    }


    void expr() {
        term ();
        oper();
    }

    void letStatement () {
        match(TokenType.LET);
        var id = currentToken.lexeme;
        match(TokenType.IDENT);
        match(TokenType.EQ);
        expr();
        code.append(String.format("%s\n", "pop "+id));
        match(TokenType.SEMICOLON);
    }

    void printStatement () {
        match(TokenType.PRINT);
        expr();
        code.append(String.format("%s\n", "print"));
        match(TokenType.SEMICOLON);
    }

    void statements () {
        
        while (currentToken.type != TokenType.EOF) {
            statement();
        }
    }

    void statement () {
        if (currentToken.type == TokenType.PRINT) {
            printStatement();
        } else if (currentToken.type == TokenType.LET) {
            letStatement();
        } else {
            throw new Error("syntax error");
        }
    }

    void term () {
        if (currentToken.type == TokenType.NUMBER)
            number();
        else if (currentToken.type == TokenType.IDENT) {
            code.append(String.format("%s\n", "push "+currentToken.lexeme));
            match(TokenType.IDENT);
        }
        else
            throw new Error("syntax error");
    }


    void oper () {
        if (currentToken.type == TokenType.PLUS) {
            match(TokenType.PLUS);
            term();
            code.append(String.format("%s\n", "add"));
            oper();
        } else if (currentToken.type == TokenType.MINUS) {
            match(TokenType.MINUS);
            term();
            code.append(String.format("%s\n", "sub"));
            oper();
        } 
    }

    public String output () {
        return code.toString();
    }

}
 



public class Tradutor {
    public static void main(String[] args) {
        
        String input = """
            let a = 42 + 5 - 8;
            let b = 56 + 8;
            print a + b + 6;        
                """;
        
        Parser p = new Parser (input.getBytes());
        p.parse();
        System.out.println(p.output());
   
    }
}
