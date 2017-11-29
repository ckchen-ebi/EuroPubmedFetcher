package net.ckc;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by ckchen on 29/11/2017.
 */
public class FetchCitingPapers {

    LinkedHashMap<Integer, Set<Integer>> pmidCitingPmids = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException, URISyntaxException {
        FetchCitingPapers fetcher = new FetchCitingPapers();
        fetcher.run();
    }
    private void run() throws IOException, URISyntaxException {
        //List<Integer> pmids = Arrays.asList(24599260, 21122816); // quick test

        List<Integer> pmids = Arrays.asList(19525957,
                20799038,
                21122816,
                21240276,
                21655083,
                21677750,
                21734703,
                21750680,
                21763481,
                22005280,
                22144916,
                22179047,
                22308354,
                22532369,
                22579044,
                22595669,
                22719219,
                22761313,
                22801499,
                22802351,
                22876197,
                22922464,
                22922648,
                22945801,
                22958918,
                23028378,
                23142661,
                23166506,
                23185619,
                23226398,
                23267094,
                23300663,
                23401851,
                23454480,
                23468651,
                23516444,
                23620107,
                23640545,
                23713592,
                23817550,
                23827709,
                23870131,
                23897886,
                23908241,
                23912999,
                23934451,
                23973919,
                23989956,
                24013503,
                24055102,
                24064296,
                24135232,
                24139043,
                24179230,
                24210661,
                24239741,
                24274065,
                24284630,
                24293546,
                24356961,
                24357318,
                24391134,
                24423645,
                24529376,
                24613482,
                24646517,
                24652767,
                24667445,
                24685140,
                24706806,
                24721909,
                24726326,
                24726384,
                24777781,
                24807221,
                24821797,
                24833352,
                24836425,
                24844465,
                24860998,
                24898145,
                24981230,
                25026213,
                25056906,
                25087892,
                25127743,
                25155611,
                25180231,
                25183173,
                25187265,
                25195104,
                25215489,
                25220394,
                25222142,
                25248098,
                25258341,
                25259925,
                25263220,
                25282615,
                25299188,
                25340873,
                25347065,
                25377878,
                25404741,
                25422446,
                25428364,
                25451192,
                25481835,
                25492475,
                25512563,
                25519955,
                25521379,
                25525875,
                25526730,
                25533288,
                25543281,
                25550471,
                25580854,
                25617472,
                25654255,
                25660024,
                25662603,
                25680095,
                25713362,
                25724625,
                25729399,
                25782772,
                25801166,
                25805620,
                25810472,
                25838550,
                25869670,
                25870538,
                25959730,
                25961503,
                25970242,
                25980009,
                25985275,
                25985299,
                25990470,
                25992553,
                26028225,
                26037925,
                26044960,
                26060214,
                26072710,
                26078274,
                26095358,
                26102480,
                26119739,
                26177727,
                26188089,
                26214740,
                26223655,
                26246171,
                26258414,
                26268777,
                26280336,
                26286991,
                26305884,
                26307081,
                26317471,
                26320659,
                26335643,
                26365183,
                26385183,
                26398943,
                26402843,
                26420843,
                26432886,
                26437029,
                26438361,
                26523868,
                26525805,
                26579598,
                26582950,
                26587753,
                26590424,
                26619789,
                26637354,
                26660102,
                26665171,
                26681805,
                26697887,
                26727661,
                26740663,
                26747696,
                26755700,
                26776516,
                26785054,
                26795843,
                26808229,
                26822507,
                26828201,
                26829592,
                26865945,
                26868297,
                26876430,
                26878175,
                26881968,
                26903600,
                26903602,
                26925136,
                26935106,
                26952749,
                26968211,
                26980189,
                26994494,
                27009967,
                27013243,
                27032818,
                27048792,
                27050515,
                27053112,
                27064284,
                27083047,
                27106110,
                27117407,
                27135601,
                27141965,
                27147664,
                27177310,
                27184849,
                27203244,
                27207521,
                27210552,
                27249171,
                27266524,
                27302397,
                27357688,
                27381259,
                27385014,
                27444544,
                27460150,
                27466704,
                27470444,
                27493188,
                27496731,
                27502165,
                27504968,
                27507934,
                27518902,
                27524794,
                27550173,
                27581460,
                27626380,
                27660326,
                27695001,
                27701470,
                27705781,
                27709605,
                27721407,
                27798843,
                27822537,
                27826253,
                27827819,
                27827997,
                27857073,
                27935040,
                27943094,
                27965109,
                27986456,
                27996060,
                28007585,
                28026023,
                28031293,
                28041877,
                28045099,
                28049716,
                28052249,
                28077655,
                28089251,
                28104815,
                28128235,
                28137885,
                28193997,
                28238654,
                28239655,
                28254523,
                28319090,
                28322331,
                28332498,
                28343629,
                28363983,
                28369354,
                28388629,
                28395340,
                28424523,
                28430876,
                28488815,
                28499989,
                28507225,
                28507547,
                28530678,
                28533362,
                28578315,
                28600325,
                28621310,
                28630177,
                28658614,
                28696225,
                28739881,
                28743718,
                28778945,
                28811369,
                28878098,
                28899994,
                28923496
              );


        fetchCitingPmids(pmids);

        // output result
        exportExcel();
    }

