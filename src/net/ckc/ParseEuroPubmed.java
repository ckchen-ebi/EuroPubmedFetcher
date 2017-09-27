package net.ckc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.common.usermodel.*;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.ckc.Pubmed;
import net.ckc.MeshTerm;


/**
 * Created by ckchen on 06/09/2017.
 */

public class ParseEuroPubmed {

    public static void main(String[] args) throws IOException, URISyntaxException {
        ParseEuroPubmed parser = new ParseEuroPubmed();
        parser.run();
    }
    private void run() throws IOException, URISyntaxException {
        //List<String> pmids = Arrays.asList("21655083","22144916");

        List<String> pmids = Arrays.asList(
                "21655083","22144916","22532369","22719219","22801499","22802351","23028378","23267094","23401851","23454480",
                "23468651","23640545","23713592","23817550","23827709","23897886","23908241","24013503","24064296","24293546",
                "24391134","24685140","24981230","25026213","25087892","25180231","25183173","25187265","25195104","25215489",
                "25299188","25377878","25481835","25492475","25580854","25660024","25662603","25713362","25805620","25810472",
                "25838550","25870538","25985299","25990470","26028225","26044960","26072710","26078274","26102480","26177727",
                "26188089","26246171","26268777","26280336","26286991","26307081","26402843","26420843","26432886","26587753",
                "26619789","26637354","26665171","26697887","26727661","26740663","26747696","26776516","26795843","26828201",
                "26829592","26868297","26876430","26878175","26903600","26935106","26968211","26994494","27032818","27083047",
                "27207521","27210552","27249171","27302397","27357688","27444544","27460150","27466704","27470444","27518902",
                "27524794","27550173","27695001","27701470","27709605","27826253","27827819","27857073","27935040","28007585",
                "28026023","28041877","28045099","28049716","28089251","28104815","28128235","28238654","28254523","28319090",
                "28322331","28343629","28369354","28388629","28395340","28424523","28488815","28499989","28507225","28530678");

        List<Pubmed> papers = fetchEuropePubmedData(pmids);

        // output result
        exportExcel(papers);
    }
    private List<Pubmed> fetchEuropePubmedData(List<String> pmids){

        System.out.println("PARSING EUROPE PUBMED:");

        List<Pubmed> papers = new ArrayList<>();

        for(String pmid : pmids){
            String dbfetchUrl = "http://www.ebi.ac.uk/europepmc/webservices/rest/search/query=" + pmid + "%20and%20src:MED&format=json&resulttype=core";

            JSONObject json = fetchHttpUrlJson(dbfetchUrl);

            //System.out.println("check json: "+ json);
            JSONArray results = json.getJSONObject("resultList").getJSONArray("result");

            for ( int j=0; j<results.size(); j++ ) {

                JSONObject r = results.getJSONObject(j);

                String pmidFound = Integer.toString(r.getInt("pmid"));

                if (! pmidFound.equals(pmid)){
                    continue;  // skip this result, want to avoid a query pmid points to multiple pmids
                }

                Pubmed pub = new Pubmed();

                pub.setPmid(pmid);

                String title = "";
                if ( r.containsKey("title")  ){
                    pub.setTitle(r.getString("title"));
                }

                String abstractText = "";
                if (r.containsKey("abstractText")){
                    pub.setAbstractText(r.getString("abstractText"));
                }

                if ( r.containsKey("fullTextUrlList") ){
                    JSONArray textUrl = r.getJSONObject("fullTextUrlList").getJSONArray("fullTextUrl");
                    for ( int l=0; l<textUrl.size(); l++ ){
                        JSONObject thisT = (JSONObject) textUrl.get(l);
                        if ( thisT.containsKey("url") ){
                            pub.setPaperurl(thisT.getString("url"));
                            break;
                        }
                    }
                }

                if ( r.containsKey("meshHeadingList") ){

                    List<MeshTerm> meshTerms = new ArrayList<>();

                    JSONArray meshHeadings = r.getJSONObject("meshHeadingList").getJSONArray("meshHeading");

                    for ( int mh=0; mh<meshHeadings.size(); mh++ ){
                        JSONObject thisMeshHeading = (JSONObject) meshHeadings.get(mh);

                        MeshTerm meshTerm = new MeshTerm();
                        MainMeshTerm mainMeshTerm = new MainMeshTerm();
                        //System.out.println(thisMeshHeading.toString());

                        // mesh heading
                        if ( thisMeshHeading.containsKey("descriptorName") ) {
                            mainMeshTerm.setMeshHeading(thisMeshHeading.getString("descriptorName"));
                        }
                        if ( thisMeshHeading.containsKey("majorTopic_YN") ) {
                            mainMeshTerm.setMajorTopic_YN(thisMeshHeading.getString("majorTopic_YN"));
                        }

                        meshTerm.setMainMeshTerm(mainMeshTerm);

                        // mesh subheading
                        if ( thisMeshHeading.containsKey("meshQualifierList") ) {
                            JSONArray meshQualifiers= thisMeshHeading.getJSONObject("meshQualifierList").getJSONArray("meshQualifier");

                            List<SubMeshTerm> subMeshTerms = new ArrayList<>();

                            for (int mq = 0; mq < meshQualifiers.size(); mq++) {
                                JSONObject thisMeshQualifier = (JSONObject) meshQualifiers.get(mq);
                                SubMeshTerm subMeshTerm = new SubMeshTerm();
                                if ( thisMeshQualifier.containsKey("qualifierName") ) {
                                    subMeshTerm.setQualifierName(thisMeshQualifier.getString("qualifierName"));
                                }
                                if ( thisMeshQualifier.containsKey("majorTopic_YN") ) {
                                    subMeshTerm.setMajorTopic_YN(thisMeshQualifier.getString("majorTopic_YN"));
                                }
                                subMeshTerms.add(subMeshTerm);
                            }

                            meshTerm.setSubMeshTerms(subMeshTerms);
                        }

                        meshTerms.add(meshTerm);
                    }

                    pub.setMeshTerms(meshTerms);
                }

                papers.add(pub);
            }
        }

        return papers;

    }

