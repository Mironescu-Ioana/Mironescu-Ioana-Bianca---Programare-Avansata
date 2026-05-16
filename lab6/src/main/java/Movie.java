import java.sql.Date;

public class Movie {
    private Integer id;
    private String title;
    private Date releaseDate;
    private Integer duration;
    private Double score;
    private Integer genreId;

    public Movie() {
    }

    public Movie(Integer id, String title, Date releaseDate, Integer duration, Double score, Integer genreId) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.score = score;
        this.genreId = genreId;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public Integer getId() {
        return id;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Double getScore() {
        return score;
    }

    public String getTitle() {
        return title;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString()
    {
        return "Movie-> id:"+id+", title: "+title+", realeaseDate: "+releaseDate+", duration: "+duration+", score: "+score+"genreId: "+genreId+";";
    }
}