    private void fetchCitingPmids(List<Integer> pmids) {

        String dbfetchUrl = "http://www.ebi.ac.uk/europepmc/webservices/rest/search/format=json&resulttype=idlist&pageSize=500&query=CITES:";

        int counts = 0;
        for(Integer pmid : pmids) {
            counts++;
            JSONObject json = fetchHttpUrlJson(dbfetchUrl + pmid + "_MED");

            //System.out.println("check json: "+ json);

            // some paper do not have citation
            JSONArray results = json.getJSONObject("resultList").getJSONArray("result");

            if (results.size() > 0) {
                for (int j = 0; j < results.size(); j++) {

                    JSONObject r = results.getJSONObject(j);

                    if (r.containsKey("pmid")) {
                        int citingPmid = Integer.parseInt(r.getString("pmid"));

                        if (!pmidCitingPmids.containsKey(pmid)) {
                            pmidCitingPmids.put(pmid, new HashSet<Integer>());
                        }
                        pmidCitingPmids.get(pmid).add(citingPmid);
                    }
                }
            }
            else {
                pmidCitingPmids.put(pmid, new HashSet<Integer>());
                pmidCitingPmids.get(pmid).add(null);
            }

            //System.out.println(counts + ":" + pmidCitingPmids.get(pmid).size());
        }
    }
    
    private void exportExcel() throws URISyntaxException {

        for(int i=0; i<2; i++) {

            XSSFWorkbook wb = new XSSFWorkbook();

            // create a new sheet
            XSSFSheet sheet = wb.createSheet("pmid-citingPmids");

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
            sheet.createFreezePane(0, 1); // freeze first row when scroll: always sees the header

            //header row
            XSSFRow headerRow = sheet.createRow(0);
            //headerRow.setHeightInPoints(40);

            XSSFCell headerCell;
            List<String> titles = Arrays.asList("PMID", "Paper counts", "Citing PMID");
            int cellCount = 0;
            for (String title : titles) {
                headerCell = headerRow.createCell(cellCount);
                headerCell.setCellValue(title);
                headerCell.setCellStyle(style);
                cellCount++;
            }

            int rowCount = 0;
            String baseUrl = "http://europepmc.org/abstract/MED/";
            String filename = null;
            String NOCITATION = "no citation";

            for (Map.Entry<Integer, Set<Integer>> entry : pmidCitingPmids.entrySet()) {
                System.out.println(entry.getKey() + "/" + entry.getValue());

                Integer pmid = entry.getKey();
                Set<Integer> citingPmids = (Set<Integer>) entry.getValue();
                Iterator iter = citingPmids.iterator();
                Object first = iter.next();

                if (i == 0) {

                    // File one: all citing pmids in one column
                    filename = "/Users/ckchen/Downloads/pmid-citingPmids.xlsx";

                    rowCount++;

                    XSSFRow row = sheet.createRow(rowCount);

                    int cellNum = 0;

                    for (String title : titles) {
                        XSSFCell cell = row.createCell(cellNum);

                        if (title.equals("PMID")) {
                            cell.setCellValue(pmid);
                        }
                        else if (title.equals("Paper counts")) {
                            cell.setCellValue(first == null ? 0 : citingPmids.size());
                        }
                        else {
                            cell.setCellValue(first == null ? NOCITATION : StringUtils.join(citingPmids, ", "));
                        }

                        cellNum++;
                    }
                }
                else {
                    // File two: each citing pmid in its own column
                    filename = "/Users/ckchen/Downloads/pmid-citingPmid.xlsx";

                    int idRow = 0;
                    for(Integer pmidint : citingPmids){
                        idRow++;
                        rowCount++;

                        XSSFRow row = sheet.createRow(rowCount);

                        int cellNum = 0;

                        for (String title : titles) {
                            XSSFCell cell = row.createCell(cellNum);

                            if (title.equals("PMID")) {
                                cell.setCellValue(pmid);
                            }
                            else if (title.equals("Paper counts")) {
                               // cell.setCellValue(idRow + "/" + citingPmids.size());
                                cell.setCellValue(first == null ? "0" : idRow + "/" + citingPmids.size());
                            }
                            else {
                                String cellStr = baseUrl + pmidint;

                                if (first == null){
                                    cell.setCellValue(NOCITATION);
                                }
                                else {
                                    XSSFHyperlink url_link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_URL);
                                    url_link.setAddress(cellStr);
                                    cell.setCellValue(cellStr);
                                    cell.setHyperlink(url_link);
                                }

//                                System.out.println("row " + rowCount + ": " + pmid + " -> " + pmidint);
                            }

                            cellNum++;
                        }
                    }
                }
            }

            try {
                FileOutputStream outputStream = new FileOutputStream(filename);
                wb.write(outputStream);
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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