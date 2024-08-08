package org.example;

import com.google.common.collect.Lists;
import org.sonar.java.model.JParser;
import org.sonar.java.model.JParserConfig;
import org.sonar.java.model.JavaTree;
import org.sonar.java.model.JavaVersionImpl;
import org.sonar.plugins.java.api.JavaVersion;
import org.sonar.plugins.java.api.tree.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 比较有用的类
 * SonarSymbolTableVisitor - sonar-java项目的类
 *
 */
public class ComplicatedAST {

    public static void main(String[] args) throws IOException {
        CompilationUnitTree ast = parseJava(Path.of
                ("/Users/liurui/workspace/java/NewJava/codeanalyzer/src/main/resources/ComplicatedSample.java"));
        visit(ast);
    }

    public static CompilationUnitTree parseJava(Path filePath) throws IOException {
        String source = Files.readString(filePath, UTF_8);
        JavaVersion javaVersion = new JavaVersionImpl(JavaVersionImpl.MAX_SUPPORTED);
        List<File> classpath = Collections.emptyList();
        JParserConfig parserConfig = JParserConfig.Mode.FILE_BY_FILE.create(javaVersion, classpath);
        return JParser.parse(parserConfig.astParser(), javaVersion.effectiveJavaVersionAsString(), filePath.toString(), source);
    }

    /**
     * DFS tree
     * @param node
     */
    private static void visit(Tree node) {
        /**
         * 打印以语义为单元的信息
         */
        switch (node.kind()) {
            case COMPILATION_UNIT: {
                // 分析顶层的结构
                printCompilationUnitInformation(node);
                break;
            }
            case PACKAGE: {
                // 分析package信息，package是单独的语义块，不再递归分析子树
                printPackageInformation(node);
                return;
            }
            case IMPORT:{
                // 每个import一条。
                printImportInformation(node);
                break;
            }
            case CLASS: {
                // 最复杂的Tree
                printClassInformation(node);
                return;
            }
        }
        /**
         * 关于树结构的处理，都需要转到JavaTree来处理。
         */
        // 不是所有的Tree都可以转成JavaTree吧？
        JavaTree javaTree = (JavaTree)node;
        if (javaTree.isLeaf()) {
            return ;
        }
        List<Tree> children = ((JavaTree) node).getChildren();
        for (Tree child: children) {
            visit(child);
        }
    }

    /**
     * CompilationUnitTree的信息都是怎么抽取的
     * @param node
     */
    private static void printCompilationUnitInformation(Tree node) {
        CompilationUnitTree compilationUnitTree = (CompilationUnitTree)node;
        if (compilationUnitTree.packageDeclaration() != null) {
            System.out.println("has package declaration");
        }
        if (!compilationUnitTree.imports().isEmpty()) {
            System.out.println("has imports");
        }
        if (!compilationUnitTree.types().isEmpty()) {
            // 这个是什么信息？
            System.out.println("has types");
        }
        if (compilationUnitTree.moduleDeclaration() != null) {
            System.out.println("has module declaration");
        }
    }

    private static void printPackageInformation(Tree node) {
        PackageDeclarationTree packageDeclarationTree = (PackageDeclarationTree)node;
        ExpressionTree expressionTree = packageDeclarationTree.packageName();
        MemberSelectExpressionTree memberSelectExpressionTree = (MemberSelectExpressionTree)expressionTree;
        // 按照帮助文档，MemberSelectExpressionTree.expressionTree是前缀，MemberSelectExpressionTree.identifier是后缀，所以拼起来就是包名
        String pkgName = processExpressionTree(memberSelectExpressionTree);
        System.out.println("package name - " + pkgName);
    }

    private static void printImportInformation(Tree node) {
        ImportTree importTree = (ImportTree)node;
        MemberSelectExpressionTree memberSelectExpressionTree = (MemberSelectExpressionTree)importTree.qualifiedIdentifier();
        String importLibName = processExpressionTree(memberSelectExpressionTree);
        String staticImport = importTree.isStatic() ? "static " : "";
        System.out.println(staticImport + "import lib - " + importLibName);
    }

