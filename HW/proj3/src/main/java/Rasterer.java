import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        //parse map argument from params
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double w = params.get("w");
        double h = params.get("h");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        double lonDPP = (lrlon - ullon)/w;//lon/pixel
        //depth
        int depth = getDepth(lonDPP);

        //left lon index, right lon index, top lat index, bottom lat index
        //assume query box totally inside root
        double dlon = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON)/(Math.pow(2, depth));
        //degree per block at depth layer
        double dlat = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT)/(Math.pow(2, depth));
        int llonIndex = (int) Math.floor((ullon - MapServer.ROOT_ULLON)/dlon);
        double oj = (lrlon - MapServer.ROOT_ULLON)/dlon;
        int rlonIndex = (oj != ((int) oj))? (int) oj : ((int) oj) - 1;
        int tlatIndex = (int) Math.floor((MapServer.ROOT_ULLAT - ullat)/dlat);
        double aj = (MapServer.ROOT_ULLAT - lrlat)/dlat;
        int blatIndex = (aj != (int) aj)? (int) aj : ((int) aj) - 1;

        //query boolean
        //boolean query_success;
        /*below assumes only query totally inside as success
        if (ullon >= MapServer.ROOT_ULLON && lrlon <= MapServer.ROOT_LRLON &&
                ullat <= MapServer.ROOT_ULLAT && lrlat >= MapServer.ROOT_LRLAT &&
                ullon < lrlon && ullat > lrlat) {
            query_success = true;
        } else {
            query_success = false;
            int D = (int) Math.pow(2, depth) - 1;
            //if query outside of root bound, just use root bound
            llonIndex = llonIndex < 0 ? 0 : llonIndex;
            rlonIndex = rlonIndex > D ? D : rlonIndex;
            tlatIndex = tlatIndex < 0 ? 0 : tlatIndex;
            blatIndex = blatIndex > D ? D : blatIndex;

        }
        */
        boolean query_success = false;
        if (ullon >= MapServer.ROOT_LRLON || lrlon <= MapServer.ROOT_ULLON ||
            ullat <= MapServer.ROOT_LRLAT || lrlat >= MapServer.ROOT_ULLAT ||
            ullon >= lrlon || ullat <= lrlat) {
            int D = (int) Math.pow(2, depth) - 1;
            llonIndex = llonIndex < 0 ? 0 : llonIndex;
            rlonIndex = rlonIndex > D ? D : rlonIndex;
            tlatIndex = tlatIndex < 0 ? 0 : tlatIndex;
            blatIndex = blatIndex > D ? D : blatIndex;
        } else {
            query_success = true;
        }
        //bounding box
        double raster_ul_lon = MapServer.ROOT_ULLON + llonIndex * dlon;
        double raster_ul_lat = MapServer.ROOT_ULLAT - tlatIndex * dlat;
        double raster_lr_lon = MapServer.ROOT_ULLON + (rlonIndex + 1) * dlon;
        double raster_lr_lat = MapServer.ROOT_ULLAT - (blatIndex + 1) * dlat;
        //render-grid
        int rowNum = blatIndex - tlatIndex + 1;
        int colNum = rlonIndex - llonIndex + 1;
        String[][] render_grid = new String[rowNum][colNum];
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < colNum; j++) {
                render_grid[i][j] = "d"+Integer.toString(depth)+"_x"+Integer.toString(llonIndex+j)+
                        "_y"+Integer.toString(tlatIndex + i)+".png";
            }
        }
        //put in results
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("query_success", query_success);
        results.put("render_grid", render_grid);
        return results;
    }
    /*Return depth of layer*/
    private int getDepth(double lonDPP) {
        double d0lonDPP = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON)/MapServer.TILE_SIZE;
        //lonDPP of layer 0
        int d = 0;
        while (d0lonDPP > lonDPP) {//use next layer
            d0lonDPP /= 2;
            d += 1;
        }
        d = d < 8 ? d : 7;//max 7th layer
        return d;
    }
    //test test1234.html and testtwelve.html
    public static void main(String[] args) {
        Rasterer ras = new Rasterer();
        Map<String, Double> params = new HashMap<>();
        params.put("lrlon",-122.20908713544797);
        params.put("ullon",-122.3027284165759);
        params.put("w",305.0);
        params.put("h",300.0);
        params.put("ullat",37.88708748276975);
        params.put("lrlat",37.848731523430196);
        Map<String, Object> ans = ras.getMapRaster(params);
        System.out.println(ans);
    }
}
