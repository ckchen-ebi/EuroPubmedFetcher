package net.ckc;

import java.util.List;

/**
 * Created by ckchen on 06/09/2017.
 */
public class MainMeshTerm {
    public String meshHeading;
    public String majorTopic_YN;

    public String getMeshHeading() {
        return meshHeading;
    }

    public void setMeshHeading(String meshHeading) {
        this.meshHeading = meshHeading;
    }

    public String getMajorTopic_YN() {
        return majorTopic_YN;
    }

    public void setMajorTopic_YN(String majorTopic_YN) {
        this.majorTopic_YN = majorTopic_YN;
    }

    @Override
    public String toString() {
        return "MainMeshTerm{" +
                "meshHeading='" + meshHeading + '\'' +
                ", majorTopic_YN='" + majorTopic_YN + '\'' +
                '}';
    }
}