    /**
     * MemberSelectExpressionTree的树，其expression()返回的可能是一棵复杂的递归的ExpressionTree，所以一般要递归处理
     *
     * 抽取全名。
     * 在package和import树中，全名是一个递归的抽取方法。
     * 其他情况可能更复杂
     * @return
     */
    private static String processExpressionTree(MemberSelectExpressionTree memberSelectExpressionTree) {
        ExpressionTree expressionTree = memberSelectExpressionTree.expression();
        if (expressionTree instanceof IdentifierTree) {
            return ((IdentifierTree) expressionTree).name() + "." + memberSelectExpressionTree.identifier().name();
        } else {
            return processExpressionTree((MemberSelectExpressionTree) memberSelectExpressionTree.expression())
                    + "." + memberSelectExpressionTree.identifier().name();
        }
    }

    private static void printClassInformation(Tree node) {
        ClassTree classTree = (ClassTree) node;
        List<Tree> members = classTree.members();
        for (Tree member : members) {
            if (member instanceof ClassTree) {
                // 内部类
            } else if (member instanceof VariableTree) {
                // 变量
                printVariableInformation(member);
            } else if (member instanceof MethodTree) {
                // 函数
                printMethodInformation(member);
            }
        }
    }

    /**
     * 处理变量的相关信息方面，这种方式比用Java反射要麻烦太多了。
     *
     * @param node
     */
    private static void printVariableInformation(Tree node) {
        VariableTree variableTree = (VariableTree) node;
        String name = variableTree.simpleName().name();
        System.out.println("name - " + name);
        processTypeTree(variableTree.type());
    }

    private static void processTypeTree(TypeTree typeTree) {
        if (typeTree instanceof ParameterizedTypeTree) {
            ParameterizedTypeTree parameterizedType = (ParameterizedTypeTree) typeTree;
            System.out.println("Parameterized Type: " + parameterizedType.type());
            for (TypeTree typeArgument : parameterizedType.typeArguments()) {
                // 递归处理泛型参数
                processTypeTree(typeArgument);
            }
        } else if (typeTree instanceof ArrayTypeTree) {
            ArrayTypeTree arrayType = (ArrayTypeTree) typeTree;
            System.out.println("Array Type: " + arrayType.type());
            // 递归处理数组基础类型
            processTypeTree(arrayType.type());
        } else if (typeTree instanceof IdentifierTree) {
            IdentifierTree identifier = (IdentifierTree) typeTree;
            System.out.println("Identifier: " + identifier.name());
        } else {
            System.out.println("Other Type: " + typeTree);
        }
    }

    /**
     * 真正可能有更大作用的是根据函数分析
     * @param node
     */
    private static void printMethodInformation(Tree node) {
        MethodTree methodTree = (MethodTree) node;
        BlockTree blockTree = methodTree.block();
        if (blockTree == null) return;
        processBlockTree(blockTree);
    }

    private static void processBlockTree(BlockTree blockTree) {
        System.out.println("Processing a block...");
        for (StatementTree statement : blockTree.body()) {
            if (statement instanceof BlockTree) {
                processBlockTree((BlockTree) statement); // 递归处理嵌套的块
            } else {
                System.out.println("Processing statement: " + statement);
                if (statement instanceof ForStatementTree) {
                    processForStatementTree((ForStatementTree) statement);
                } else {
                    //
                }
            }
        }
        System.out.println("End of block.");
    }

    //TODO
    private static void processForStatementTree(ForStatementTree forStatementTree) {
        System.out.println("Processing ForStatementTree");
    }

    /**
     * TODO:
     * 直接将某些树过滤出来，例如过滤出来MethodTree。
     * 直接遍历来过滤子树吗？
     */
    private List<Tree> filterMethodTree(Tree node) {
        List<Tree> result = Lists.newArrayList();
        filterMethodTree(node, result);
        return result;
    }

    private void filterMethodTree(Tree node, List<Tree> result) {
        JavaTree cur = (JavaTree) node;
        if (cur.isLeaf()) {
            return;
        }
        if (node instanceof MethodTree) {
            result.add(node);
        }
        List<Tree> children = cur.getChildren();
        for (Tree tree: children) {
            filterMethodTree(tree, result);
        }
    }

}