    private void exportExcel(List<Pubmed> papers) throws URISyntaxException {

        XSSFWorkbook wb = new XSSFWorkbook();

        // create a new sheet
        XSSFSheet sheet = wb.createSheet("EuroPubmedData");

        CreationHelper createHelper = wb.getCreationHelper();

        CellStyle style = wb.createCellStyle();//Create style
        Font font = wb.createFont();//Create font
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
        style.setFont(font);//set it to bold
        //style.setWrapText(true);

        XSSFPrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        sheet.createFreezePane(0,1); // freeze first row when scroll: always sees the header

        //header row
        XSSFRow headerRow = sheet.createRow(0);
        //headerRow.setHeightInPoints(40);

        XSSFCell headerCell;
        List<String> titles = Arrays.asList("Title", "PMID", "Abstract", "Main MeSH term", "Sub MeSH terms", "Fulltext link");

        int cellCount = 0;
        for (String title : titles) {
            headerCell = headerRow.createCell(cellCount);
            headerCell.setCellValue(title);
            headerCell.setCellStyle(style);
            cellCount++;
        }
        // data rows
        // Create a row and put some cells in it. Rows are 0 based.
        // Then set value for that created cell

        int totalRowCounts = 0;
        int paperCount = 0;
        for (Pubmed pub : papers) { // data does not contain title row

            paperCount++;
//            System.out.println(pub.getPmid());
//            System.out.println(pub.getMeshTerms());

            int rowCounts = 0;

            if (pub.getMeshTerms() != null) {
                rowCounts = pub.getMeshTerms().size();
            }
            totalRowCounts += rowCounts;

            //System.out.println("need " + rowCounts + " rows");
            int start = paperCount == 1 ? 0 : totalRowCounts - rowCounts;

            if (totalRowCounts == start){
                totalRowCounts++;
            }

            int meshCount = 0;
            // start continues from previous count for row numbers
            for (int c=start; c<totalRowCounts; c++) {
                meshCount ++;
                XSSFRow row = sheet.createRow(c+1);

                int cellNum = 0;

                for (String title : titles) {

                    XSSFCell cell = row.createCell(cellNum);

                    if (title.equals("Title")) {
                        cell.setCellValue(pub.getTitle());
                    }
                    else if (title.equals("PMID")) {
                        cell.setCellValue(pub.getPmid());
                    }
                    else if (title.equals("Abstract")) {
                        cell.setCellValue(pub.getAbstractText());
                    }
                    else if (title.equals("Main MeSH term")) {
                        List<String> vals = new ArrayList<>();
                        String isMajorTopic = null;
                        if (rowCounts != 0) {

                            // meshCount refers to the counts of current paper
                            // ie, not related to numbers from continuous count
                            MeshTerm term = pub.getMeshTerms().get(meshCount-1);

                            String mainTerm = term.getMainMeshTerm().getMeshHeading();
                            isMajorTopic = term.getMainMeshTerm().getMajorTopic_YN();
                            String main = mainTerm + " (" + isMajorTopic + ")";
                            cell.setCellValue(main);
                            //System.out.println("main: " + main);
                            cellNum++;

                            if (term.getSubMeshTerms() != null) {
                                List<String> subs = new ArrayList<>();
                                for (int s = 0; s < term.getSubMeshTerms().size(); s++) {
                                    String subTerm = term.getSubMeshTerms().get(s).getQualifierName();
                                    isMajorTopic = term.getSubMeshTerms().get(s).getMajorTopic_YN();
                                    String sub = subTerm + " (" + isMajorTopic + ")";
                                    subs.add(sub);
                                }

                                //System.out.println("sub: " + StringUtils.join(subs, ", "));
                                cell = row.createCell(cellNum);
                                cell.setCellValue(StringUtils.join(subs, ", "));
                            }
                        }
                        else {
                            cell.setCellValue("");
                            //System.out.println("main: " + main);
                            cellNum++;
                            cell = row.createCell(cellNum);
                            cell.setCellValue("");
                        }

                    }
                    else if (title.equals("Fulltext link")) {

                        cell = row.createCell(cellNum-1);
                        String cellStr = pub.getPaperurl();
                        cellStr = cellStr.replaceAll(" ","%20");  // so that url link would work
                        cellStr = new URI(cellStr).toASCIIString();
                        cellStr = cellStr.replace("%3F","?");  // so that url link would work

                        XSSFHyperlink url_link = (XSSFHyperlink)createHelper.createHyperlink(Hyperlink.LINK_URL);

                        url_link.setAddress(cellStr);

                        cell.setCellValue(cellStr);
                        cell.setHyperlink(url_link);
                    }

                    cellNum++;
                }
            }
        }

        String filename = "/Users/ckchen/Downloads/pubmedData.xlsx";
        try {
            FileOutputStream outputStream = new FileOutputStream(filename);
            wb.write(outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");

    }

    private JSONObject fetchHttpUrlJson(String dbfetchUrl) {
        // Data obtained from service, to be returned
        String jsonStr = null;
        // Get data using HTTP GET
        try {
            URL url = new URL(dbfetchUrl);
            BufferedReader inBuf = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer strBuf = new StringBuffer();
            while(inBuf.ready()) {
                strBuf.append(inBuf.readLine() + System.getProperty("line.separator"));
            }
            jsonStr = strBuf.toString();
        }
        catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
        // Return the response data as JSON object
        JSONObject json = JSONObject.fromObject(jsonStr);
        return json;
    }

}