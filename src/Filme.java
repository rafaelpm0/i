public class Filme {

    public int index;
    public long budget;
    public String genres;
    public String homepage;
    public int id;
    public String keywords;
    public String original_language;
    public String original_title;
    public String overview;
    public double popularity;
    public String production_companies;
    public String production_countries;
    public String release_date;
    public long revenue;
    public double runtime;
    public String spoken_languages;
    public String status;
    public String tagline;
    public String title;
    public double vote_average;
    public int vote_count;
    public String cast;
    public String crew;
    public String director;

    // 🔧 Construtor
    public Filme(int index, long budget, String genres, String homepage, int id,
                 String keywords, String original_language, String original_title,
                 String overview, double popularity, String production_companies,
                 String production_countries, String release_date, long revenue,
                 double runtime, String spoken_languages, String status,
                 String tagline, String title, double vote_average,
                 int vote_count, String cast, String crew, String director) {

        this.index = index;
        this.budget = budget;
        this.genres = genres;
        this.homepage = homepage;
        this.id = id;
        this.keywords = keywords;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.popularity = popularity;
        this.production_companies = production_companies;
        this.production_countries = production_countries;
        this.release_date = release_date;
        this.revenue = revenue;
        this.runtime = runtime;
        this.spoken_languages = spoken_languages;
        this.status = status;
        this.tagline = tagline;
        this.title = title;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.cast = cast;
        this.crew = crew;
        this.director = director;
    }

    // 📄 toString (pra debug)
    // @Override
    // public String toString() {
    //     return "Filme{" +
    //             "title='" + title + '\'' +
    //             ", vote_average=" + vote_average +
    //             ", popularity=" + popularity +
    //             ", budget=" + budget +
    //             '}';
    // }
}