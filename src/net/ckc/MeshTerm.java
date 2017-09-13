package net.ckc;

import java.util.List;

/**
 * Created by ckchen on 06/09/2017.
 */
public class MeshTerm {
    public MainMeshTerm mainMeshTerm;
    public List<SubMeshTerm> subMeshTerms;

    public MainMeshTerm getMainMeshTerm() {
        return mainMeshTerm;
    }

    public void setMainMeshTerm(MainMeshTerm mainMeshTerm) {
        this.mainMeshTerm = mainMeshTerm;
    }

    public List<SubMeshTerm> getSubMeshTerms() {
        return subMeshTerms;
    }

    public void setSubMeshTerms(List<SubMeshTerm> subMeshTerms) {
        this.subMeshTerms = subMeshTerms;
    }

    @Override
    public String toString() {
        return "MeshTerm{" +
                "mainMeshTerm=" + mainMeshTerm +
                ", subMeshTerms=" + subMeshTerms +
                '}';
    }
}
