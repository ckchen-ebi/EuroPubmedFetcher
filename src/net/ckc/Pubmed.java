package net.ckc;

import java.util.List;

/**
 * Created by ckchen on 06/09/2017.
 */
public class Pubmed {

    String pmid;
    String title;
    String paperurl;
    List<MeshTerm> meshTerms;
    String abstractText;

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPaperurl() {
        return paperurl;
    }

    public void setPaperurl(String paperurl) {
        this.paperurl = paperurl;
    }

    public List<MeshTerm> getMeshTerms() {
        return meshTerms;
    }

    public void setMeshTerms(List<MeshTerm> meshTerms) {
        this.meshTerms = meshTerms;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }

    @Override
    public String toString() {
        return "Pubmed{" +
                "pmid='" + pmid + '\'' +
                ", title='" + title + '\'' +
                ", paperurl='" + paperurl + '\'' +
                ", meshTerms=" + meshTerms +
                ", abstractText='" + abstractText + '\'' +
                '}';
    }
}
