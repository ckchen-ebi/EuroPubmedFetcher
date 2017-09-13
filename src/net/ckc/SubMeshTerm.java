package net.ckc;

/**
 * Created by ckchen on 06/09/2017.
 */
public class SubMeshTerm {

    public String qualifierName;
    public String majorTopic_YN;

    public String getQualifierName() {
        return qualifierName;
    }

    public void setQualifierName(String qualifierName) {
        this.qualifierName = qualifierName;
    }

    public String getMajorTopic_YN() {
        return majorTopic_YN;
    }

    public void setMajorTopic_YN(String majorTopic_YN) {
        this.majorTopic_YN = majorTopic_YN;
    }

    @Override
    public String toString() {
        return "SubMeshTerm{" +
                "qualifierName='" + qualifierName + '\'' +
                ", majorTopic_YN='" + majorTopic_YN + '\'' +
                '}';
    }
}
