import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.json.JSONArray;
import org.json.JSONObject;

public class AjaxScraper {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 6; i++) {
                int year = 2010 + i;
                String url = "https://www.scrapethissite.com/pages/ajax-javascript/?ajax=true&year=" + year;

                Connection.Response response = Jsoup.connect(url)
                        .ignoreContentType(true)
                        .method(Connection.Method.GET)
                        .execute();

                String jsonResponse = response.body();
                JSONArray jsonArray = new JSONArray(jsonResponse);

                System.out.println("\nMovies for " + year + ":");
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject movie = jsonArray.getJSONObject(j);

                    String title = movie.optString("title", "Unknown Title");
                    int nominations = movie.optInt("nominations", 0);
                    int awards = movie.optInt("awards", 0);  // Changed from awards_won to awards

                    System.out.printf("%s - %d nominations, %d awards%n",
                            title,
                            nominations,
                            awards);
                }
            }
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}