//package NLP.preprocessing;
//
//import edu.stanford.nlp.coref.data.CorefChain;
//import edu.stanford.nlp.ie.util.RelationTriple;
//import edu.stanford.nlp.ling.CoreLabel;
//import edu.stanford.nlp.pipeline.*;
//import edu.stanford.nlp.semgraph.SemanticGraph;
//import edu.stanford.nlp.trees.Tree;
//import util.HTMLHelp;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
//public class Preprocessing {
//
//    public static String text = "When a MediaPlayer object is just created using <code>new</code> or after {@link #reset()} is called, it is in the <em>Idle</em> state; and after {@link #release()} is called, it is in the <em>End</em> state. Between these two states is the life cycle of the MediaPlayer object.";
//
//    public static void main(String[] args) {
//        // set up pipeline properties
//        Properties props = new Properties();
//        // set the list of annotators to run
//        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref,kbp,quote");
//        // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
//        props.setProperty("coref.algorithm", "neural");
//        // build pipeline
//        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
//        // create a document object
//        CoreDocument document = new CoreDocument(text);
//        // annnotate the document
//        pipeline.annotate(document);
//        // examples
//
//        //tokens of the document
//        System.out.println("=======================tokens=======================");
//        List<CoreLabel> tokenList = document.tokens();
//        for(CoreLabel coreLabel : tokenList){
//            System.out.println(coreLabel.toString());
//            System.out.println();
//        }
//
//        System.out.println("=======================sentences=======================");
//        for(CoreSentence currentSentence : document.sentences()){
//            System.out.println("sentence:"+currentSentence);
//            System.out.println("pos tags:"+currentSentence.posTags());
//            System.out.println("ner tags:"+currentSentence.nerTags());
//            System.out.println("constituencyParse:"+currentSentence.constituencyParse());
//            System.out.println("dependency parse:"+currentSentence.dependencyParse());
//            System.out.println("relations:"+currentSentence.relations());
//            System.out.println("entity mentions:"+currentSentence.entityMentions());
//        }
//
//        System.out.println("=======================coref=======================");
//        System.out.println("coref chains:"+document.corefChains());
//
//        System.out.println("=======================quotes=======================");
//        System.out.println("quotes:"+document.quotes());
//    }
//}
