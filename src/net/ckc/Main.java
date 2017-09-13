package net.ckc;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public enum Logical {
        AND, OR;

    }

    public static void main(String[] args) {

      String ui   = "MATCH (mp0:Mp)<-[:PARENT*0..]-(mp:Mp)<-[:MP]-(sr:StatisticalResult)-[:ALLELE]->(a:Allele)-[:GENE]->(g:Gene)  WHERE mp0.mpTerm = 'abnormal retina morphology' AND  sr.significant = true  WITH g, a, mp, sr,  extract(x IN collect(DISTINCT mp) | x.mpTerm) AS mps  OPTIONAL MATCH (g)<-[:GENE]-(dm:DiseaseModel)-[:MOUSE_PHENOTYPE]->(dmp:Mp) WHERE  dm.diseaseToModelScore >= 0 AND dm.diseaseToModelScore <= 100  AND dmp.mpTerm IN mps RETURN collect(distinct a), collect(distinct g), collect(distinct sr), collect(distinct mp), collect(distinct dm)";
      String test = "MATCH (mp0:Mp)<-[:PARENT*0..]-(mp:Mp)<-[:MP]-(sr:StatisticalResult)-[:ALLELE]->(a:Allele)-[:GENE]->(g:Gene)  WHERE mp0.mpTerm = 'abnormal retina morphology' AND  sr.significant = true  WITH g, a, mp, sr,  extract(x IN collect(DISTINCT mp) | x.mpTerm) AS mps  OPTIONAL MATCH (g)<-[:GENE]-(dm:DiseaseModel)-[:MOUSE_PHENOTYPE]->(dmp:Mp) WHERE  dm.diseaseToModelScore >= 0 AND dm.diseaseToModelScore <= 100  AND dmp.mpTerm IN mps RETURN collect(distinct a), collect(distinct g), collect(distinct sr), collect(distinct mp), collect(distinct dm)";
        System.out.println(ui.equals(test));


      //  System.out.println( Double.parseDouble("9.78313E-7") );


//        String symbol = "Casq1<tm1b(EUCOMM)Wtsi>";
//
//
//        int index = symbol.indexOf('<');
//        String wantedSymbol = symbol.replace(symbol.substring(0, 6), "").replace(">","");
//
//        System.out.println(wantedSymbol);
//        String mpStr = "(abnormal retina morphology and abnormal blood circulation) or cardiovascular system phenotype";
//
//        String regex_aAndb_Orc = "\\s*\\(([A-Za-z0-9-\\\\,;:\\s]{1,})\\s*\\b(AND|and)\\b\\s*([A-Za-z0-9-\\\\,;:\\s]{1,})\\)\\s*\\b(OR|or)\\b\\s*([A-Za-z0-9-\\\\,;:\\s]{1,})\\s*";
//
//        if (mpStr.matches(regex_aAndb_Orc)) {
//            System.out.println("matches (a and b) or c"); // due to join empty list to a non-empty list evals to empty, convert this to (a or c) + (b or c)
//
//            Pattern pattern = Pattern.compile(regex_aAndb_Orc);
//            Matcher matcher = pattern.matcher(mpStr);
//
//            while (matcher.find()) {
//                System.out.println("found: " + matcher.group(0));
//                String mpA = matcher.group(1).trim();;
//                String mpB = matcher.group(3).trim();;
//                String mpC = matcher.group(5).trim();;
//                System.out.println("A: " + mpA + ", B: " + mpB + ", C: "+ mpC);
//            }
//        }

    }
}
