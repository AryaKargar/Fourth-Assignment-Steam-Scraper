import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Parser {
    private List<Game> games;

    public Parser() {
        this.games = new ArrayList<>();
    }

    public void setUp() throws IOException {
        File input = new File("src/Resources/Video_Games.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        Elements gameElements = doc.select("div.game");
        for (Element gameElement : gameElements) {
            String name = gameElement.select("h3.game-name").text();
            String ratingText = gameElement.select("span.game-rating").text().replace("/5", "");
            double rating = Double.parseDouble(ratingText);
            String priceText = gameElement.select("span.game-price").text().replace("€", "").trim();
            int price = Integer.parseInt(priceText);

            games.add(new Game(name, rating, price));
        }
    }

    public List<Game> sortByName() {
        List<Game> sortedByName = new ArrayList<>(games);
        sortedByName.sort(Comparator.comparing(Game::getName));
        return sortedByName;
    }

    public List<Game> sortByRating() {
        List<Game> sortedByRating = new ArrayList<>(games);
        sortedByRating.sort(Comparator.comparing(Game::getRating).reversed());
        return sortedByRating;
    }

    public List<Game> sortByPrice() {
        List<Game> sortedByPrice = new ArrayList<>(games);
        sortedByPrice.sort(Comparator.comparing(Game::getPrice).reversed());
        return sortedByPrice;
    }

    public static void main(String[] args) {
        Parser parser = new Parser();
        try {
            parser.setUp();

            System.out.println("Sorted by Name:");
            parser.sortByName().forEach(System.out::println);

            System.out.println("\n Sorted by Rating:");
            parser.sortByRating().forEach(System.out::println);

            System.out.println("\n Sorted by Price:");
            parser.sortByPrice().forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
