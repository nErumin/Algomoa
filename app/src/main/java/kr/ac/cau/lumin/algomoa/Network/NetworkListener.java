package kr.ac.cau.lumin.algomoa.Network;

/**
 * Created by Lumin on 2015-11-24.
 */
public interface NetworkListener {
    void executeOnNetwork(String response);
    void executeFailOnNetwork(String errorResponse);
}
