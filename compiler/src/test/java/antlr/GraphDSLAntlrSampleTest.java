package antlr;

import gen.GraphLexer;
import gen.GraphParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class GraphDSLAntlrSampleTest {

    @Test
    public void testAntlrSample() throws IOException {
        //Reading the DSL script
        FileInputStream inputStream = new FileInputStream("src/main/resources/GraphSample.gr");
        //转化为字符流
        CharStream is = CharStreams.fromStream(inputStream);

        //Passing the input to the lexer to create tokens
        GraphLexer lexer = new GraphLexer(is);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        //Passing the tokens to the parser to create the parse trea.
        GraphParser parser = new GraphParser(tokens);

        //Semantic model to be populated
        Graph g = new Graph();

        //Adding the listener to facilitate walking through parse tree.
        parser.addParseListener((ParseTreeListener) new MyGraphBaseListener(g));

        //invoking the parser.
        parser.graph();

        Graph.printGraph(g);
    }
}
